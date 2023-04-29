package com.ruoyi.minio.template;

import ch.qos.logback.core.util.FileUtil;

import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.minio.entity.ObjectItem;
import com.ruoyi.minio.utils.FileUtils;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.minio.utils.FileUtils.createFileItem;

/**
 * @Author xiaozq
 * @Date 2022/8/5 10:42
 * <p>@Description:</p>
 */
@Component
public class MinioTemplate {

    @Autowired
    private MinioClient minioClient;

    //    @Value("${minio.bucket}")
    public String bucketName = "photo";

    //    @Value("${minio.urlprefix}")
    public String urlprefix = "/minio/";

    /**
     * 判断bucket是否存在，不存在则创建
     *
     * @param name
     */
    public void existBucket(String name) {
        try {
            boolean exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public Map <String, String> upload(MultipartFile file, Long id) {
        Map <String, String> resultMap = new HashMap <>();
//        String filename = FileUtils.extractUploadFilename(file);
//        +  file.getOriginalFilename()
        String fileName = String.valueOf(id) + UUID.randomUUID();
        try {
            InputStream inputStream = file.getInputStream();
            // 上传到minio服务器
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(fileName)
                    .stream(inputStream, -1L, 10485760L)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回地址
        resultMap.put("url", fileName);

        return resultMap;
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public Map <String, String> uploadBlob(File file) {
        Map <String, String> resultMap = new HashMap <>();
        System.out.println(file.getName() + file.getPath());
//        String filename = FileUtils.extractUploadFilename(file);
//        +  file.getOriginalFilename()
        String fileName = "" + UUID.randomUUID();
        FileItem fileItem = createFileItem(file.getPath(), file.getName());
        MultipartFile f = new CommonsMultipartFile(fileItem);

        try {
            InputStream inputStream = f.getInputStream();
            // 上传到minio服务器
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(fileName)
                    .stream(inputStream, -1L, 10485760L)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回地址
        resultMap.put("url", fileName);

        return resultMap;
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名
     * @param delete   是否删除
     * @throws IOException
     */
    public void fileDownload(@RequestParam(name = "fileName") String fileName,
                             @RequestParam(defaultValue = "false") Boolean delete,
                             HttpServletResponse response) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (StringUtils.isBlank(fileName)) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                String data = "文件下载失败";
                OutputStream ps = response.getOutputStream();
                ps.write(data.getBytes("UTF-8"));
                return;
            }

            outputStream = response.getOutputStream();
            // 获取文件对象
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(this.bucketName).object(fileName).build());
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/") + 1), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            // 输出文件
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            inputStream.close();
            // 判断：下载后是否同时删除minio上的存储文件
            if (BooleanUtils.isTrue(delete)) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(this.bucketName).object(fileName).build());
            }
        } catch (Throwable ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            try {
                OutputStream ps = response.getOutputStream();
                ps.write(data.getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查看文件对象
     *
     * @param bucketName 存储bucket名称
     * @return 存储bucket内文件对象信息
     */
    public List <ObjectItem> listObjects(String bucketName) {
        Iterable <Result <Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        List <ObjectItem> objectItems = new ArrayList <>();
        try {
            for (Result <Item> result : results) {
                Item item = result.get();

                ObjectItem objectItem = new ObjectItem();
                objectItem.setObjectName(item.objectName());
                objectItem.setSize(item.size());

                objectItems.add(objectItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectItems;
    }

    /**
     * 批量删除文件对象
     *
     * @param bucketName 存储bucket名称
     * @param objects    对象名称集合
     */
    public Map <String, String> removeObjects(String bucketName, List <String> objects) {
        Map <String, String> resultMap = new HashMap <>();

        List <DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
        try {
            Iterable <Result <DeleteError>> removeObjects = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
            resultMap.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("mes", "网络异常，删除失败");
        }
        return resultMap;
    }

    public Map <String, String> removeObject(String objects) {
        objects=objects.substring(objects.indexOf("photo/",1) + 6, objects.length());
        Map <String, String> resultMap = new HashMap <>();
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(objects)
                    .build());

//        List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
//        try {
//            Iterable<Result<DeleteError>> removeObjects=minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
            resultMap.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("mes", "网络异常，删除失败");
        }
        return resultMap;
    }


    public Map <String, String> uploadBlobTest(List <String> fileList, Long l1, String type) {
        Map <String, String> resultMap = new HashMap <>();
        fileList.forEach(file -> {
            String ilk = type.equals("Invitation") ? "invitation" : "avatar";
            Base64.Decoder decoder1 = Base64.getMimeDecoder();
            file = file.substring(file.indexOf(",", 1) + 1, file.length()).trim();
            // 去掉base64前缀 data:image/jpeg;base64,
            byte[] b = decoder1.decode(file);
            // 处理数据
            for (int j = 0; j < b.length; ++j) {
                if (b[j] < 0) {
                    b[j] += 256;
                }
            }
            try {
                InputStream inputStream = new ByteArrayInputStream(b);
                String string = UUID.randomUUID().toString().concat(".png");

                MultipartFile file1 = new MockMultipartFile(string, string, "image/png", inputStream);
                inputStream.close();
                String fileName = ilk + "/" + l1 +"/" + UUID.randomUUID() + file1.getOriginalFilename();
//            InputStream inputStream = FileUtils.getImageStream(file);
                // 上传到minio服务器
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(this.bucketName)
                        .object(fileName)
                        .stream(file1.getInputStream(), -1L, 10485760L)
                        .contentType(file1.getContentType())
                        .build());
                // 返回地址
                resultMap.put("url", "http://localhost:9000/photo/"+ fileName);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        });
        return resultMap;
    }

    public List<String> getPhotoList(String type,Long id) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().prefix(type+"/"+id+"/").bucket(this.bucketName).build());
        List<String> list = new ArrayList<>();
        results.forEach(r->{
            try {
                Item item = r.get();
                list.add("http://localhost:9000/photo/"+item.objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return list;
    }
}

package com.ruoyi.minio.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xiaozq
 * @Date 2022/11/17 9:54
 * <p>@Description: 注意：这里不能用@Data,需手动写gitter，setter
 * reason：与@ConfigurationProperties一起用的时候，配置文件的值不能赋值给对应类属性，属性值均为null</p>
 */
@Configuration
//@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String server="http://127.0.0.1";

    private int port=9000;

    private String accessKey="minioadmin";

    private String secretKey="minioadmin";

    private String bucket="photo";


    private String urlPrefix="/minio/";

    /**
     * 创建minio连接对象
     * @return
     */
    @Bean
    public MinioClient minioClient(){
        return  MinioClient.builder()
                           .endpoint(server,port,false)
                           .credentials(accessKey,secretKey)
                           .build();
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }


    public void setUrlPrefix(String urlPrefix) { this.urlPrefix = urlPrefix; }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucket() {
        return bucket;
    }
}

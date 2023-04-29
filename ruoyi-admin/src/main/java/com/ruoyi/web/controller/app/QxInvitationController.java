package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.app.service.IQxLikesService;
import com.ruoyi.app.service.IQxSubjectService;
import com.ruoyi.app.service.IQxUserService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxLikes;
import com.ruoyi.common.core.domain.app.QxSubject;
import com.ruoyi.common.core.domain.app.QxUser;
import com.ruoyi.common.core.domain.app.vo.QxInvitationVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.app.service.IQxInvitationService;
import com.ruoyi.minio.template.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/app/invitation")
public class QxInvitationController extends BaseController
{
    @Autowired
    private IQxInvitationService qxInvitationService;
    @Autowired
    private MinioTemplate miniotemplate;
    @Autowired
    private IQxSubjectService qxSubjectService;
    @Autowired
    private IQxUserService qxUserService;
    @Autowired
    private IQxLikesService qxLikesService;
    /**
     * 查询【帖子】列表
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxInvitation qxInvitation)
    {
        startPage();
        if (qxInvitation.getSubject()!=null){
            QueryWrapper<QxInvitation> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("subject",qxInvitation.getSubject());
            List<QxInvitation> list = qxInvitationService.list(queryWrapper);
            return getDataTable(list);
        }
        List<QxInvitation> list = qxInvitationService.selectQxInvitationList(qxInvitation);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QxInvitation qxInvitation)
    {
        List<QxInvitation> list = qxInvitationService.selectQxInvitationList(qxInvitation);
        ExcelUtil<QxInvitation> util = new ExcelUtil<QxInvitation>(QxInvitation.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 根据ID获取【单个帖子】详细信息
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qxInvitationService.getById(id));
    }
    /**
     * 根据ID获取【单个帖子】详细信息
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:query')")
    @GetMapping("/getInInvitation")
    public QxInvitation getInInvitation(String id) {
        return qxInvitationService.getById(id);
    }
    /**
     * 新增【帖子】
     */

    @PreAuthorize("@ss.appHasPermi('app:invitation:add')")
    @PostMapping
    public AjaxResult add(@RequestBody  QxInvitation qxInvitation) {
        for (String s : qxInvitation.getSubject().split(",")) {
            if (qxSubjectService.getById(s) == null) {
                return AjaxResult.warn("查询无此话题" + s);
            }
        }
        return toAjax(qxInvitationService.save(qxInvitation));
    }

    /**
     * 修改【帖子】
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:edit')")
    @Log(title = "【修改帖子】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxInvitation qxInvitation) {
        for (String s : qxInvitation.getSubject().split(",")) {
            if (qxSubjectService.getById(s)==null){
                return AjaxResult.warn("查询无此话题"+s);
            }}
        return toAjax(qxInvitationService.updateById(qxInvitation));
    }

    /**
     * 删除【帖子】
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:remove')")
    @Log(title = "【删除帖子】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qxInvitationService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 上传【图片】
     */
//    @PreAuthorize("@ss.appHasPermi('app:invitation:uploadPhoto')")
    @Log(title = "专家", businessType = BusinessType.UPDATE)
    @PostMapping ("/uploadPhoto")
    public AjaxResult uploadPhoto( @RequestParam("fileList") List<String> file,@RequestParam("textarea") String textarea) {
        Iterator<String> a=file.iterator();
        while (a.hasNext()){
            String  n=a.next();
            if (n.indexOf("data")!=-1){
                a.remove();
            }
        }
        QxInvitation qxInvitation=new QxInvitation();
        qxInvitation.setUserId(SecurityUtils.getLoginUser().getQxUser().getId());
        qxInvitation.setContent(textarea);
        qxInvitationService.save(qxInvitation);
        String type="Invitation";
        if(file!=null){
            miniotemplate.uploadBlobTest(file,qxInvitation.getId(),type);
        }
        return AjaxResult.success();
    }
    /**
     * 查询【帖子】列表
     */
//    @PreAuthorize("@ss.hasPermi('app:invitation:list')")
    @GetMapping("/plazaList")
    public TableDataInfo plazaList(QxInvitationVo qxInvitationvo,String area)
    {
        if (area!=null){
            qxInvitationvo.setQxUser( SecurityUtils.getLoginUser().getQxUser());
        }
        if (qxInvitationvo.getOthersId()!=null){
            return getDataTable(qxInvitationService.selectQxInVo(qxInvitationvo));
        }
        qxInvitationvo.setUserId(SecurityUtils.getLoginUser().getQxUser().getId());
        List<QxInvitationVo>qxInvitationVoList=qxInvitationService.selectQxInVo(qxInvitationvo);
        return getDataTable(qxInvitationVoList);
    }

    /**
     * 查询【帖子】列表
     */
//    @PreAuthorize("@ss.hasPermi('app:invitation:list')")
    @GetMapping("/InvitationLikes")
    public AjaxResult InvitationLikes(QxInvitationVo qxInvitationvo)
    {
        AjaxResult ajax = AjaxResult.success();
        startPage();
        qxInvitationvo.setLikeId(SecurityUtils.getLoginUser().getQxUser().getId());
        List<QxInvitationVo> likesList= qxInvitationService.selectQxInVo(qxInvitationvo);
        ajax.put("InvitationVoList",likesList);
        List<QxInvitationVo> InvitationVoList= qxInvitationService.selectQxInLike(qxInvitationvo);
        ajax.put("likesList",InvitationVoList);
        return success(ajax);
    }
    /**
     * 删除评论
     */
    @PreAuthorize("@ss.appHasPermi('app:invitation:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
    @GetMapping("/delete")
    public AjaxResult delete(String id) {
        QueryWrapper<QxLikes> wrapper = new QueryWrapper<>();
        wrapper.eq("type",1).eq("father_id",id);
        qxLikesService.remove(wrapper);
        return toAjax(qxInvitationService.removeById(id));
    }

}

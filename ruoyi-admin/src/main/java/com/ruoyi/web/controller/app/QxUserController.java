package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.QxUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.app.service.IQxUserService;
import com.ruoyi.minio.template.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/app/user")
public class QxUserController extends BaseController
{
    @Autowired
    private IQxUserService qxUserService;
    @Autowired
    private MinioTemplate miniotemplate;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.appHasPermi('app:user:loginUser')")
    @GetMapping("/loginUser")
    public AjaxResult loginUser()
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("rows",SecurityUtils.getLoginUser().getQxUser());
        return success(ajax);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.appHasPermi('app:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxUser qxUser)
    {
        startPage();
        List<QxUser> list = qxUserService.selectQxUserList(qxUser);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.appHasPermi('app:user:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QxUser qxUser)
    {
        List<QxUser> list = qxUserService.selectQxUserList(qxUser);
        ExcelUtil<QxUser> util = new ExcelUtil<QxUser>(QxUser.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    @PreAuthorize("@ss.appHasPermi('app:user:query')")
    @GetMapping()
    public AjaxResult getInfo(Long userId) {
        Long id =SecurityUtils.getLoginUser().getQxUser().getId();
        QxUser qxUser = SecurityUtils.getLoginUser().getQxUser();
        if(userId!=null) {
            qxUser=qxUserService.getOne(Wrappers.<QxUser>lambdaQuery().eq(QxUser::getId,userId));
            id = userId;
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("rows",qxUser);
        ajax.put("age",qxUserService.logicGetById(id));
        return ajax;
    }

//    /**
//     * 获取【请填写功能名称】详细信息
//     */
//    @PreAuthorize("@ss.appHasPermi('app:user:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return success(qxUserService.getById(id));
//    }

    /**
     * 新增【请填写功能名称】
     */
    @Log(title = "【注册用户】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxUser qxUser) {
        AjaxResult ajax = AjaxResult.success();
        QueryWrapper queryWrapper =new QueryWrapper();
        List <String> fileList=new ArrayList <>();
        String Avatar=qxUser.getAvatar();
        String phone =qxUser.getPhone();
        qxUser.setPhone(phone);
        qxUser.setPassword(SecurityUtils.encryptPassword(qxUser.getPassword()));
        qxUser.setArea("北京市-市辖区-东城区");
        qxUser.setBirthday("2000-01-01");
        qxUser.setStatus("0");
        qxUser.setAvatar("");
        qxUserService.save(qxUser);
        queryWrapper.select("id").eq("phone",qxUser.getPhone());
        QxUser qxUser1=qxUserService.getOne(queryWrapper);
        qxUser.setAvatar(Avatar);
        qxUser.setId(qxUser1.getId());
        fileList.add(qxUser.getAvatar());
        Map<String, String> map= miniotemplate.uploadBlobTest(fileList,qxUser.getId(),"avatar");
        qxUser.setAvatar(map.get("url"));
        ajax.put("avatar",qxUser.getAvatar());
        return toAjax(qxUserService.updateById(qxUser));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.appHasPermi('app:user:edit')")
    @Log(title = "【用户修改】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxUser qxUser) {
        AjaxResult ajax = AjaxResult.success();
        Long id = SecurityUtils.getLoginUser().getQxUser().getId();
        qxUser.setId(id);
        List <String> fileList=new ArrayList <>();
        fileList.add(qxUser.getAvatar());
        if(!qxUser.getAvatar().substring(0,4).equals("http")){
            QxUser qxUserOld=qxUserService.getById(qxUser);
            if(qxUserOld.getAvatar()!=null&&qxUserOld.getAvatar().length()>0){
                miniotemplate.removeObject(qxUserOld.getAvatar());
            }
            Map<String, String> map= miniotemplate.uploadBlobTest(fileList,id,"avatar");
            qxUser.setAvatar(map.get("url"));
        }
        ajax.put("avatar",qxUser.getAvatar());
        qxUserService.updateById(qxUser);
        return ajax;
    }

    @Log(title = "【修改密码】", businessType = BusinessType.UPDATE)
    @PutMapping("/uppwd")
    public AjaxResult upUserPwd(@RequestBody QxUser qxUser) {
        UpdateWrapper<QxUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password",SecurityUtils.encryptPassword(qxUser.getPassword())).eq("phone",qxUser.getPhone());
        return toAjax(qxUserService.update(updateWrapper));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.appHasPermi('app:user:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qxUserService.removeByIds(Arrays.asList(ids)));
    }
    /**
     * 修改【用户背景图】
     */
    @PreAuthorize("@ss.appHasPermi('app:user:ground')")
    @Log(title = "【用户背景图修改】", businessType = BusinessType.UPDATE)
    @PutMapping("/ground")
    public AjaxResult editGround(@RequestBody String ground) {
        QxUser qxUser=new QxUser();
        qxUser.setId(SecurityUtils.getLoginUser().getQxUser().getId());
        qxUser=qxUserService.getById(qxUser);
        if(qxUser.getBground()!=null&&qxUser.getBground().length()>0){
            miniotemplate.removeObject(qxUser.getBground());
        }
        List <String> fileList=new ArrayList <>();
        fileList.add(ground);
        Map <String, String>map=miniotemplate.uploadBlobTest(fileList,qxUser.getId(),"avatar");
        qxUser.setBground(map.get("url"));
        return toAjax( qxUserService.updateById(qxUser));
    }

}

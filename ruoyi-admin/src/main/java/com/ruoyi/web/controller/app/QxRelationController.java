package com.ruoyi.web.controller.app;

import com.ruoyi.app.service.IQxRelationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.QxRelation;
import com.ruoyi.common.core.domain.app.vo.QxRelationVO;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关系Controller
 *
 * @author ruoyi
 * @date 2023-04-09
 */
@RestController
@RequestMapping("/app/relation")
public class QxRelationController extends BaseController {
    @Autowired
    private IQxRelationService qxRelationService;

    /**
     * 查询关系列表
     */
    @PreAuthorize("@ss.appHasPermi('app:relation:list')")
    @GetMapping("/list")
    public AjaxResult list(QxRelationVO qxRelation, String nickName) {
        AjaxResult ajax = AjaxResult.success();
        Long id = SecurityUtils.getLoginUser().getQxUser().getId();
        qxRelation.setFromId(id);
        List<QxRelation> typeOne = qxRelationService.selectQxRelationList(qxRelation, nickName);
        ajax.put("typeOne", typeOne);
        return success(ajax);
    }

    @PreAuthorize("@ss.appHasPermi('app:relation:listTwo')")
    @GetMapping("/listTwo")
    public AjaxResult listTwo(QxRelationVO qxRelation, String nickName) {
        AjaxResult ajax = AjaxResult.success();
        Long id = SecurityUtils.getLoginUser().getQxUser().getId();
        qxRelation.setToId(id);
        List<QxRelation> typeTwo = qxRelationService.selectQxRelationList(qxRelation, nickName);
        ajax.put("typeTwo", typeTwo);
        return success(ajax);
    }

    /**
     * 查询发帖人关系
     */
    @PreAuthorize("@ss.appHasPermi('app:relation:invitationUser')")
    @GetMapping("/invitationUser")
    public AjaxResult invitationUser(QxRelationVO qxRelation) {
        AjaxResult ajax = AjaxResult.success();
        Long id = SecurityUtils.getLoginUser().getQxUser().getId();
        ajax.put("invitationRelation", qxRelationService.invitationUser(id, qxRelation.getUserId()));
        return success(ajax);
    }

    /**
     * 新增关系
     */
    @PreAuthorize("@ss.appHasPermi('app:relation:add')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody QxRelationVO qxRelation) {
        return toAjax(qxRelationService.verifySave(qxRelation));
    }

    /**
     * 新增关系
     */
    @PreAuthorize("@ss.appHasPermi('app:relation:add')")
    @PostMapping("/invitationAdd")
    public AjaxResult invitationAdd(@RequestBody QxRelationVO qxRelation) {
        return toAjax(qxRelationService.invitationAdd(qxRelation));
    }
}

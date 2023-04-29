package com.ruoyi.web.controller.app;

import com.ruoyi.app.service.IQxCommentService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.vo.QxCommentVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 评论Controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/app/comment")
public class QxCommentController extends BaseController {
    @Autowired
    private IQxCommentService qxCommentService;

    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.appHasPermi('app:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxComment qxComment) {
        List<QxCommentVO> list = qxCommentService.selectQxCommentList(qxComment);
        return getDataTable(list);
    }

    /**
     * 新增评论
     */
    @PreAuthorize("@ss.appHasPermi('app:comment:add')")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody QxComment qxComment) {
        qxCommentService.verifySave(qxComment);
        return success();

    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.appHasPermi('app:comment:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qxCommentService.removeByIds(Arrays.asList(ids)));
    }
    /**
     * 删除评论
     */
    @PreAuthorize("@ss.appHasPermi('app:comment:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
    @GetMapping("/delete")
    public AjaxResult delete(String id) {
        return toAjax(qxCommentService.removeById(id));
    }
}

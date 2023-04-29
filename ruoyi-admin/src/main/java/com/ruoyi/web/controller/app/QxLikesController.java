package com.ruoyi.web.controller.app;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.app.QxLikes;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.app.service.IQxLikesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 点赞Controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/app/likes")
public class QxLikesController extends BaseController
{
    @Autowired
    private IQxLikesService qxLikesService;

    /**
     * 查询点赞列表
     */
    @PreAuthorize("@ss.appHasPermi('app:likes:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxLikes qxLikes)
    {
        startPage();
        List<QxLikes> list = qxLikesService.selectQxLikesList(qxLikes);
        return getDataTable(list);
    }

    /**
     * 新增点赞
     */
    @PreAuthorize("@ss.appHasPermi('app:likes:add')")
    @Log(title = "点赞", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody QxLikes qxLikes) {
        return toAjax(qxLikesService.verifySave(qxLikes));
    }
}

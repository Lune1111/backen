package com.ruoyi.web.controller.app;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.app.QxSubject;
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
import com.ruoyi.app.service.IQxSubjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 话题Controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/app/subject")
public class QxSubjectController extends BaseController
{
    @Autowired
    private IQxSubjectService qxSubjectService;

    /**
     * 查询话题列表
     */
    @PreAuthorize("@ss.appHasPermi('app:subject:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxSubject qxSubject)
    {
        startPage();
        List<QxSubject> list = qxSubjectService.selectQxSubjectList(qxSubject);
        return getDataTable(list);
    }

    /**
     * 导出话题列表
     */
    @PreAuthorize("@ss.appHasPermi('app:subject:export')")
    @Log(title = "话题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QxSubject qxSubject)
    {
        List<QxSubject> list = qxSubjectService.selectQxSubjectList(qxSubject);
        ExcelUtil<QxSubject> util = new ExcelUtil<QxSubject>(QxSubject.class);
        util.exportExcel(response, list, "话题数据");
    }

    /**
     * 获取话题详细信息
     */
    @PreAuthorize("@ss.appHasPermi('app:subject:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qxSubjectService.getById(id));
    }

    /**
     * 新增话题
     */
    @PreAuthorize("@ss.appHasPermi('app:subject:add')")
    @Log(title = "话题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxSubject qxSubject) {
        return toAjax(qxSubjectService.save(qxSubject));
    }

    /**
     * 修改话题
     */
    @PreAuthorize("@ss.appHasPermi('app:subject:edit')")
    @Log(title = "话题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxSubject qxSubject) {
        return toAjax(qxSubjectService.updateById(qxSubject));
    }

    /**
     * 删除话题
     */
    @PreAuthorize("@ss.appHasPermi('app:subject:remove')")
    @Log(title = "话题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qxSubjectService.removeByIds(Arrays.asList(ids)));
    }
}

package com.ruoyi.web.controller.app;

import com.ruoyi.app.service.IQxReportService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.QxReport;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 举报Controller
 *
 * @author ruoyi
 * @date 2023-04-23
 */
@RestController
@RequestMapping("/app/report")
public class QxReportController extends BaseController
{
    @Autowired
    private IQxReportService qxReportService;

    /**
     * 查询举报列表
     */
    @PreAuthorize("@ss.appHasPermi('app:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxReport qxReport)
    {
        startPage();
        List<QxReport> list = qxReportService.selectQxReportList(qxReport);
        return getDataTable(list);
    }

    /**
     * 新增举报
     */
    @PreAuthorize("@ss.appHasPermi('app:report:add')")
    @Log(title = "举报", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody QxReport qxReport) {
        return toAjax(qxReportService.verifySave(qxReport));
    }

}

package com.ruoyi.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxReport;

import java.util.List;

/**
 * 举报Service接口
 *
 * @author ruoyi
 * @date 2023-04-23
 */
public interface IQxReportService extends IService<QxReport>
{

    /**
     * 查询举报列表
     *
     * @param qxReport 举报
     * @return 举报集合
     */
    public List<QxReport> selectQxReportList(QxReport qxReport);

    int verifySave(QxReport qxReport);
}

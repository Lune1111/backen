package com.ruoyi.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxReport;

import java.util.List;

/**
 * 举报Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-23
 */
public interface QxReportMapper extends BaseMapper<QxReport>
{
    /**
     * 查询举报列表
     *
     * @param qxReport 举报
     * @return 举报集合
     */
    public List<QxReport> selectQxReportList(QxReport qxReport);

    int verifyByInvitationDelete(List<QxInvitation> qxInvitationList);

    int verifyByCommentDelete(List<QxComment> qxCommentList1);
}

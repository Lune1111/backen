package com.ruoyi.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.app.mapper.QxCommentMapper;
import com.ruoyi.app.mapper.QxInvitationMapper;
import com.ruoyi.app.mapper.QxLikesMapper;
import com.ruoyi.app.mapper.QxReportMapper;
import com.ruoyi.app.service.IQxCommentService;
import com.ruoyi.app.service.IQxInvitationService;
import com.ruoyi.app.service.IQxReportService;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxReport;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 举报Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-23
 */
@Service
@Component("reportTask")
public class QxReportServiceImpl extends ServiceImpl<QxReportMapper, QxReport> implements IQxReportService {
    @Autowired
    private IQxInvitationService qxInvitationService;
    @Autowired
    private IQxCommentService qxCommentService;
    @Autowired
    QxInvitationMapper invitationMapper;
    @Autowired
    QxCommentMapper commentMapper;
    @Autowired
    QxLikesMapper likesMapper;
    @Autowired
    QxReportMapper reportMapper;


    /**
     * 查询举报列表
     *
     * @param qxReport 举报
     * @return 举报
     */
    @Override
    public List<QxReport> selectQxReportList(QxReport qxReport) {
        return baseMapper.selectQxReportList(qxReport);
    }

    @Override
    @Transactional
    public int verifySave(QxReport qxReport) {
        qxReport.setUserId(SecurityUtils.getLoginUser().getQxUser().getId());
        if (qxReport.getType().equals("1")) {
            qxInvitationService.update(Wrappers.<QxInvitation>lambdaUpdate()
                    .setSql("report_number=report_number+1")
                    .eq(QxInvitation::getId, qxReport.getFatherId()));
        } else {
            qxCommentService.update(Wrappers.<QxComment>lambdaUpdate()
                    .setSql("report_number=report_number+1")
                    .eq(QxComment::getId, qxReport.getFatherId()));
        }
        return baseMapper.insert(qxReport);
    }

    @Transactional
    public void deleteTask() {
        List<QxInvitation> qxInvitationList = qxInvitationService.list(Wrappers.<QxInvitation>lambdaQuery().ge(QxInvitation::getReportNumber, 2));
        if (!qxInvitationList.isEmpty()) {
            invitationMapper.verifyDelete(qxInvitationList);//删除帖子表
            likesMapper.verifyByInvitationDelete(qxInvitationList);//删除点赞表帖子id符合的
            reportMapper.verifyByInvitationDelete(qxInvitationList);//删除举报表帖子id符合的
            List<QxComment> qxCommentList1 = commentMapper.selectList(Wrappers.<QxComment>lambdaQuery().in(QxComment::getInvitationId, qxInvitationList.stream().map(QxInvitation::getId).collect(Collectors.toList())));
            if (!qxCommentList1.isEmpty()) {
                commentMapper.deleteByList(qxCommentList1);
                likesMapper.verifyByCommentDelete(qxCommentList1);
                reportMapper.verifyByCommentDelete(qxCommentList1);//删除举报表帖子id符合的
            }
        }
        List<QxComment> qxCommentList = commentMapper.selectList(Wrappers.<QxComment>lambdaQuery().ge(QxComment::getReportNumber, 2));
        if (!qxCommentList.isEmpty()) {
            commentMapper.deleteByList(qxCommentList);
            likesMapper.verifyByCommentDelete(qxCommentList);
            reportMapper.verifyByCommentDelete(qxCommentList);//删除举报表帖子id符合的
        }
    }
}

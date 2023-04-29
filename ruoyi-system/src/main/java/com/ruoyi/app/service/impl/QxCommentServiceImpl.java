package com.ruoyi.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.app.mapper.QxCommentMapper;
import com.ruoyi.app.mapper.QxLikesMapper;
import com.ruoyi.app.service.IQxCommentService;
import com.ruoyi.app.utils.SensitiveWordsUtil;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.vo.QxCommentVO;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
@Component("commentTask")
public class QxCommentServiceImpl extends ServiceImpl<QxCommentMapper, QxComment> implements IQxCommentService {
    @Autowired
    private QxLikesMapper qxLikesMapper;
    @Autowired
    private QxInvitationServiceImpl qxInvitationService;

    /**
     * 查询评论列表
     *
     * @param qxComment 评论
     * @return 评论
     */
    @Override
    public List<QxCommentVO> selectQxCommentList(QxComment qxComment) {
        qxComment.setUserId(SecurityUtils.getLoginUser().getQxUser().getId());
        List<QxCommentVO> qxCommentList = baseMapper.selectQxCommentList(qxComment);
          qxCommentList
                .forEach(j->{
                    j.setDate(DateUtils.verifyDate(j.getCreateTime()));
                });
        List<QxCommentVO> qxCommentList1=qxCommentList.stream().filter(x -> x.getParentId() == 0).collect(Collectors.toList());
        for (int i = 0; i < qxCommentList1.size(); i++) {
            int finalI = i;
            qxCommentList1.get(i).setQxCommentList(qxCommentList.stream().filter(x -> x.getParentId().equals(qxCommentList1.get(finalI).getId())).collect(Collectors.toList()));
        }
        return qxCommentList1;
    }

    @Override
    @Transactional
    public int verifySave(QxComment qxComment) {
        if (new SensitiveWordsUtil().sensitiveWord(qxComment.getContent()))
            throw new ServiceException("该评论包含敏感词");
        qxComment.setUserId(SecurityUtils.getLoginUser().getQxUser().getId());
        qxInvitationService.update(Wrappers.<QxInvitation>lambdaUpdate()
                .setSql("comment_number=comment_number+1")
                .eq(QxInvitation::getId, qxComment.getInvitationId()));
        return baseMapper.insert(qxComment);
    }

    @Transactional
    public void deleteTask() {
        QxComment qxComment = new QxComment();
        qxComment.setDeleted(1);
        List<QxComment> qxCommentList = baseMapper.selectQxComment(qxComment);
        while (!qxCommentList.isEmpty()) {
            baseMapper.deleteByList(qxCommentList);
            qxLikesMapper.deleteByList(qxCommentList);
            List<Long> collect = qxCommentList.stream().map(QxComment::getId).collect(Collectors.toList());
            qxCommentList = baseMapper.selectList(Wrappers.<QxComment>lambdaQuery().in(QxComment::getParentId, collect));
        }
    }
}

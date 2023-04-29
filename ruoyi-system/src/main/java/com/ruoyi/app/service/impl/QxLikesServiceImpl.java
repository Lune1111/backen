package com.ruoyi.app.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.app.service.IQxCommentService;
import com.ruoyi.app.service.IQxInvitationService;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxLikes;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.app.mapper.QxLikesMapper;
import com.ruoyi.app.service.IQxLikesService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 点赞Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
public class QxLikesServiceImpl extends ServiceImpl<QxLikesMapper, QxLikes> implements IQxLikesService {

    @Autowired
    private IQxCommentService qxCommentService;
    @Autowired
    private IQxInvitationService qxInvitationService;

    /**
     * 查询点赞列表
     *
     * @param qxLikes 点赞
     * @return 点赞
     */
    @Override
    public List<QxLikes> selectQxLikesList(QxLikes qxLikes) {
        return baseMapper.selectQxLikesList(qxLikes);
    }

    @Override
    @Transactional
    public int verifySave(QxLikes qxLikes) {
        qxLikes.setUserId(SecurityUtils.getLoginUser().getQxUser().getId());
        List<QxLikes> qxLikesList = baseMapper.selectQxLikesList(qxLikes);
        String num = "+1";
        if (qxLikesList.isEmpty()) {
            if (qxLikes.getType().equals("1")) {
                qxInvitationService.update(Wrappers.<QxInvitation>lambdaUpdate()
                        .setSql("praise_number=praise_number" + num + "")
                        .eq(QxInvitation::getId, qxLikes.getFatherId()));
            }
            else {
                qxCommentService.update(Wrappers.<QxComment>lambdaUpdate()
                        .setSql("praise_number=praise_number" + num + "")
                        .eq(QxComment::getId, qxLikes.getFatherId()));
            }
            return baseMapper.insert(qxLikes);
        }
        if (qxLikesList.get(0).getDeleted() == 0) {
            num = "-1";
        }
        qxInvitationService.update(Wrappers.<QxInvitation>lambdaUpdate()
                .setSql("praise_number=praise_number" + num + "")
                .eq(QxInvitation::getId, qxLikes.getFatherId()));
        qxCommentService.update(Wrappers.<QxComment>lambdaUpdate()
                .setSql("praise_number=praise_number" + num + "")
                .eq(QxComment::getId, qxLikes.getFatherId()));
        return baseMapper.verifyUpdate(qxLikesList.get(0));
    }

}

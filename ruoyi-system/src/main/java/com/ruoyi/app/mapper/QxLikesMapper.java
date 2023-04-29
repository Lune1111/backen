package com.ruoyi.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxLikes;

import java.util.List;

/**
 * 点赞Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface QxLikesMapper extends BaseMapper<QxLikes>
{
    /**
     * 查询点赞列表
     *
     * @param qxLikes 点赞
     * @return 点赞集合
     */
    public List<QxLikes> selectQxLikesList(QxLikes qxLikes);

    int deleteByList(List<QxComment> qxComments);

    int verifyUpdate(QxLikes qxLikes);

    int verifyByCommentDelete(List<QxComment> qxCommentList1);

    int verifyByInvitationDelete(List<QxInvitation> qxInvitationList);
}

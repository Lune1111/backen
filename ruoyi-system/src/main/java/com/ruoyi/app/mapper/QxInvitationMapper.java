package com.ruoyi.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.vo.QxInvitationVo;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface QxInvitationMapper extends BaseMapper<QxInvitation>
{
    /**
     * 查询【请填写功能名称】列表
     *
     * @param qxInvitation 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<QxInvitation> selectQxInvitationList(QxInvitation qxInvitation);

    /**
     * 查询【帖子，关联帖子的用户】
     *  @return 【帖子，关联帖子的用户】集合
     */
    List<QxInvitationVo> selectQxInVo(QxInvitationVo qxinvitationvo);
    /**
     * 查询【用户点赞帖子】
     *  @return 【用户点赞帖子】集合
     */
    List<QxInvitationVo> selectQxInLike(QxInvitationVo qxinvitationvo);

    int verifyDelete(List<QxInvitation> qxInvitationList);
}

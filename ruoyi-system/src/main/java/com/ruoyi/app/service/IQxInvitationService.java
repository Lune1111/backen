package com.ruoyi.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.vo.QxInvitationVo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface IQxInvitationService extends IService<QxInvitation>
{

    /**
     * 查询【用户，话题，查询帖子】列表
     *
     * @param qxInvitation 【用户，话题，查询帖子】
     * @return 【用户，话题，查询帖子】集合
     */
    List<QxInvitation> selectQxInvitationList(QxInvitation qxInvitation);
    /**
     * 查询【帖子，关联帖子的用户】
     *  @return 【帖子，关联帖子的用户】集合
     */
    List<QxInvitationVo> selectQxInVo(QxInvitationVo qxInvitationvo);
    /**
     * 查询【用户点赞帖子】
     *  @return 【用户点赞帖子】集合
     */
    List<QxInvitationVo> selectQxInLike(QxInvitationVo qxinvitationvo);

}

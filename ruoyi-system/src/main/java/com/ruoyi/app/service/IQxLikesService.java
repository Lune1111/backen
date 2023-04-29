package com.ruoyi.app.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxLikes;

/**
 * 点赞Service接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface IQxLikesService extends IService<QxLikes>
{

    /**
     * 查询点赞列表
     *
     * @param qxLikes 点赞
     * @return 点赞集合
     */
    public List<QxLikes> selectQxLikesList(QxLikes qxLikes);

    int verifySave(QxLikes qxLikes);
}

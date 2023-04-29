package com.ruoyi.app.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.vo.QxCommentVO;

/**
 * 评论Service接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface IQxCommentService extends IService<QxComment>
{

    /**
     * 查询评论列表
     *
     * @param qxComment 评论
     * @return 评论集合
     */
    public List<QxCommentVO> selectQxCommentList(QxComment qxComment);

    int verifySave(QxComment qxComment);
}

package com.ruoyi.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxComment;
import com.ruoyi.common.core.domain.app.vo.QxCommentVO;

import java.util.List;

/**
 * 评论Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface QxCommentMapper extends BaseMapper<QxComment> {
    /**
     * 查询评论列表
     *
     * @param qxComment 评论
     * @return 评论集合
     */
    public List<QxCommentVO> selectQxCommentList(QxComment qxComment);

    int deleteByList(List<QxComment> qxComments);

    List<QxComment> selectQxComment(QxComment qxComment);
}

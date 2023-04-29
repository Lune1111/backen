package com.ruoyi.common.core.domain.app.vo;

import com.ruoyi.common.core.domain.app.QxComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 关系VO
 *
 * @author 王楠
 * @date 2023-04-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxCommentVO extends QxComment {
    private static final long serialVersionUID = 1L;
    /**
     * 回复用户昵称
     */
    private String replyName;
    /**
     * 回复者昵称
     */
    private String nickName;
    /**
     * 是否点赞
     */
    private Integer isLike;
    /**
     * 是否举报
     */
    private Integer isReport;

    private String avatar;

    private List<QxComment> qxCommentList;

    private String date;

}

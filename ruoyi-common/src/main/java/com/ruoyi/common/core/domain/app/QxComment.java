package com.ruoyi.common.core.domain.app;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 评论对象 qx_comment
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户主键
     */
    @Excel(name = "用户主键")
    private Long userId;
    /**
     * 回复的userId
     */
    @Excel(name = "回复的userId")
    private Long replyUserId;
    /**
     * 帖子id
     */
    @Excel(name = "帖子id")
    private Long invitationId;
    /**
     * 评论父id
     */
    @Excel(name = "评论父id")
    private Long parentId;
    /**
     * 评论内容
     */
    @Excel(name = "评论内容")
    private String content;
    /**
     * 点赞数
     */
    @Excel(name = "点赞数")
    private Long praiseNumber;

    /** 举报数 */
    @Excel(name = "举报数")
    private Integer reportNumber;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("content", getContent())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("deleted", getDeleted())
                .toString();
    }
}

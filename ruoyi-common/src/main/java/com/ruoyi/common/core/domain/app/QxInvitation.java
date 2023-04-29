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
 * 【请填写功能名称】对象 qx_invitation
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxInvitation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;
    /** 正文 */
    @Excel(name = "正文")
    private String content;
    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long praiseNumber;
    /** 评论数 */
    @Excel(name = "评论数")
    private Long commentNumber;
    /** 话题ids */
    @Excel(name = "话题ids")
    private String subject;
    /** 是否刪除 */
    @Excel(name = "刪除狀態")
    private Integer deleted;
    /** 举报数 */
    @Excel(name = "举报数")
    private Integer reportNumber;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("content", getContent())
            .append("praiseNumber", getPraiseNumber())
            .append("commentNumber", getCommentNumber())
            .append("subject", getSubject())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("deleted", getDeleted())
        .toString();
    }
}

package com.ruoyi.common.core.domain.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 点赞对象 qx_likes
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxLikes extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;
    /** 父id */
    @Excel(name = "父id")
    private Long fatherId;
    /** 1帖子2评论 */
    @Excel(name = "1帖子2评论")
    private String type;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("fatherId", getFatherId())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("deleted", getDeleted())
        .toString();
    }
}

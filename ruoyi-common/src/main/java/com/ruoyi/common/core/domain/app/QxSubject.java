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
 * 话题对象 qx_subject
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxSubject extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 姓名 */
    @Excel(name = "姓名")
    private String name;
    /** 人数 */
    @Excel(name = "人数")
    private Long num;
    /** 图片 */
    @Excel(name = "图片")
    private String pic;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("num", getNum())
            .append("pic", getPic())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("deleted", getDeleted())
        .toString();
    }
}

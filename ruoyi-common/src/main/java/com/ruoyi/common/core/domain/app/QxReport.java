package com.ruoyi.common.core.domain.app;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 举报对象 qx_report
 *
 * @author ruoyi
 * @date 2023-04-23
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用户主键 */
    @Excel(name = "用户主键")
    private Long userId;
    /** 举报类型 */
    @Excel(name = "举报类型")
    private String type;
    /** 父Id */
    @Excel(name = "父Id")
    private Long fatherId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
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

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
 * 关系对象 qx_relation
 *
 * @author ruoyi
 * @date 2023-04-09
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxRelation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 发起方
     */
    @Excel(name = "发起方")
    private Long fromId;
    /**
     * 接收方
     */
    @Excel(name = "接收方")
    private Long toId;
    /**
     * 关系类型
     */
    @Excel(name = "关系类型")
    private String type;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fromId", getFromId())
                .append("toId", getToId())
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

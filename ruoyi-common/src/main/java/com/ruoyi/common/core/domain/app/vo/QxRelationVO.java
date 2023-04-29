package com.ruoyi.common.core.domain.app.vo;

import com.ruoyi.common.core.domain.app.QxRelation;
import com.ruoyi.common.core.domain.app.QxUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关系VO
 *
 * @author 王楠
 * @date 2023-04-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxRelationVO extends QxRelation {

    private static final long serialVersionUID = 1L;

    /**
     * qx用户VO对象
     */
    private QxUser qxUser;
    /**
     * 粉丝关注页面状态
     */
    private Long current;

    private Long userId;

    private Long relation;
}

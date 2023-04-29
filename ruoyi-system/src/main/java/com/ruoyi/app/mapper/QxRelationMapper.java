package com.ruoyi.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxRelation;
import com.ruoyi.common.core.domain.app.vo.QxRelationVO;

import java.util.List;

/**
 * 关系Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-09
 */
public interface QxRelationMapper extends BaseMapper<QxRelation>
{
    /**
     * 查询关系列表
     *
     * @param qxRelation 关系
     * @return 关系集合
     */
    public List<QxRelation> selectQxRelationList(QxRelation qxRelation);

    int verifyUpdate(QxRelation qxRelation);

    QxRelation selectIsEmpty(QxRelationVO qxRelation);
}

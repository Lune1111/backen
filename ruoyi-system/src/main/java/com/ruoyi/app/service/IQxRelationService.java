package com.ruoyi.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxRelation;
import com.ruoyi.common.core.domain.app.vo.QxRelationVO;

import java.util.List;

/**
 * 关系Service接口
 *
 * @author ruoyi
 * @date 2023-04-09
 */
public interface IQxRelationService extends IService<QxRelation> {

    /**
     * 查询关系列表
     *
     * @param qxRelation 关系
     * @return 关系集合
     */
    public List<QxRelation> selectQxRelationList(QxRelationVO qxRelation, String nickName);

    int verifySave(QxRelationVO qxRelation);

    Long invitationUser(Long id, Long userId);

    int invitationAdd(QxRelationVO qxRelation);
}

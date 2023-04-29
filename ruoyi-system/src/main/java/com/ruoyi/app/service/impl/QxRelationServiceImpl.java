package com.ruoyi.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.app.mapper.QxRelationMapper;
import com.ruoyi.app.service.IQxRelationService;
import com.ruoyi.common.core.domain.app.QxRelation;
import com.ruoyi.common.core.domain.app.QxUser;
import com.ruoyi.common.core.domain.app.vo.QxRelationVO;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关系Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-09
 */
@Service
public class QxRelationServiceImpl extends ServiceImpl<QxRelationMapper, QxRelation> implements IQxRelationService {


    /**
     * 查询关系列表
     *
     * @param qxRelation 关系
     * @return 关系
     */
    @Override
    public List<QxRelation> selectQxRelationList(QxRelationVO qxRelation, String nickName) {
        QxUser qxUser = new QxUser();
        qxUser.setNickName(nickName);
        qxRelation.setQxUser(qxUser);
        return baseMapper.selectQxRelationList(qxRelation);
    }

    @Override
    @Transactional
    public int verifySave(QxRelationVO qxRelationVO) {
        if (qxRelationVO.getCurrent() == 0 || qxRelationVO.getCurrent() == 2) {
            decouplingDeleted(qxRelationVO);
            return baseMapper.verifyUpdate(qxRelationVO);
        }
        decoupling(qxRelationVO);
        QxRelation qxRelation1 = baseMapper.selectIsEmpty(qxRelationVO);
        if (qxRelation1 == null) {
            qxRelationVO.setDeleted(0);
            return baseMapper.insert(qxRelationVO);
        }
        decouplingDeleted(qxRelationVO);
        return baseMapper.verifyUpdate(qxRelation1);
    }

    void decoupling(QxRelationVO qxRelationVO) {
        Long fromId = qxRelationVO.getFromId();
        qxRelationVO.setFromId(qxRelationVO.getToId());
        qxRelationVO.setToId(fromId);
    }

    void decouplingDeleted(QxRelationVO qxRelationVO) {
        qxRelationVO.setDeleted(qxRelationVO.getDeleted() == 0 ? 1 : 0);
    }

    /**
     * 查询发帖人关系
     */
    @Override
    public Long invitationUser(Long id, Long userId) {
        QxRelation qxRelation = baseMapper.selectOne(Wrappers.<QxRelation>lambdaQuery().eq(QxRelation::getFromId, id).eq(QxRelation::getToId, userId));
        QxRelation qxRelationTwo = baseMapper.selectOne(Wrappers.<QxRelation>lambdaQuery().eq(QxRelation::getFromId, userId).eq(QxRelation::getToId, id));
        if (qxRelation != null && qxRelationTwo != null)
            return 0L;
        if (qxRelation != null)
            return 1L;
        if (qxRelationTwo != null)
            return 2L;
        return 3L;
    }

    @Override
    public int invitationAdd(QxRelationVO qxRelationVO) {
        qxRelationVO.setFromId(SecurityUtils.getLoginUser().getQxUser().getId());
        QxRelation qxRelation1 = baseMapper.selectIsEmpty(qxRelationVO);
        if (qxRelation1 == null) {
            return baseMapper.insert(qxRelationVO);
        }
        decouplingDeleted(qxRelationVO);
        return baseMapper.verifyUpdate(qxRelationVO);
    }
}

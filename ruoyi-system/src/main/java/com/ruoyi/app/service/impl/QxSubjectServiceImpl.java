package com.ruoyi.app.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.app.QxSubject;
import org.springframework.stereotype.Service;
import com.ruoyi.app.mapper.QxSubjectMapper;
import com.ruoyi.app.service.IQxSubjectService;

/**
 * 话题Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
public class QxSubjectServiceImpl extends ServiceImpl<QxSubjectMapper, QxSubject> implements IQxSubjectService
{


    /**
     * 查询话题列表
     *
     *
     * @param qxSubject 话题
     * @return 话题
     */
    @Override
    public List<QxSubject> selectQxSubjectList(QxSubject qxSubject)
    {
        return baseMapper.selectQxSubjectList(qxSubject);
    }

}

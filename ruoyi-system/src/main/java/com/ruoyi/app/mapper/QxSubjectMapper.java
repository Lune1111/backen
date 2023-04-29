package com.ruoyi.app.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxSubject;

/**
 * 话题Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface QxSubjectMapper extends BaseMapper<QxSubject>
{
    /**
     * 查询话题列表
     *
     * @param qxSubject 话题
     * @return 话题集合
     */
    public List<QxSubject> selectQxSubjectList(QxSubject qxSubject);

}

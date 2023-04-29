package com.ruoyi.app.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxSubject;

/**
 * 话题Service接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface IQxSubjectService extends IService<QxSubject>
{

    /**
     * 查询话题列表
     *
     * @param qxSubject 话题
     * @return 话题集合
     */
    public List<QxSubject> selectQxSubjectList(QxSubject qxSubject);

}

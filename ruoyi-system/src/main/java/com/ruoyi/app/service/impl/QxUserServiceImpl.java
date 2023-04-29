package com.ruoyi.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.app.QxUser;
import com.ruoyi.app.mapper.QxUserMapper;
import com.ruoyi.app.service.IQxUserService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Service
public class QxUserServiceImpl extends ServiceImpl<QxUserMapper, QxUser> implements IQxUserService
{


    /**
     * 查询【请填写功能名称】列表
     *
     *
     * @param qxUser 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<QxUser> selectQxUserList(QxUser qxUser)
    {
        return baseMapper.selectQxUserList(qxUser);
    }

    @Override
    public List<QxUser> selectLikeQxUserList(QxUser qxUser) {
        return baseMapper.selectLikeQxUserList(qxUser);
    }

    @Override
    public QxUser selectPassword(String password) {
        QueryWrapper<QxUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("phone",password);
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public QxUser logicGetById(Long id) {
        QxUser qxUser = baseMapper.selectById(id);
        qxUser.setBirthday(""+DateUtils.differentDaysByMillisecond(DateUtils.dateTime("yyyy-MM-dd",qxUser.getBirthday()),new Date()));
        return qxUser;
    }

}

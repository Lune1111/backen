package com.ruoyi.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.app.QxUser;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface IQxUserService extends IService<QxUser>
{

    /**
     * 查询【请填写功能名称】列表
     *
     * @param qxUser 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<QxUser> selectQxUserList(QxUser qxUser);
    public List<QxUser> selectLikeQxUserList(QxUser qxUser);

    public QxUser selectPassword(String password);

    QxUser logicGetById(Long id);
}

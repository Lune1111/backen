package com.ruoyi.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.QxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface QxUserMapper extends BaseMapper<QxUser>
{
    /**
     * 查询【请填写功能名称】列表
     *
     * @param qxUser 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<QxUser> selectLikeQxUserList(QxUser qxUser);
    public List<QxUser> selectQxUserList(QxUser qxUser);
}

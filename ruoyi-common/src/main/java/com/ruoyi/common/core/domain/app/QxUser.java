package com.ruoyi.common.core.domain.app;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 【请填写功能名称】对象 qx_user
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;
    /** 密码 */
    @Excel(name = "密码")
    private String password;
    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;
    /** 0:女 1:男 */
    @Excel(name = "0:女 1:男")
    private String sex;
    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private String birthday;
    /** 地区 所属地区 省市区县 */
    @Excel(name = "地区 所属地区 省市区县")
    private String area;
    /** 个人简介 */
    @Excel(name = "个人简介")
    private String intro;
    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;
    /** 角色背景图地址 */
    @Excel(name = "角色背景图地址")
    private String bground;

    private String status;
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("phone", getPhone())
            .append("password", getPassword())
            .append("nickName", getNickName())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("area", getArea())
            .append("intro", getIntro())
            .append("avatar", getAvatar())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("deleted", getDeleted())
            .append("bground", getBground())
        .toString();
    }
}

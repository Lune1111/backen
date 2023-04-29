package com.ruoyi.web.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.app.service.impl.QxUserServiceImpl;
import com.ruoyi.common.core.domain.app.QxUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private QxUserServiceImpl qxUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith("Login ")) {
            username = username.substring(6);
            SysUser user = userService.selectUserByUserName(username);
            if (StringUtils.isNull(user)) {
                log.info("登录用户：{} 不存在.", username);
                throw new ServiceException("登录用户：" + username + " 不存在");
            } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
                log.info("登录用户：{} 已被删除.", username);
                throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
            } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
                log.info("登录用户：{} 已被停用.", username);
                throw new ServiceException("对不起，您的账号：" + username + " 已停用");
            }
            return createLoginUser(user);
        }
        username = username.substring(9);
        QxUser qxUser = qxUserService.getOne(Wrappers.<QxUser>lambdaQuery().eq(QxUser::getPhone, username));
        if (StringUtils.isNull(qxUser)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("用户不存在/密码错误");
        }
        if (UserStatus.DISABLE.getCode().equals(qxUser.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        return LoginUser(qxUser);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }

    public UserDetails LoginUser(QxUser qxUser) {
        return new LoginUser(qxUser.getId(), qxUser);
    }
}

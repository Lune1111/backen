package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.app.service.IQxUserService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.QxUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.RedisUtils;
import com.ruoyi.web.controller.sms.QxTextMessage;
import com.ruoyi.web.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class QxLoginController extends BaseController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private IQxUserService qxUserService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/appLogin")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        String token=loginService.login("AppLogin ",loginBody.getUsername(), loginBody.getPassword(),loginBody.getCode(),loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    @PostMapping("/textmessage")
    public AjaxResult TextMessage(@RequestBody LoginBody loginBody){
        Random yzm = new Random();
        int a;
        String yzm2 = "";
        for (int i = 0; i < 6; i++) {
            a=yzm.nextInt(10);
            yzm2+=a;
        }
        AjaxResult ajax = AjaxResult.success();
        String phone ="+86"+loginBody.getPhoneNo();
        String[] phoneNumber={phone};
        ajax.put("rows",QxTextMessage.SendSms(yzm2,phoneNumber));
        System.out.println(yzm2);
        RedisUtils.set("ycode",yzm2+loginBody.getPhoneNo());
        return ajax;
    }

    @GetMapping( "/sms")
    public AjaxResult sms(String yzm){
        return toAjax(RedisUtils.get("ycode").equals(yzm));
    }

    @PostMapping("/smslog")
    public AjaxResult smslogin(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        QxUser qxUser = qxUserService.getOne(Wrappers.<QxUser>lambdaQuery().eq(QxUser::getPhone,loginBody.getPhoneNo()));String token=loginService.login("AppLogin ",loginBody.getUsername(), qxUser.getPassword(),null,loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

}

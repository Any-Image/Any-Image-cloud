package com.icloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icloud.controller.utils.R;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import com.icloud.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.icloud.controller.utils.Code;

import javax.annotation.Resource;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IUserService ius;
    @Autowired
    private MailService mailService;

    @PostMapping("/code")
    public R code(@RequestBody User user){
        String sub = "欢迎注册影像云";
        Code co = new Code();
        String s = co.codeGenerator(user.getEmail());
        String text = "【影像云】验证码"+s+"，您正在注册影像云，若非本人操作，请勿泄露。";
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getEmail,user.getEmail());
        User one = ius.getOne(lqw);
        if (one==null){
            return new R(mailService.sendMail(user.getEmail(), sub, text),s);
        }else {
            return new R(false);
        }
    }

    @PostMapping
    public R register(@RequestBody User user){
        if (user.getType()==0){
            user.setIsdone(0);
        }
        user.setImage("a");
        return new R(ius.save(user));
    }
}

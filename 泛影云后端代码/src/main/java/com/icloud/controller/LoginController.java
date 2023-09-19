package com.icloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icloud.controller.utils.R;
import com.icloud.dao.UserDao;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService ius;

    @PostMapping
    public R login(@RequestBody User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        if (user.getPhone().length()==11){
            lqw.eq(User::getPhone,user.getPhone());
        }
        else {
            lqw.eq(User::getEmail,user.getPhone());
        }
        User one = ius.getOne(lqw);
        if (one!=null&&one.getPassword().equals(user.getPassword())&&one.getType()==user.getType()){
            if (one.getImage().length()!=1){
                byte[] bytes = new byte[0];
                try {
                    bytes = Files.readAllBytes(Paths.get(one.getImage()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String content = new String(bytes, StandardCharsets.UTF_8);
                one.setImage(content);
                return new R(true,one);
            }else {
                return new R(true, one);
            }
        } else if (one!=null&&one.getType()==user.getType()&&!one.getPassword().equals(user.getPassword())){
            return new R(false,"1");
        }else if (one!=null&&one.getType()!=user.getType()&&one.getPassword().equals(user.getPassword())){
            return new R(false,"2");
        }else if (one!=null&&one.getType()!=user.getType()&&!one.getPassword().equals(user.getPassword())){
            return new R(false,"3");
        } else {
            return new R(false,"0");
        }
    }
}

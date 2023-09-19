package com.icloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.icloud.controller.utils.Code;
import com.icloud.controller.utils.R;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import com.icloud.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.LambdaMetafactory;

@RestController
@RequestMapping("/admin")
public class ManagerController {
    @Autowired
    private IUserService ius;

    @Autowired
    private MailService mailService;

    @GetMapping("/p0/{currentPage}/{pageSize}")
    public R getAllp0(@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getType,0).eq(User::getIsdone,0);
        IPage<User> page = ius.getPage1(currentPage, pageSize, lqw);
        return new R(true,page);
    }
    @GetMapping("/p1/{currentPage}/{pageSize}")
    public R getAllp1(@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getType,0).ne(User::getIsdone,0);
        IPage<User> page = ius.getPage1(currentPage, pageSize, lqw);
        return new R(true,page);
    }
//未诊患者分页查询
    @GetMapping("/p0/{currentPage}/{pageSize}/{name}")
    public R getOneP0(@PathVariable int currentPage, @PathVariable int pageSize, @PathVariable String name){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(User::getName,name).eq(User::getType,0).eq(User::getIsdone,0);
        IPage<User> page = ius.getPage1(currentPage,pageSize,lqw);
        if (page.getRecords().size()!=0){
            return new R(true,page);
        }
        else {
            return new R(false);
        }
    }

//    已诊患者分页查询
    @GetMapping("/p1/{currentPage}/{pageSize}/{name}")
    public R getOneP1(@PathVariable int currentPage, @PathVariable int pageSize, @PathVariable String name){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(User::getName,name).eq(User::getType,0).ne(User::getIsdone,0);
        IPage<User> page = ius.getPage1(currentPage,pageSize,lqw);
        if (page.getRecords().size()!=0){
            return new R(true,page);
        }
        else {
            return new R(false);
        }
    }

//    从医生列表中移除
    @PutMapping("/{phone}")
    public R del(@RequestBody User user,@PathVariable String phone){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getPhone,phone);
        R r = new R(ius.update(user,lqw));
        return r;
    }

    @PostMapping("/doctors")
    public R addOneD(@RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        System.out.println(user);
        lqw.eq(User::getPhone,user.getPhone());
        lqw1.eq(User::getIdnum,user.getIdnum());
        User one1 = ius.getOne(lqw);
        User one2 = ius.getOne(lqw1);
        if (one1!=null||one2!=null){
            return new R(false);
        }else {
            user.setType(1);
            user.setPassword(user.getIdnum());
            user.setImage("a");
            return new R(ius.save(user));
        }
    }
    @PostMapping("/expertises")
    public R addOneP(@RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        System.out.println(user);
        lqw.eq(User::getPhone,user.getPhone());
        lqw1.eq(User::getIdnum,user.getIdnum());
        User one1 = ius.getOne(lqw);
        User one2 = ius.getOne(lqw1);
        if (one1!=null||one2!=null){
            return new R(false);
        }else {
            user.setType(2);
            user.setPassword(user.getIdnum());
            user.setImage("a");
            return new R(ius.save(user));
        }
    }

//    真删除
    @DeleteMapping("/{phone}/{type}")
    public R deleteOne(@PathVariable String phone,@PathVariable Integer type){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (type!=4){
            lqw.eq(User::getPhone,phone);
        }else {
            lqw.eq(User::getFile,phone);
        }
        return new R(ius.remove(lqw));
    }

    @GetMapping("/{currentPage}/{pageSize}")
    public R getPageM(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getType,1);
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }

    @PostMapping ("/{currentPage}/{pageSize}")
    public R getPageBy(@PathVariable Integer currentPage, @PathVariable Integer pageSize, @RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        if(user.getName()!=null){
            if (user.getSex()==null){
                if (user.getUnit()==null){
                    lqw.like(User::getName,user.getName()).eq(User::getType,1);
                }else {
                    lqw.like(User::getName,user.getName()).like(User::getUnit, user.getUnit()).eq(User::getType,1);
                }
            }else {
                if (user.getUnit()==null){
                    lqw.like(User::getName,user.getName()).eq(User::getSex,user.getSex()).eq(User::getType,1);
                }else {
                    lqw.like(User::getName,user.getName()).like(User::getUnit, user.getUnit()).eq(User::getSex,user.getSex()).eq(User::getType,1);
                }
            }
        } else if (user.getSex()!=null) {
            if (user.getUnit()==null){
                lqw.eq(User::getSex,user.getSex()).eq(User::getType,1);
            }else {
                lqw.eq(User::getSex,user.getSex()).like(User::getUnit, user.getUnit()).eq(User::getType,1);
            }
        } else if (user.getUnit()!=null) {
            lqw.like(User::getUnit, user.getUnit()).eq(User::getType,1);
        }else {
            return new R(false);
        }
        IPage<User> page = ius.getPage1(currentPage,pageSize,lqw);
        if (page.getRecords().size()!=0){
            return new R(true,page);
        }else {
            return new R(false);
        }
    }
    @PostMapping("/code")
    public R code(@RequestBody User user){
        String sub = "影像云提示:";
        Code co = new Code();
        String s = co.codeGenerator(user.getEmail());
        String text = "【影像云】验证码"+s+"，您正在重置密码，若非本人操作，请勿泄露。";
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getEmail,user.getEmail());
        User one = ius.getOne(lqw);
        return new R(mailService.sendMail(user.getEmail(), sub, text),s);
    }

    @PutMapping("/findpass/{email}")
    public R FindPass(@RequestBody User user, @PathVariable String email){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, email);
        return new R(ius.update(user,lqw));
    }
}

package com.icloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.icloud.controller.utils.R;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService ius;

    @GetMapping("/{email}")
    public R getOne(@PathVariable String email) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (email.length()!= 11){
            lqw.eq(User::getEmail,email);
        }else {
            lqw.eq(User::getPhone,email);
        }
        User one = ius.getOne(lqw);
        if (one!=null) {
            if (one.getImage().length()!=1) {
                byte[] bytes;
                try {
                    bytes = Files.readAllBytes(Paths.get(one.getImage()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String content = new String(bytes, StandardCharsets.UTF_8);
                one.setImage(content);
                return new R(true, one);
            } else {
                return new R(true, one);
            }
        }
        else {
            return new R(false);
        }
    }

    @GetMapping("/s/{physician}")
    public R getOne1(@PathVariable String physician){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,physician);
        User one = ius.getOne(lqw);
        if (one!=null){
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
        }
        else {
            return new R(false);
        }
    }

    @GetMapping("/p0/{currentPage}/{pageSize}/{name}/{phone}/{type}")
    public R getOneP0(@PathVariable int currentPage, @PathVariable int pageSize, @PathVariable String name,@PathVariable String phone,@PathVariable Integer type){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (type==2) {
            lqw.like(User::getName, name).eq(User::getType, 0).eq(User::getIsdone, 0).eq(User::getPhysician, phone);
        } else {
            lqw.like(User::getName,name).eq(User::getType,0).eq(User::getIsdone,0).eq(User::getPhysicians,phone);
        }
        IPage<User> page = ius.getPage1(currentPage,pageSize,lqw);
        if (page.getRecords().size()!=0){
            return new R(true,page);
        }
        else {
            return new R(false);
        }
    }
    @GetMapping("/p1/{currentPage}/{pageSize}/{name}/{phone}/{type}")
    public R getOneP1(@PathVariable int currentPage, @PathVariable int pageSize, @PathVariable String name, @PathVariable String phone,@PathVariable Integer type){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (type==2) {
            lqw.like(User::getName, name).eq(User::getType, 0).eq(User::getIsdone, 1).eq(User::getPhysician, phone);
        } else {
            lqw.like(User::getName,name).eq(User::getType,0).eq(User::getIsdone,1).eq(User::getPhysicians,phone);
        }
        IPage<User> page = ius.getPage1(currentPage,pageSize,lqw);
        if (page.getRecords().size()!=0){
            return new R(true,page);
        }
        else {
            return new R(false);
        }
    }

    @PutMapping("/mine")
    public R update(@RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw.eq(User::getIdnum,user.getIdnum());
        User one = ius.getOne(lqw);
        if(user.getType()!=0){
            if (user.getPhone().equals(one.getPhone())){
                return new R(ius.update(user,lqw));
            }else{
                lqw1.eq(User::getPhysician,one.getPhone());
                List<User> tow = ius.list(lqw1);
                if (tow.size()==0){
                    return new R(ius.update(user,lqw));
                }else {
                    for (int i=0;i<tow.size();i++){
                        tow.get(i).setPhysician(user.getPhone());
                    }
                    ius.updateBatchById(tow);
                    return new R(ius.update(user,lqw));
                }
            }
        }else {
            if (user.getDescription().equals(one.getDescription())){
                return new R(ius.update(user,lqw));
            }else {
                one.setIsdone(0);
//                2023年5月15日
                one.setPhysician("");
                one.setPhysicians("");
                ius.update(one,lqw);
                return new R(ius.update(user,lqw));
            }
        }
    }
    @PutMapping("/mine/{phone}")
    public R addPhy(@RequestBody User user, @PathVariable String phone){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,phone);
        lqw1.eq(User::getPhone,user.getPhysician());
        if (user.getPhone()!=null){
            LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(User::getPhone,user.getPhone());
            User three = ius.getOne(lqw2);
            three.setIsdone(0);
            ius.update(three,lqw2);
        }
        User one = ius.getOne(lqw);
        User two = ius.getOne(lqw1);
        one.setPhysician(user.getPhysician());
        one.setIsdone(0);
        two.setIsdone(1);
        ius.update(two,lqw1);
        return new R(ius.update(one, lqw));
    }

    @PutMapping("/info/{email}")
    public R diagnose(@RequestBody User user,@PathVariable String email){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,email);
        User one = ius.getOne(lqw);
        if (user.getDescription1().equals(one.getDescription1())){
            return new R(ius.update(user,lqw));
        }else {
            if (one.getPhysicians()==null||one.getPhysicians().length()!=11) {
                user.setIsdone(1);
            }else {
                user.setIsdone(1);
                LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
                lqw1.eq(User::getPhone,one.getPhysicians());
                User one1 = ius.getOne(lqw1);
                one1.setIsdone(1);
                ius.update(one1,lqw1);
            }
            return new R(ius.update(user,lqw));
        }
    }
    @PutMapping("/{phone}/{type}/{row}")
    public R ddel(@PathVariable String phone,@PathVariable Integer type,@PathVariable Integer row){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (row!=4){
            lqw.eq(User::getPhone,phone);
        }else {
            lqw.eq(User::getFile,phone);
        }
        User one = ius.getOne(lqw);
        if (type==2) {
            one.setPhysician("0");
        }else {
            one.setPhysicians("0");
        }
//        2023年5月15日修改
        one.setIsdone(0);
        return new R(ius.update(one,lqw));
    }

    @GetMapping("/experts/{currentPage}/{pageSize}")
    public R getPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        return new R(true,ius.getPage2(currentPage,pageSize));
    }
    @GetMapping("/patients/{currentPage}/{pageSize}/{phone}/{type}/{isdone}")
    public R getPageP(@PathVariable Integer currentPage, @PathVariable Integer pageSize,@PathVariable String phone,@PathVariable Integer type, @PathVariable Integer isdone){
        return new R(true,ius.getPage0(currentPage,pageSize,phone,type,isdone));
    }

    @PostMapping ("/experts/{currentPage}/{pageSize}")
    public R getPageBy(@PathVariable Integer currentPage, @PathVariable Integer pageSize, @RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if(user.getName()!=null){
            if (user.getSex()==null){
                if (user.getUnit()==null){
                    lqw.like(User::getName,user.getName()).eq(User::getType,2);
                }else {
                    lqw.like(User::getName,user.getName()).like(User::getUnit, user.getUnit()).eq(User::getType,2);
                }
            }else {
                if (user.getUnit()==null){
                    lqw.like(User::getName,user.getName()).eq(User::getSex,user.getSex()).eq(User::getType,2);
                }else {
                    lqw.like(User::getName,user.getName()).like(User::getUnit, user.getUnit()).eq(User::getSex,user.getSex()).eq(User::getType,2);
                }
            }
        } else if (user.getSex()!=null) {
            if (user.getUnit()==null){
                lqw.eq(User::getSex,user.getSex()).eq(User::getType,2);
            }else {
                lqw.eq(User::getSex,user.getSex()).like(User::getUnit, user.getUnit()).eq(User::getType,2);
            }
        } else if (user.getUnit()!=null) {
            lqw.like(User::getUnit, user.getUnit()).eq(User::getType,2);
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

    @PostMapping
    public R addOneP(@RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,user.getPhone());
        lqw1.eq(User::getIdnum,user.getIdnum());
        User one1 = ius.getOne(lqw);
        User one2 = ius.getOne(lqw1);
        if (one1!=null||one2!=null){
            return new R(false);
        }else {
            user.setType(0);
            user.setPassword(user.getIdnum());
            user.setImage("a");
            user.setIsdone(0);
            return new R(ius.save(user));
        }
    }

    @PostMapping("/{email}")
    public R addImage(@PathVariable String email,@RequestParam("files") MultipartFile file ) throws IllegalStateException, IOException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        FileWriter writer;
        User user = new User();
        byte[] b = file.getBytes();
        String str = Base64.getEncoder().encodeToString(b);
        if (email.length()!=11) {
            lqw.eq(User::getEmail,email);
        }else {
            lqw.eq(User::getPhone,email);
        }
        User one = ius.getOne(lqw);
        String id = String.valueOf(one.getId());
        try {
            writer = new FileWriter(id+".txt");
            //清空原文件内容
            writer.write("");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setImage(id+".txt");
        ius.update(user,lqw);
        return new R(true, str);
//        return new R(ius.update(user,lqw));
    }

    @GetMapping("/m/{phone}")
    public R checkM(@PathVariable String phone){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,phone);
        User one = ius.getOne(lqw);
        one.setIsdone(2);
        ius.update(one,lqw);
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
    }
}

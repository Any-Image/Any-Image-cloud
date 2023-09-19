package com.icloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icloud.controller.utils.R;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/remotations")
public class RemoteDiagnosis {
    @Autowired
    private IUserService ius;

    @GetMapping("/{file}")
    public R getOneRe(@PathVariable String file){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getFile,file);
        return new R(true,ius.getOne(lqw));
    }
    @PutMapping("/info/{file}")
    public R putInfo(@RequestBody User user,@PathVariable String file){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getFile,file);
        User one = ius.getOne(lqw);
        if (user.getDescription1().equals(one.getDescription1())){
            return new R(ius.update(user,lqw));
        }else {
            user.setIsdone(1);
            LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
            lqw1.eq(User::getPhone,one.getPhysicians());
            User one1 = ius.getOne(lqw1);
            one1.setIsdone(1);
            ius.update(one1,lqw1);
            return new R(ius.update(user,lqw));
        }
    }
    @PostMapping("/add")
    public R sav( @RequestParam("a")String filePath){
        String patientID = null, studyUID = null, seriesUID = null, phone = null;
        User user = new User();
        String[] split = filePath.split(",");
        for (int i = 0; i < split.length; i++){
            String[] split1 = split[i].split(":");
            if (i==0) {
                patientID = split1[1].substring(1,split1[1].length()-1);
            }else if (i==1) {
                studyUID = split1[1].substring(1,split1[1].length()-1);
            } else if (i==2) {
                seriesUID = split1[1].substring(1,split1[1].length()-1);
            }else {
                phone = split1[1].substring(17,split1[1].length()-2);
            }
        }
        String url="patientID="+patientID+"&studyUID="+studyUID+"&seriesUID="+seriesUID;
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw.eq(User::getType,4);
        lqw.eq(User::getPhone,phone);
        List<User> list = ius.list(lqw);
        User one = ius.getOne(lqw1);
        int num = list.size() + 1;
        user.setType(4);
        user.setName("会诊" + num);
        user.setIsdone(0);
        user.setImage("a");
        user.setIdnum(one.getName());
        user.setSex(one.getUnit());
        user.setFile(url);
        user.setPhysicians(phone);
        return new R(ius.save(user));
    }
    @PutMapping("/transfer/{phone}")
    public R addPhy(@RequestBody User user, @PathVariable String phone){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw.eq(User::getFile,phone);
        lqw1.eq(User::getPhone,user.getPhysician());
        LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(User::getPhone,user.getPhone());
        User three = ius.getOne(lqw2);
        three.setIsdone(0);
        ius.update(three,lqw2);
        User one = ius.getOne(lqw);
        User two = ius.getOne(lqw1);
        one.setPhysician(user.getPhysician());
        one.setIsdone(0);
        one.setPassword(two.getName());
        one.setUnit(two.getUnit());
        two.setIsdone(1);
        ius.update(two,lqw1);
        return new R(ius.update(one, lqw));
    }
    @GetMapping("/admin0/{currentPage}/{pageSize}")
    public R getAllR0(@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getType,4).eq(User::getIsdone,0);
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
    @GetMapping("/admin1/{currentPage}/{pageSize}")
    public R getPageAllR1(@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getType,4).eq(User::getIsdone,1);
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
    @GetMapping("/remotation0/{currentPage}/{pageSize}/{phone}/{type}")
    public R getPageR0(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable String phone,@PathVariable Integer type){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (type==2) {
            lqw.eq(User::getType,4).eq(User::getIsdone,0).eq(User::getPhysician,phone);
        }else {
            lqw.eq(User::getType,4).eq(User::getIsdone,0).eq(User::getPhysicians,phone);
        }
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
    @GetMapping("/remotation1/{currentPage}/{pageSize}/{phone}/{type}")
    public R getPageR1(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable String phone,@PathVariable Integer type){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if (type==2) {
            lqw.eq(User::getType,4).eq(User::getIsdone,1).eq(User::getPhysician,phone);
        }else {
            lqw.eq(User::getType,4).eq(User::getIsdone,1).eq(User::getPhysicians,phone);
        }
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }

//    条件查询
    @GetMapping("/admin0/{currentPage}/{pageSize}/{name}")
    public R getPAllByR0(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable String name){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(User::getType,2).eq(User::getName,name);
        User one = ius.getOne(lqw1);

        lqw.eq(User::getType,4).eq(User::getIsdone,0).eq(User::getPhysician,one.getPhone());
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
    @GetMapping("/admin1/{currentPage}/{pageSize}/{name}")
    public R getAllByR1(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable String name){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(User::getType,2).eq(User::getName,name);
        User one = ius.getOne(lqw1);

        lqw.eq(User::getType,4).eq(User::getIsdone,1).eq(User::getPhysician,one.getPhone());
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
    @GetMapping("/remotation0/{currentPage}/{pageSize}/{name}/{phone}/{type}")
    public R getPageByR0(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable String phone,@PathVariable Integer type,@PathVariable String name){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(User::getType,2).eq(User::getName,name);
        User one = ius.getOne(lqw1);
        if (type==2) {
            lqw.eq(User::getType,4).eq(User::getIsdone,0).eq(User::getPhysician,phone).eq(User::getPhysicians,one.getPhone());
        }else {
            lqw.eq(User::getType,4).eq(User::getIsdone,0).eq(User::getPhysicians,phone).eq(User::getPhysician,one.getPhone());
        }
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
    @GetMapping("/remotation1/{currentPage}/{pageSize}/{name}/{phone}/{type}")
    public R getPageByR1(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable String phone,@PathVariable Integer type,@PathVariable String name){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(User::getType,2).eq(User::getName,name);
        User one = ius.getOne(lqw1);
        System.out.println(one);
        if (type==2) {
            lqw.eq(User::getType,4).eq(User::getIsdone,1).eq(User::getPhysician,phone).eq(User::getPhysicians,one.getPhone());
        }else {
            lqw.eq(User::getType,4).eq(User::getIsdone,1).eq(User::getPhysicians,phone).eq(User::getPhysician,one.getPhone());
        }
        return new R(true,ius.getPage1(currentPage,pageSize,lqw));
    }
}

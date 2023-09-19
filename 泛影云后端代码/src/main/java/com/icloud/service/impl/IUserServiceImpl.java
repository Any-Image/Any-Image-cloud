package com.icloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icloud.dao.UserDao;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr.eq;

@Service
public class IUserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
    @Autowired
    private UserDao ud;

    @Override
    public IPage<User> getPage2(int currentPage, int pageSize) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getType,2);
        IPage<User> page = new Page(currentPage,pageSize);
        ud.selectPage(page,lqw);
        return page;
    }

    @Override
    public IPage<User> getPage1(int currentPage, int pageSize, LambdaQueryWrapper lqw) {
        IPage<User> page = new Page(currentPage,pageSize);
        ud.selectPage(page,lqw);
        return page;
    }


    @Override
    public IPage<User> getPage0(int currentPage, int pageSize,String phone, int type, int isdone) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        if (phone.length()==11){
            if (isdone == 1){
                if (type==2) {
                    lqw.eq(User::getPhysician,phone).ne(User::getIsdone,0);
                } else{
                    lqw.eq(User::getPhysicians,phone).ne(User::getIsdone,0);
                }
            }else {
                if (type==2) {
                    lqw.eq(User::getPhysician,phone).eq(User::getIsdone,0);
                } else{
                    lqw.eq(User::getPhysicians,phone).eq(User::getIsdone,0);
                }
            }
        }
//        } else{
//            lqw.eq(User::getType,phone).eq(User::getIsdone,isdone);
//        }
        IPage<User> page = new Page(currentPage,pageSize);
        ud.selectPage(page,lqw);
        return page;
    }

//    @Override
//    public IPage<User> getPage3(int currentPage, int pageSize) {
//        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
//        lqw.gt(User::getPhysicians,"10000000000").gt(User::getPhysician,"10000000000");
//        Page page = new Page(currentPage, pageSize);
//        ud.selectPage(page,lqw);
//        return page;
//    }
}
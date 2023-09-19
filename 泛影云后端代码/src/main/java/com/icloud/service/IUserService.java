package com.icloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icloud.domain.User;

import javax.management.Query;

public interface IUserService extends IService<User> {
    IPage<User> getPage2(int currentPage, int pageSize);

    IPage<User> getPage1(int currentPage, int pageSize, LambdaQueryWrapper lqw);

    IPage<User> getPage0(int currentPage, int pageSize, String phone, int type, int isdone);

//    IPage<User> getPage3(int currentPage, int pageSize);
}
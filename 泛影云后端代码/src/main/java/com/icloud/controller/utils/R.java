package com.icloud.controller.utils;

import lombok.Data;

@Data
//前后端数据交互协议
public class R {
    private Boolean flag;
    private Object data;

    public R(){};

    public R(Boolean flag){
        this.flag = flag;
    }

    public R(Boolean flag,Object data){
        this.flag = flag;
        this.data = data;
    }
}

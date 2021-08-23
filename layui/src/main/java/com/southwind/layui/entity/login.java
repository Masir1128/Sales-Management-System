package com.southwind.layui.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class login {
    private int ID;
    private String name;
    private String password;
}

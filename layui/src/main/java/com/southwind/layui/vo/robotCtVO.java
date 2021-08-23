package com.southwind.layui.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class robotCtVO {
    private Integer ID;
    private String rot;
    private String loca;
    private String locb;
    private String juli;
    private String speed;
    private String time;
    private String up;
    private String down;
    private String weight;
}

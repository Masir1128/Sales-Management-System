package com.southwind.layui.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class bussinessMessage {
    //@TableId(type = IdType.AUTO)
    private int ID;
    private String projectName;
    private String customName;
    private String customAddress;
    private String bussiness;
    private String manager;
    private String industry;
    private String time;
    private String rem;
    private String Assessor;
    private String model;
    private String nature;
    private String status;
    private Double pingtime;
    private int rnum;
    private String tsleader;
    private String tstime;
    private String pgbg;
}

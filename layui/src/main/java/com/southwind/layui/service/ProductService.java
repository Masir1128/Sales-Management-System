package com.southwind.layui.service;

import com.southwind.layui.entity.Product;
import com.southwind.layui.vo.*;

public interface ProductService {
    public DataVO<ProductVO> findData(Integer page,Integer limit);
    public BarVO getBarVO();
    // 反馈总表
    public DataVO<bussinessVO> findData1(Integer page,Integer limit);
    // 测试按照某个销售进行查询
    public DataVO<bussinessVO> findData3(Integer page,Integer limit,String name);

    // 测试按照某个销售进行查询
    public DataVO<bussinessVO> findData4(Integer page,Integer limit,String name);

    // 测试按照某个项目性质进行查询
    public DataVO<bussinessVO> SalesStatus(Integer page,Integer limit,String status);
    //
    public DataVO<bussinessVO>  CombineFind(Integer page,Integer limit,String comfind,String name);
    // 反馈销售人员列表ct
    public DataVO<bussinessNameVO> findData2();
    // 反馈机器人节拍表
    public DataVO<robotCtVO> ct();
    // 反馈机器人控制方案
    public DataVO<robotCtVO> kz();
    // 查询Echarts 的数据
    public BusTVO getBusVO();
    // 查询工时柱状图，该函数只允许输入 姓名 和 时间段
    public BusTVO getBusVObyTime(String name,String st,String et);
    public BusTVO getBusVOByManager(String name);

    public BusTVO getPieVO1();
}

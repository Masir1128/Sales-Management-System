# QKM售前评估管理系统

@Masir 20200923 更新



|版本号|发布时间|发布部门|更新概述|
|:----:|:----:|:----:|:----:|
|V1.1|2020/9/23|培训中心|新建文档 |
|        ||          |          |

程序结构框架

@20200928  新增性质查询，以及联合查询





### 搭建项目框架
1.新建工程文件

2.引入依赖包

mybatis class

```
<dependency>
<groupId>com.baomidou</groupId>
<artifactId>mybatis-plus-boot-starter</artifactId>
<version>3.3.1.tmp</version>
</dependency>

```

3.选择需要返回的数据库，这里需要提前配置数据库的驱动。在该开发中先测试与数据库的交互接口，可以把Product的信息进行返回，所以准备好数据库

4.创建一个Product

（1）在main>>java下面新建一个entity的包，在包里新建Product.java

```java
package com.southwind.layui.entity;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer stock;
    private Integer categoryleveloneId;
    private Integer categoryleveltwoId;
    private Integer categorylevelthreeId;
    private String fileName;
}
```

5.再新建一个包mapper，并新建ProductMapper

```java
package com.southwind.layui.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.layui.entity.Product;

public interface ProductMapper extends BaseMapper<Product> {
}
```

6.新建yml文件，导入配置

```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mmall?serverTimezone=UTC
    username: root
    password: root
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

7.进行单元测试 goto >> test

```java
package com.southwind.layui.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper mapper;

    @Test
    void test(){
        mapper.selectList(null).forEach(System.out::println);
    }

}
```

8.主函数增加

```
@MapperScan("com.southwind.layui.mapper")
```

### 解析数据

9.创建一个vo包，并新建DataVO类

```java
package com.southwind.layui.vo;
import lombok.Data;
import java.util.List;

@Data
public class DataVO<T> {
    private Integer code;
    private String msg;
    private Integer count;
    private List<T> data;
}
```

10.在entity 新建一个ProductCategory类（为了解决解析ID的问题）

```java
package com.southwind.layui.entity;

import lombok.Data;

@Data
public class ProductCategory {
    private Integer id;
    private String name;
}
```

11.同理-在mapper包下新建一个接口 ProductCategoryMapper

```java
package com.southwind.layui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.layui.entity.ProductCategory;

public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
}
```

12.新建测试文件

```java
package com.southwind.layui.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    void test(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",655);
        System.out.println(mapper.selectOne(wrapper));
    }
}
```

测试结果

![wiKTpQ.png](https://s1.ax1x.com/2020/09/03/wiKTpQ.png)



### 开始封装业务层

13.新建一个service的包，里面新建文件接口 ProductService

```java
package com.southwind.layui.service;

import com.southwind.layui.entity.Product;
import com.southwind.layui.vo.DataVO;
import com.southwind.layui.vo.ProductVO;

public interface ProductService {
    public DataVO<ProductVO>  findData();
}
```

14.在VO文件下新建ProductVO

```java
package com.southwind.layui.vo;

public class ProductVO {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer stock;
    private String categorylevelone;
    private String categoryleveltwo;
    private String categorylevelthree;
    private String fileName;

}
```

15.在service下新建一个impl的包，并新建ProductServiceImpl类



需要增加一个插件 https://www.jb51.net/article/184570.htm 解决set get方法

16.测试接口

17.新建Controller 测试反馈到网端

18.增加一个config

### 新增功能

需求：通过页面新增按钮点击之后，弹出layui层，显示需要补充的数据，如下：

![wOi0fK.png](https://s1.ax1x.com/2020/09/22/wOi0fK.png)

业务逻辑
1.通过按钮获取页面

```html
<button type="button" class="layui-btn layui-btn-sm layui-btn-normal" onclick="add()">添加</button>
```

```javascript
//新增销售信息
function add(){
    layer.open({
        type:2,
        title:'新增QKM标准品评估信息',
        area:['840px','600px'],
        content:'edit',
    });
}
```

2.输入新增信息并提交

前端代码：通过POST

```html
<form class="layui-form" action="/add" method="post">
```

后端代码：进行接收前端数据

```java
//add功能
@RequestMapping(value = "/add",method = RequestMethod.POST)
public String save(bussinessMessage bussinessMessage1){
    System.out.println(bussinessMessage1);
    bussinessMessage mes = new bussinessMessage();
    mes.setID(bussinessMessage1.getID());
    mes.setProjectName(bussinessMessage1.getProjectName());
    mes.setCustomName(bussinessMessage1.getCustomName());
    mes.setCustomAddress(bussinessMessage1.getCustomAddress());
    mes.setBussiness(bussinessMessage1.getBussiness());
    mes.setManager(bussinessMessage1.getManager());
    mes.setIndustry(bussinessMessage1.getIndustry());
    mes.setTime(bussinessMessage1.getTime());
    mes.setRem(bussinessMessage1.getRem());
    mes.setAssessor(bussinessMessage1.getAssessor());
    mes.setModel(bussinessMessage1.getModel());
    mes.setNature(bussinessMessage1.getNature());
    mes.setStatus(bussinessMessage1.getStatus());
    mapper.insert(mes);
    System.out.println("插入成功");
    return "index";
}
```



### 删除功能

```java
//测试Delete功能
@RequestMapping(value = "/delete",method = RequestMethod.POST,params = {"id"})
    public String delete(String id){
    System.out.println(id);
    int row = mapper.deleteById(id);
    System.out.println("删除条数" + row);
    return "index1";
}
```



### 修改功能
开发思路：需要点击编辑按钮之后获取edit.html弹出层，并将数据进行反馈到输入框，点击保存之后，将数据传递到后台进行更新即可
- 数据反馈到输入框业务逻辑
反馈机制
1.通过Ajax进行反馈(无使用)
2.通过端数据直接导入

本次开发是通过layui技术进行开发，开发思路是，在主页面点击编辑按钮后，主页面JS代码部分会监听事件触发，代码如下：

```js
else if(obj.event === 'add'){
    layui.use('layer', function () {
        //layer.alert('编辑行：<br>'+ JSON.stringify(data))
        layer.open({
            type: 2,
            title:'修改信息',
            area:['840px','600px'], //设置页面大小
            content: 'add.html',    //配置需要传递参数的子页面
            success: function (layero, index){
                var body = layui.layer.getChildFrame('body', index);
                console.log(data);
                console.log("ss");
                //传递参数
                body.find("input[name='ID']").val(data.id);
                body.find("input[name='projectName']").val(data.projectName);
                body.find("input[name='customName']").val(data.customName);
                body.find("input[name='customAddress']").val(data.customAddress);
                body.find("input[name='bussiness']").val(data.bussiness);
                body.find("input[name='manager']").val(data.manager);
                body.find("input[name='industry']").val(data.industry);
                body.find("input[name='time']").val(data.time);
                body.find("input[name='rem']").val(data.rem);
                body.find("input[name='Assessor']").val(data.assessor);
                body.find("input[name='model']").val(data.model);
                body.find("input[name='nature']").val(data.nature);
                body.find("input[name='status']").val(data.status);
                //form.render();
            }
        });
    });
}
```
以上代码需要写清楚子页面对于name的名称，并一一对应去传入，然后后续的工作如同add功能一样，通过post请求，先传入Spring后台进行解析，代码如下：


![wjZklt.png](https://s1.ax1x.com/2020/09/23/wjZklt.png)

Spring端代码
```java
//edit功能
//说明： bussinessMessage 这个是销售基本信息类
@RequestMapping(value = "/edit",method = RequestMethod.POST)
public String edit(bussinessMessage bussinessMessage2){
    System.out.println(bussinessMessage2);
    bussinessMessage mes = mapper.selectById(bussinessMessage2.getID());
    mes.setID(bussinessMessage2.getID());
    mes.setProjectName(bussinessMessage2.getProjectName());
    mes.setCustomName(bussinessMessage2.getCustomName());
    mes.setCustomAddress(bussinessMessage2.getCustomAddress());
    mes.setBussiness(bussinessMessage2.getBussiness());
    mes.setManager(bussinessMessage2.getManager());
    mes.setIndustry(bussinessMessage2.getIndustry());
    mes.setTime(bussinessMessage2.getTime());
    mes.setRem(bussinessMessage2.getRem());
    mes.setAssessor(bussinessMessage2.getAssessor());
    mes.setModel(bussinessMessage2.getModel());
    mes.setNature(bussinessMessage2.getNature());
    mes.setStatus(bussinessMessage2.getStatus());
    int rows = mapper.updateById(mes);
    System.out.println("影响记录数:" +rows);
    return "index1";

}
```

### 查询功能
#### 单独查询（人员）
- 按照人员进行查询

开发思路：

1.首页数据是通过后台接口List1，进行反馈

```java
@RequestMapping("/List1")
    @ResponseBody
    public DataVO List1(){
        return productService.findData1();
}
```

反馈效果：

![09Figs.png](https://s1.ax1x.com/2020/09/25/09Figs.png)

2.findData1的函数交互逻辑如下：

```java
// 反馈总表
public DataVO<bussinessVO> findData1();
```

```java
/**
* 获取所有评估项目列表
* @Masir
*/
@Override
public DataVO<bussinessVO> findData1(){
    DataVO dataVO = new DataVO();
    dataVO.setCode(0);
    dataVO.setMsg("");
    bussinessMapper.selectCount(null);
    dataVO.setCount(Long.valueOf(bussinessMapper.selectCount(null)));
    List<bussinessMessage> bussinesslist = bussinessMapper.selectList(null);
    dataVO.setData(bussinesslist);
    return dataVO;
}
```



3.根据1、2思路进行开发，先编写后台数据访问

（1）先定义一个接口

```java
public DataVO<bussinessVO> findData3(String name);
```

（2）编写findData3接口函数

```java
 /**
     * @Masir
     * 查询部门数据（人员）
     */
@Override
public DataVO<bussinessVO> findData3(String name){
    DataVO dataVO = new DataVO();
    dataVO.setCode(0);
    dataVO.setMsg("");
    QueryWrapper<bussinessMessage> queryWrapper = new QueryWrapper<bussinessMessage>();
    queryWrapper.like("manager",name);
    List<bussinessMessage> bussinesslist = bussinessMapper.selectList(queryWrapper);
    bussinesslist.forEach(System.out::println);
    dataVO.setData(bussinesslist);
    return dataVO;
}
```

（４）编写Controll

```java
//find查询功能
    /**
     *
     * @20200925  查询功能
     * @Masir
     * @return
     */
    @RequestMapping(value = "/find",method ={RequestMethod.GET,RequestMethod.POST},params = {"id"})
    @ResponseBody
    public DataVO<bussinessVO> find(String id){
        System.out.println(id);
        return productService.findData3(id);

    }
```

（５）编写前端代码

设置一个下拉框

```html

<label class="layui-form-label">名称查询</label>
	<div class="layui-input-inline" >
		<select id = "lv1"  name="lv1" style="width: 200px;height: 30px" >
			<option selected="selected">请选择</option>
		</select>
	</div>
```

下拉框触发接口

```javascript
$("#lv1").change(function(){
		var qw=$("#lv1").val();
		id_counter = 1;
		console.log(qw);
}
```

在接口函数中编写 table.reload，并根据查询来选择新的url，具体见代码！

#### 单独查询（状态）
- 根据状态进行查询

步骤：
1.编写service 接口函数

```java
// 测试按照某个项目性质进行查询
public DataVO<bussinessVO> SalesStatus(String status);
```

2.编写SalesStatus（）

```java
/**
* @Masir
* 查询项目性质进行数据查询（项目状态）
*/
@Override
public DataVO<bussinessVO> SalesStatus(String status){
    DataVO dataVO = new DataVO();
    dataVO.setCode(0);
    dataVO.setMsg("");
    QueryWrapper<bussinessMessage> queryWrapper = new QueryWrapper<bussinessMessage>();
    queryWrapper.like("status",status);
    List<bussinessMessage> bussinesslist = bussinessMapper.selectList(queryWrapper);
    bussinesslist.forEach(System.out::println);
    dataVO.setData(bussinesslist);
    return dataVO;
}
```

3.编写Controll 

```java
//find查询功能（项目性质）
/**
*
* @20200928 查询功能
* @Masir
* @return
*/
@RequestMapping(value = "/findstatus",method ={RequestMethod.GET,RequestMethod.POST},params = {"stu"})
@ResponseBody
public DataVO<bussinessVO> findstatus(String stu){
	System.out.println(stu);
	return productService.SalesStatus(stu);
}
```

4.编写前端页面逻辑
方法如同
#### 联合查询

后端

方法如何单个查询业务逻辑一样，只需要将1个参数改为2个参数即可，参考代码

```java
/**
     * @Masir
     * 联合查询
     */
@Override
public DataVO<bussinessVO> CombineFind(String comfind,String name){
    DataVO dataVO = new DataVO();
    dataVO.setCode(0);
    dataVO.setMsg("");
    QueryWrapper<bussinessMessage> queryWrapper = new QueryWrapper<bussinessMessage>();
    queryWrapper.eq("status",comfind).eq("manager",name);
    List<bussinessMessage> bussinesslist = bussinessMapper.selectList(queryWrapper);
    bussinesslist.forEach(System.out::println);
    dataVO.setData(bussinesslist);
    return dataVO;
}
```

controll

```java
/**
     *
     * @20200928 联合查询
     * @Masir
     * @return
     */
@RequestMapping(value = "/comfind",method ={RequestMethod.GET,RequestMethod.POST},params = {"com","name"})
@ResponseBody
public DataVO<bussinessVO> comfind(String com,String name){
    System.out.println(com);
    return productService.CombineFind(com,name);

}
```

前端

通过flag 进行获取不同的url


### 权限问题

### Redmine获取

### 工时问题
实时获取



### Echart 数据分析
#### 工时统计总览
开发思路，需要将数据从数据库查出来之后通过Echart的数据格式进行反馈
开发步骤：

1、新建数据库，选择需要查询的数据
新建一张数据表，通过查询语句获取需要查询的数据，如下：

```mssql
SELECT project_name,pingtime from bussiness_message WHERE pingtime>0 GROUP BY pingtime
```
![wzAkrV.png](https://s1.ax1x.com/2020/09/24/wzAkrV.png)



2、编写两个VO 类，用于存储从数据库取出的数据，并重新排列

```java
package com.southwind.layui.vo;

import lombok.Data;

@Data
public class BusVO {
    private String projectName;
    private Double pingtime;

}
```

```java
package com.southwind.layui.vo;

import lombok.Data;

import java.util.List;

@Data
public class BusTVO {
    private List<String> names;
    private List<Double> values;
}

```

3、编写Mapper查询语句

```java
public interface BussinessMapper extends BaseMapper<bussinessMessage> {
    @Select("SELECT project_name,pingtime from bussiness_message WHERE pingtime>0 GROUP BY pingtime")
    public List<BusVO> findAllBusVO();
}

```

![wzAWzn.png](https://s1.ax1x.com/2020/09/24/wzAWzn.png)



4、编写Service

![wzEmef.png](https://s1.ax1x.com/2020/09/24/wzEmef.png)

编写getBusVO( )

```java
@Override
    public BusTVO getBusVO(){
        List<BusVO> list = bussinessMapper.findAllBusVO();
        List<String> names = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for(BusVO busVO :list){
            names.add(busVO.getProjectName());
            values.add(busVO.getPingtime());
        }
        BusTVO busTVO = new BusTVO();
        busTVO.setNames(names);
        busTVO.setValues(values);
        return busTVO;
    }
```

![wzEJO0.png](https://s1.ax1x.com/2020/09/24/wzEJO0.png)



5、编写Controller接口

```java
 @RequestMapping("/BusVO")
    @ResponseBody
    public BusTVO getBusVO(){ return productService.getBusVO();}
```

这个时候就可以去网页进行测试，结果如下：

![wzEI1A.png](https://s1.ax1x.com/2020/09/24/wzEI1A.png)



6、编写前端页面（本次是前后不分离）

![wzVkAU.png](https://s1.ax1x.com/2020/09/24/wzVkAU.png)



7、查看结果

![wzVmcR.png](https://s1.ax1x.com/2020/09/24/wzVmcR.png)
#### 工时统计按人员查询 + 时间段

开发思路:

1.布局前端页面,要求可以从数据库中读取人员名称信息 √

2.开发后台业务逻辑,可以通过访问链接的方式得到想要的数据,数据必须可以通过前端进行传递

开发步骤：

- Step1：思路可以从Controller开始，因为考虑到人员的查询需要从外部传入所以， 一步到位，如下代码：

  name 表示外部传入需要查询的人员

```java
@RequestMapping(value = "/BusVOByManager",method ={RequestMethod.GET,RequestMethod.POST},params = {"name"})
@ResponseBody
public BusTVO getBusVOByManager(String name){
    return productService.getBusVOByManager(name);
}
```

- Step2：在Service 目录下新建函数

```java
public BusTVO getBusVOByManager(String name);
```
<img src="https://s1.ax1x.com/2020/10/09/0D21VP.png" alt="0D21VP.png" style="zoom:80%;" />

- Step3：编写接口函数

  ```java
  /**
       * 描述：获取销售信息总工时记录,本函数是通过人员获取
       * @Masir
       */
      @Override
      public BusTVO getBusVOByManager(String name){
          List<BusVO> list = bussinessMapper.findAllBusVOByManager(name);
          List<String> names = new ArrayList<>();
          List<Double> values = new ArrayList<>();
          for(BusVO busVO :list){
              names.add(busVO.getProjectName());
              values.add(busVO.getPingtime());
          }
          BusTVO busTVO = new BusTVO();
          busTVO.setNames(names);
          busTVO.setValues(values);
          return busTVO;
      }
  ```

- Step4：测试

测试格式 (http://localhost:8080/BusVOByManager?name=张基学)

![0D2zIf.png](https://s1.ax1x.com/2020/10/09/0D2zIf.png)

至此，后台功能已完成！

3.将数据反馈在前端页面中 

开发思路：该部分开发，只需要在用户点击Select按钮后重新刷新Echart的函数即可，代码如下：

```javascript
//响应搜索按钮，根据人员进行查询
$(function(){
    $.ajax({
        "url": "List2",
        "type": "get",
        "dataType": "json",
        "success": function(json){
            console.log(json.data);
            for(var i=0;i<json.data.length;i++){
                var ch = json.data[i].name;

                $("#lv1").append("<option>"+ch+"</option>")
            }
        }
    })
})
/**
		 * 描述：查询
		 */
$("#lv1").change(function(){
    var qw=$("#lv1").val(); //获取用户选择的人员
    $(function(){
        $.ajax({
            "url":"BusVOByManager?name="+qw,
            "type":"POST",
            "success":function(data){
                console.log(data.names)
                console.log(data.values)
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                // 指定图表的配置项和数据
                var option = {
                    title : {
                        text : '评估工时统计表'
                    },
                    tooltip:{
                        show : true,
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend:{
                        data : ['工时/h']
                    },
                    grid: {
                        y2: 150  //增加柱形图纵向的高度
                    },
                    xAxis: {
                        //type: 'category',
                        data: data.names,
                        axisLabel:{
                            interval:0,//横轴信息全部显示
                            rotate:-65,//-15度角倾斜显示
                        },

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name : "工时/h",
                        data: data.values,
                        type: 'bar',
                        label: {
                            show: true,
                            position: 'insideTop',
                            fontSize: 10,
                        },
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    })
})
```

新增需求

需要增加时间段的显示

开发思路：这个地方的人员选择建固定下来，也就是将select name 中的字段直接传入到form表单里面进行提交

#### 成单率统计

设计思路：

1、按照逆向思维进行设计，首先明确Pie需要的数据是什么？

```json
{
-name:[
	"项目1",
	"项目2",
	"项目3",
],
-values:[
    1,
    2,
    3
]
}
```

2、从数据库里面做文章！

```mysql
SELECT STATUS,COUNT(*) AS '次数' from bussiness_message GROUP BY STATUS
SELECT STATUS,COUNT(*) AS '次数' from bussiness_message WHERE manager='陈爱' GROUP BY STATUS
```

考虑到需要将查询出的数据字段重新赋值，所以我们需要将查询语句重新修改

```mysql
SELECT STATUS,COUNT(*) AS 'CON' from bussiness_message GROUP BY STATUS
```

开发过程如下：

1、在VO目录下，新建一个类，用来存储查询到的数据

```java
package com.southwind.layui.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Masir Description:
 * IO:
 */
@Data
public class PieVO {
    @JsonProperty("name")
    private String STATUS;
    @JsonProperty("value")
    private Double CON;
}

```

2、在BussniessMapper 接口中新建一个查询语句

```java
//查询数据库中项目性质的比例
@Select("SELECT STATUS,COUNT(*) AS 'CON' from bussiness_message GROUP BY STATUS")
public List<PieVO> findAllStatusVO();
```

3、Service

```java
@Override
public BusTVO getPieVO1(){
    List<PieVO> list = bussinessMapper.findAllStatusVO();
    List<String> names = new ArrayList<>();
    List<Double> values = new ArrayList<>();
    for(PieVO pieVO :list){
        names.add(pieVO.getSTATUS());
        values.add(pieVO.getCON());
    }
    BusTVO busTVO = new BusTVO();
    busTVO.setNames(names);
    busTVO.setValues(values);
    return busTVO;
}
```

4、Controller

```java
 @RequestMapping(value ="/pieVO1")
    @ResponseBody
    public List<PieVO> getPieVO1(){return mapper.findAllStatusVO();}
```

5、测试

<img src="https://s1.ax1x.com/2020/10/08/001178.png" alt="001178.png" style="zoom:50%;" />

1. admin 页面下拉框问题不显示

   排查是js逻辑问题

2. Echarts 数据显示不全问题

   引用文件过期

3. add添加新增功能没有ID自增的功能

4. 分页面查询功能

5. 数据库录入为null的处理机制

   通过前端不允许输入空值

6. ID的问题

   目前状况：首先理解这个问题，目前layui的列表是通过spring读取数据库的ID 直接进行反馈

   理想状况：spring后台不反馈，前端接收到数据自己排序

   layui 有一个自己排序的功能：{type: 'numbers',title:'序号'}，所以我将之前反馈的ID给注视掉了，但是又有另外一个问题，就是颜色问题

   颜色问题已解决，已解决

7. 增加和编辑数据后页面自动刷新

8. 数据库自增

   目前通过录入自增没问题，但是数据不显示

   测试不加注视，数据可以读取，但是不能插入

#### 成单率按人员查询 + 时间段

开发思路：

1.布局前端页面,要求可以从数据库中读取人员名称信息 √

2.开发后台业务逻辑,可以通过访问链接的方式得到想要的数据,数据必须可以通过前端进行传递

开发步骤

- 在service > BussinessMapper 中定义查询数据库的接口

  ```java
  // 用于查询当前需要指定销售人员的单独的成单率
  @Select("SELECT STATUS,COUNT(*) AS 'CON' from bussiness_message WHERE manager=#{name} GROUP BY STATUS")
  public List<PieVO> findAllStatusVOByManager(String name);
  ```
  
- 在imlp中定义函数

  ```java
  /**
       * 描述：获取销售成单率,本函数是通过人员获取成单率
       * @Masir
       */
  public BusTVO getPieVO2(String name){
      List<PieVO> list = bussinessMapper.findAllStatusVOByManager(name);
      List<String> names = new ArrayList<>();
      List<Double> values = new ArrayList<>();
      for(PieVO pieVO :list){
          names.add(pieVO.getSTATUS());
          values.add(pieVO.getCON());
      }
      BusTVO busTVO = new BusTVO();
      busTVO.setNames(names);
      busTVO.setValues(values);
      return busTVO;
  }
  ```
  
- 编写Service

  ```java
  @RequestMapping(value ="/pieVO2",method ={RequestMethod.GET,RequestMethod.POST},params = {"name"})
  @ResponseBody
  public List<PieVO> getPieVO2(String name){return mapper.findAllStatusVOByManager(name);}
  ```
  
- 测试
  ![0rpCfU.png](https://s1.ax1x.com/2020/10/09/0rpCfU.png)

- 完成

  

3.完成前端开发，方法如上。效果如图

![0sJqzT.png](https://s1.ax1x.com/2020/10/10/0sJqzT.png)



增加时间段的查询开发思路如下：

![05bhrT.png](https://s1.ax1x.com/2020/10/14/05bhrT.png)

### 登陆和注册功能

1.新建一张user的数据表

![0stygf.png](https://s1.ax1x.com/2020/10/10/0stygf.png)



### 权限问题

权限问题是为了解决普通用户对项目评估管理工作的查询

### 销售人员数据录入

1.准备一张销售人员信息表，如下

![0sNp26.png](https://s1.ax1x.com/2020/10/10/0sNp26.png)

2.新增前端页面用来显示这个数据，该数据通过后台List2 接口实现，已完成

![0shHt1.png](https://s1.ax1x.com/2020/10/10/0shHt1.png)

<img src="https://s1.ax1x.com/2020/10/10/0shhXF.png" alt="0shhXF.png" style="zoom:80%;" />





### 优化

- 优化主页面列表根据评估项目的状态进行颜色区分；

  




### Tolist
- ID 排序和自增的功能不需要从数据库读取，需要从前端自动获取（已解决@20200929 Masir）
- 项目序号生成（两个方法）（已解决@20200929 Masir）
  1. SpringBoot查询完成后，向前台返回Json数据时同时返回Json的数组长度，前台接收数组长度后再生成项目序号  √
  2. SpringBoot不返回Json数组长度，前台获取Json数组长度，根据数组长度生成项目序号  √
- Echarts 
	1. redmine，所有的都要增加链接-@njia
	2. 需要按照人员进行数据显示    √
	3. 工时的上线，更改颜色 （待定）-@njia
- Pie
 	1. 更新前端页面显示逻辑（已解决 @20201008 Masir）
 	2. 需要统计售前评估总成单率（已解决 @20201008 Masir）
 	3. 统计个人（销售）成单率  √


- 分页的功能

1.完成对总数据的分页
2.完成对查询数据的分页功能  - 局部分页暂时取消




<div align="center">
  <div><h1>数据库设计表自动生成器</h1></div>
  <div>
    <img
      src="https://img.shields.io/badge/mybatis%20plus-3.4.1-blue.svg"
      alt="Coverage Status"
    />
    <img
      src="https://img.shields.io/badge/freemarker-2.3.31-blue.svg"
      alt="Downloads"
    />
    <img
      src="https://visitor-badge.glitch.me/badge?page_id=yixihan.mybatis-plus-generator&left_color=green&right_color=red"
    />
  </div>
</div>

<br>



## 用途

用于 `mybatis-plus` 生成实体类



优点 :

- 可自定义程度高, 支持自定义是否生成 `swagger` 注解, 逻辑删除注解, 乐观锁注解, 实体类常量等
- 无额外内容, 仅生成实体类及对应的 `controller`, `service`, `mapper` 层文件, 不生成额外代码



## 使用教程

### resource 下新建配置文件

配置内容可参考 [example.properties](src/main/resources/config/example.properties)

![image-20221222165411467](https://typora-oss.yixihan.chat//img/202212221654529.png)



#### 配置内容介绍

```properties
# 数据库配置
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/example?serverTimezone=GMT%2B8&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull
jdbc.username=root
jdbc.password=root

# 要生产的实体类表名, 多个表用 "," 分割
custom.tableNames=user,role
# 组 包名称 (生成软件包名称 : com.yixihan.example)
custom.parentPackage=com.yixihan
# 模块 包名称
custom.moduleName=example

# 对应实体类模块的包名称
custom.entityPackage=pojo
custom.mapperPackage=mapper
custom.servicePackage=service
custom.serviceImplPackage=service.impl
custom.controllerPackage=controller

# 输出目录
custom.out.javaPath=/out/main/java
custom.out.mapperPath=/out/main/resources/mapper/

# 作者
custom.author=yixihan

# 乐观锁
custom.enable.version=true
custom.version=version

# 逻辑删除
custom.enable.logicDelete=true
custom.logicDelete=del_flag

# swagger
custom.enable.swagger=true

# 实体类常量
custom.enable.constant=true
```



### 修改 application.properties

![image-20221222165804290](https://typora-oss.yixihan.chat//img/202212221658481.png)

```properties
# 修改为启用的配置文件名, 需在config目录下
enable.config=yicode.properties
```





### 最后启动即可

![image-20221222170225462](https://typora-oss.yixihan.chat//img/202212221702989.png)



#### 生成效果图

> 目录结构

![](https://typora-oss.yixihan.chat//img/202212221659187.png)



> 实体类

![image-20221222170054277](https://typora-oss.yixihan.chat//img/202212221700812.png)



> mapper

![image-20221222170110312](https://typora-oss.yixihan.chat//img/202212221701509.png)



> mapper.xml

![image-20221222170150695](https://typora-oss.yixihan.chat//img/202212221701791.png)



> 服务类

![image-20221222170118825](https://typora-oss.yixihan.chat//img/202212221701953.png)



> 服务实现类

![image-20221222170127132](https://typora-oss.yixihan.chat//img/202212221701346.png)



> controller

![image-20221222170135167](https://typora-oss.yixihan.chat//img/202212221701365.png)



## 常见问题

- 暂无

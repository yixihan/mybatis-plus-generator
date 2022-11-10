# mybatis-plus-generator 使用教程

## 新建一个配置文件
可复制 yicode.properties

### Database Config
数据库配置

### Table Name
设置要生成的表名, 可多个, 以逗号分割

### Parent Package Dir
父包名称

### Module Name
模块名称

### Package Config
包名称

### Version
乐观锁

### logicDelete
逻辑删除

### Output Dir
输出目录

## 生成实体类

### 添加自定义配置信息
```java
private static final String XXX = "/config/xxx.properties";
```

### 修改数据库配置文件
```java
public static final String CONFIG_PATH = XXX;
```

### 修改作者信息
```java
public static final String AUTHOR = "yixihan";
```

### 效果图
![](https://typora-oss.yixihan.chat//img/202211100949020.png)

### 最后启动即可
> 生成的实体类效果图

![](https://typora-oss.yixihan.chat//img/202211100953211.png)

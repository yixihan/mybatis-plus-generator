package com.yixihan.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.yixihan.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 实体类生成工具
 *
 * @author yixihan
 * @date 2022/11/10 9:21
 */
public class CodeGenerator {

    /**
     * 数据库配置文件
     */
    public static final String CONFIG_PATH = "/application.properties";
    
    /**
     * 启用配置文件路径
     */
    public static final String ENABLE_CONFIG_PATH = "/config/%s";

    /**
     * java实体模板文件
     */
    public static final String JAVA_TEMPLATE = "templates/entity.java-ori";

    /**
     * 作者
     */
    public static String author;


    /**
     * 启动函数
     */
    public static void main(String[] args) throws IOException {
        // 加载主配置文件
        InputStream configParams = CodeGenerator.class.getResourceAsStream (CONFIG_PATH);
        Properties configProps = new Properties ();
        configProps.load (configParams);
    
        // 获取启用配置文件
        String enableConfig = String.format (ENABLE_CONFIG_PATH, configProps.getProperty ("enable.config"));
        // 加载启用配置文件
        InputStream params = CodeGenerator.class.getResourceAsStream (enableConfig);
        Properties properties = new Properties ();
        properties.load (params);
    
        // 设置作者
        author = properties.getProperty ("custom.author");
        
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator ();

        // 全局配置
        GlobalConfig gc = new GlobalConfig ();
        String projectPath = System.getProperty ("user.dir");
        TemplateConfig template = new TemplateConfig ();
        template.setEntity (JAVA_TEMPLATE);
        mpg.setTemplate (template);

        // 文件输出路径
        gc.setOutputDir (projectPath + properties.getProperty ("custom.out.javaPath"));

        // 清空原始目录
        FileUtils.emptyDir (new File (projectPath + properties.getProperty ("custom.out.mapperPath")));
        FileUtils.emptyDir (new File (projectPath + properties.getProperty ("custom.out.javaPath")));

        // 作者
        gc.setAuthor (author);
        gc.setOpen (false);
        gc.setSwagger2 (Boolean.parseBoolean (properties.getProperty ("custom.enable.swagger")));
        gc.setBaseResultMap (false);
        gc.setBaseColumnList (false);
        // 不覆盖已有，则为false
        gc.setFileOverride (true);
        // 去Service的I前缀
        gc.setServiceName("%sService");

        mpg.setGlobalConfig (gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig ();
        dsc.setUrl (properties.getProperty ("jdbc.url"));
        dsc.setDriverName (properties.getProperty ("jdbc.driver"));
        dsc.setUsername (properties.getProperty ("jdbc.username"));
        dsc.setPassword (properties.getProperty ("jdbc.password"));
        mpg.setDataSource (dsc);

        // 包配置
        PackageConfig packageConfig = new PackageConfig ();
        packageConfig.setParent (properties.getProperty ("custom.parentPackage"));
        packageConfig.setEntity (properties.getProperty ("custom.entityPackage"));
        packageConfig.setMapper (properties.getProperty ("custom.mapperPackage"));
        packageConfig.setService (properties.getProperty ("custom.servicePackage"));
        packageConfig.setServiceImpl (properties.getProperty ("custom.serviceImplPackage"));
        packageConfig.setController (properties.getProperty ("custom.controllerPackage"));

        packageConfig.setModuleName (properties.getProperty ("custom.moduleName"));
        mpg.setPackageInfo (packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig () {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // xml 生成配置
        List<FileOutConfig> focList = new ArrayList<> ();
        focList.add (new FileOutConfig ("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件路径和名称
                return projectPath + properties.getProperty ("custom.out.mapperPath") + "/"
                        + tableInfo.getEntityName () + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList (focList);
        mpg.setCfg (cfg);
        mpg.setTemplate (template.setXml (null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig ();
        // 表名生成策略
        strategy.setNaming (NamingStrategy.underline_to_camel);
        strategy.setColumnNaming (NamingStrategy.underline_to_camel);
        // 自动 lombok
        strategy.setEntityLombokModel (true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle (true);
        // 生成的表
        strategy.setInclude (getArrayValue (properties));
        // 请求路径驼峰转连字符
        strategy.setControllerMappingHyphenStyle (true);
        // 乐观锁
        if (Boolean.TRUE.toString ().equals (properties.getProperty ("custom.enable.version"))) {
            strategy.setVersionFieldName (properties.getProperty ("custom.version"));
        }
        // 逻辑删除
        if (Boolean.TRUE.toString ().equals (properties.getProperty ("custom.enable.logicDelete"))) {
            strategy.setLogicDeleteFieldName (properties.getProperty ("custom.logicDelete"));
        }
        // 生成字段常量
        strategy.setEntityColumnConstant (Boolean.parseBoolean (properties.getProperty ("custom.enable.constant")));

        mpg.setStrategy (strategy);
        mpg.setTemplateEngine (new FreemarkerTemplateEngine ());
        mpg.execute ();
    }

    private static String[] getArrayValue(Properties properties) {
        return properties.getProperty ("custom.tableNames").split (",");
    }
}

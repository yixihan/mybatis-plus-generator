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
     * 自定义配置文件
     */
    private static final String YICODE = "/config/yicode.properties";

    /**
     * 数据库配置文件
     */
    public static final String CONFIG_PATH = YICODE;

    /**
     * java实体模板文件
     */
    public static final String JAVA_TEMPLATE = "templates/entity.java-ori";

    /**
     * 作者
     */
    public static final String AUTHOR = "yixihan";


    /**
     * 启动函数
     */
    public static void main(String[] args) throws IOException {


        InputStream params = CodeGenerator.class.getResourceAsStream (CONFIG_PATH);
        Properties properties = new Properties ();
        properties.load (params);

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
        gc.setAuthor (AUTHOR);
        gc.setOpen (false);
        gc.setSwagger2 (true);
        gc.setBaseResultMap (false);
        gc.setBaseColumnList (false);
        // 不覆盖已有，则为false
        gc.setFileOverride (true);

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
                return projectPath + properties.getProperty ("custom.out.mapperPath") + packageConfig.getModuleName ()
                        + "/" + tableInfo.getEntityName () + "Mapper" + StringPool.DOT_XML;
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
        strategy.setVersionFieldName (properties.getProperty ("custom.version"));
        // 逻辑删除
        strategy.setLogicDeleteFieldName (properties.getProperty ("custom.logicDelete"));
        // 生成字段常量
        strategy.setEntityColumnConstant (true);

        mpg.setStrategy (strategy);
        mpg.setTemplateEngine (new FreemarkerTemplateEngine ());
        mpg.execute ();
    }

    private static String[] getArrayValue(Properties properties) {
        return properties.getProperty ("custom.tableNames").split (",");
    }
}

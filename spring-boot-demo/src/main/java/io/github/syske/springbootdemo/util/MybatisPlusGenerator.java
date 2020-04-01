package io.github.syske.springbootdemo.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mybatisPlus
 * @description: 代码生成器
 * @author: syske
 * @create: 2019-11-30 12:13
 */
public class MybatisPlusGenerator {
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    private static String Module_NAME = "";//模块名称
    private static String BASE_PACKAGE_NAME="io.github.syske.springbootdemo"; // 基础包
    private static String[] TABLES = {"server_info"}; // 生成代码的表名
    private static String AUTHOR_NAME="syske"; // 基础包
    private static DbType DB_TYPE=DbType.MYSQL; // 基础包

    public static void main(String[] args) {

        String modelPath = "./"; // 指定模块名，没有子模块则保持默认
        for(int i = 0 ; i<TABLES.length;i++) {
            shell(modelPath, TABLES[i]);
        }
    }

    private static void shell(String modelPath, String tabName){

        File file = new File(modelPath);
        String path = file.getAbsolutePath();
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path+"/src/main/java");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(true);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor(AUTHOR_NAME); // 设置作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 数据源类型orcale和mysql是不一样的
        dsc.setDbType(DB_TYPE);

        dsc.setDriverName(JDBC_DRIVER);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        dsc.setUrl(JDBC_URL);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] {tabName}); // 需要生成的表
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(BASE_PACKAGE_NAME);
        pc.setController("controller");
        pc.setEntity("dao.model");
        pc.setMapper("dao.mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("dao.mapper");
        pc.setModuleName(Module_NAME);
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);
        //多模块
        TemplateConfig tc = getTemplateConfig(gc,pc,modelPath,tabName, false);
        if (tc.getMapper() == null && tc.getXml() == null && tc.getService() == null &&
                tc.getServiceImpl() == null && tc.getController() == null && tc.getEntity(false) == null) {
            return;
        }
        // 关闭默认 xml 生成，调整生成 至 根目录（单模块）
        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称

        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。

        // 执行生成
        mpg.execute();
        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }


    /**
     * 控制包生成的路径与是否覆盖生成
     * @param gc // 全局配置
     * @param pc 包配置
     * @param model model名
     * @param tabName 表名
     * @param isCover 是否覆盖生成代码
     * @return TemplateConfig
     */
    private static TemplateConfig getTemplateConfig(GlobalConfig gc, PackageConfig pc, String model, String tabName, boolean isCover) {
        TemplateConfig tc = new TemplateConfig();
        String entity = getName(tabName,"_");
        String path = model + "/src/main/java/" +replace( pc.getParent());
        if (!isCover) {
            if (model.endsWith("dao")) {
                String mapperPath =path + "/" + replace(pc.getMapper()) + "/" + gc.getMapperName().replace("%s",entity) + ".java";
                if (isExists(mapperPath)) {
                    tc.setMapper(null);
                    System.out.println(gc.getMapperName().replace("%s",entity) + ".java 文件已存在");
                }

                String modelPath = path + "/" + replace(pc.getEntity()) + "/" + entity + ".java";
                if (isExists(modelPath)) {
                    tc.setEntity(null);
                    System.out.println(entity + ".java 文件已存在");
                }
                tc.setController(null);
                tc.setXml(null);
                tc.setService(null);
                tc.setServiceImpl(null);
            } else if (model.endsWith("api")) {
                String servicePath = path + "/" +replace(pc.getService()) + "/" +  gc.getServiceName().replace("%s",entity) + ".java";
                if (isExists(servicePath)) {
                    tc.setService(null);
                    System.out.println(gc.getServiceName().replace("%s",entity) + ".java 文件已存在");
                }
                tc.setController(null);
                tc.setEntity(null);
                tc.setServiceImpl(null);
                tc.setMapper(null);
                tc.setXml(null);
            }  else if (model.endsWith("service")) {
                String serviceImplPath = path + "/" +replace(pc.getServiceImpl()) + "/" +  gc.getServiceImplName().replace("%s",entity) + ".java";
                if (isExists(serviceImplPath)) {
                    tc.setServiceImpl(null);
                    System.out.println(gc.getServiceImplName().replace("%s",entity) + ".java 文件已存在");
                }
                String mapperXmlPath =path + "/" + replace(pc.getXml()) + "/" + gc.getXmlName().replace("%s",entity) + ".xml";
                if (isExists(mapperXmlPath)) {
                    tc.setXml(null);
                    System.out.println(gc.getXmlName().replace("%s",entity) + ".xml 文件已存在");
                }
                tc.setController(null);
                tc.setService(null);
                tc.setMapper(null);
                tc.setEntity(null);
            }else if (model.endsWith("web")) {
                String controllerPath = path + "/" +replace(pc.getController()) + "/" + gc.getControllerName().replace("%s",entity) + ".java";;
                if (isExists(controllerPath)) {
                    tc.setController(null);
                    System.out.println(gc.getControllerName().replace("%s",entity) + ".java 文件已存在");
                }
                tc.setMapper(null);
                tc.setXml(null);
                tc.setService(null);
                tc.setServiceImpl(null);
                tc.setEntity(null);
            }
        } else {
            if (model.endsWith("dao")) {
                tc.setController(null);
                tc.setService(null);
                tc.setXml(null);
                tc.setServiceImpl(null);
            } else if (model.endsWith("api")) {
                tc.setController(null);
                tc.setEntity(null);
                tc.setServiceImpl(null);
                tc.setMapper(null);
                tc.setXml(null);
            }  else if (model.endsWith("service")) {
                tc.setController(null);
                tc.setService(null);
                tc.setMapper(null);
                tc.setEntity(null);
            } else if (model.endsWith("web")) {
                tc.setMapper(null);
                tc.setXml(null);
                tc.setService(null);
                tc.setServiceImpl(null);
                tc.setEntity(null);
            }
        }
        return tc;
    }


    /**
     * 将点替换为斜杠
     * @param name
     * @return
     */
    private static String replace(String name) {
        return name.replace(".","/");
    }

    /**
     * 判断文件是否存在
     * @param path 路径
     * @return
     */
    private static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 根据驼峰命名，首字母大写
     * @param tabName 原名
     * @return 返回生成后的名字
     *  例如：user_info 返回 UserInfo
     */
    public static String getName(String tabName, String reChar) {
        String[] arr = tabName.split(reChar);
        String str = "";
        for (int i = 0; i < arr.length; i++ ) {
            String startChar = arr[i].substring(0,1).toUpperCase();
            String lastChar = arr[i].substring(1, arr[i].length());
            String newStr = startChar + lastChar;
            str += newStr;
        }
        return str;
    }
}

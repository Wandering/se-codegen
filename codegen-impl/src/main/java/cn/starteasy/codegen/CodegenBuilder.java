package cn.starteasy.codegen;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.DataSourceProvider;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qyang on 2016/10/27.
 */
public class CodegenBuilder {
    private CodegenConfig config;

    public CodegenBuilder(CodegenConfig config) {
        this.config = config;
    }

    public static void main(String[] args) {
        // 测试执行的话 生成到 当前工程下
        CodegenConfig codegenConfig = new CodegenConfig();

        //内部系统生成配置
        codegenConfig.setModule("sample");
        codegenConfig.setGenRootDir(GeneratorProperties.getRequiredProperty("outRoot"));
        //remote
//        codegenConfig.setDbUrl("jdbc:mysql://repo.startupeasy.cn:33060/");
//        codegenConfig.setDbUser("soeasy");
//        codegenConfig.setDbPassword("S0easy");
        //local
        codegenConfig.setDbUrl("jdbc:mysql://localhost:3306/");
        codegenConfig.setDbUser("root");
        codegenConfig.setDbPassword("root");

        CodegenBuilder codegenBuilder = new CodegenBuilder(codegenConfig);
        codegenBuilder.build(false);


        //online test
//        codegenConfig.setGenRootDir(GeneratorProperties.getRequiredProperty("outRoot"));
//        codegenConfig.setModule("yangqiang8app");
//        codegenConfig.setIsolateUser("yangqiang8");
//        codegenConfig.setIsolatePwd("yangqiang8");
//
//        CodegenBuilder codegenBuilder = new CodegenBuilder(codegenConfig);
//        codegenBuilder.build(true);


    }

    /**
     *
     * @param isOnline true 在线服务,会生成数据库和表
     */
    public void build(boolean isOnline){
        if(config.getModule() == null || config.getModule().trim().length() == 0){
            throw new RuntimeException("config's module 不能为空!");
        }
        String appOutRoot = config.getGenRootDir() + "/" + config.getModule();

        //1.
        initProps();

        //2. 云端调度，生成对应的数据库 并返回连接信息
        if(isOnline) {
//            initDatabase(config);
            //更改为用户独有的数据库信息
//            GeneratorProperties.setProperty("jdbc.databasename", config.getModule());
            GeneratorProperties.setProperty("jdbc.isolate.url", config.getIsolateUrl());
            GeneratorProperties.setProperty("jdbc.isolate.username", config.getIsolateUser());
            GeneratorProperties.setProperty("jdbc.isolate.password", config.getIsolatePwd());
        } else {
            GeneratorProperties.setProperty("jdbc.isolate.url", config.getDbUrl() + config.getModule() + "?useUnicode=true&amp;characterEncoding=UTF-8");
            GeneratorProperties.setProperty("jdbc.isolate.username", config.getDbUser());
            GeneratorProperties.setProperty("jdbc.isolate.password", config.getDbPassword());
        }

        GeneratorFacade generatorFacade = new GeneratorFacade(appOutRoot);
        try {
            //清理之前的生成， TODO  备份之前的生成
            generatorFacade.deleteOutRootDir();

            generatorFacade.generateByAllTable("template/mybatis");    //自动搜索数据库中的所有表并生成文件,template为模板的根目录
            generatorFacade.generateByTableList("template/common");

            String admin_projectPath = appOutRoot + "/"+ config.getModule() + "/"+ config.getModule() + "-admin-war";
            String webfront_projectPath = appOutRoot + "/"+ config.getModule() + "/"+ config.getModule() + "-webfront";
            //拷贝静态文件  因为freemarker 不能 直接 生成
            //将  /template_static/admin-war 下的文件 拷贝到 admin-war/src/main下 然后将/template_static/webfront/static 拷贝到 admin-war/src/main/webapp/assets下
            copyDirectiory(GeneratorProperties.getRequiredProperty("rootDir")+"/template_static/admin-war", admin_projectPath+"/src/main");
            copyDirectiory(GeneratorProperties.getRequiredProperty("rootDir")+"/template_static/admin-war-view", admin_projectPath+"/src/main/resources/templates/view");
            copyDirectiory(GeneratorProperties.getRequiredProperty("rootDir")+"/template_static/webfront/static", admin_projectPath+"/src/main/webapp/assets");


            copyDirectiory(GeneratorProperties.getRequiredProperty("rootDir")+"/template_static/webfront", webfront_projectPath+"/src/main/resources");


            copyFile(appOutRoot + "/ServiceMaps.java", admin_projectPath + "/src/main/java/" + config.makeBasePackageDir() +"/web/common");
            new File(appOutRoot + "/ServiceMaps.java").delete();

            //删除内置表的 domain/dao/service



            Thread.sleep(20000);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据库连接信息
     */
    private void initProps() {
        GeneratorProperties.setProperty("topDomain", config.getTopDomain());
        GeneratorProperties.setProperty("company", config.getCompany());
        GeneratorProperties.setProperty("module", config.getModule());

        GeneratorProperties.setProperty("basepackage", config.makeBasePackage());

        GeneratorProperties.setProperty("jdbc.databasename", config.getModule());
        GeneratorProperties.setProperty("jdbc.url", config.getDbUrl() + config.getModule() + "?useUnicode=true&amp;characterEncoding=UTF-8");
        GeneratorProperties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
        GeneratorProperties.setProperty("jdbc.username", config.getDbUser());
        GeneratorProperties.setProperty("jdbc.password", config.getDbPassword());
    }

    /**
     *
     * 调用dubbo 服务   创建数据库、用户 并赋予该用户该数据库的相关数据库权限；
     * @param codegenConfig
     */
    private void initDatabase(CodegenConfig codegenConfig) {
        String tempStore = GeneratorProperties.getRequiredProperty("jdbc.url");



        Connection conn = null;
        Statement stmt = null;
        try {

            conn = DataSourceProvider.getSchemaConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(1) from SCHEMATA where SCHEMA_NAME = '" + codegenConfig.getModule()  + "'" );
            int count = 0;
            while(rs.next()){
                count = rs.getInt(1);
            }
            if(count > 0){
                throw new RuntimeException("数据库 【" + codegenConfig.getModule() + "】已存在");
            }
            stmt.close();
            conn.close();

            //创建用户并赋权
            conn = DataSourceProvider.getUserConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select count(1) from user where User = '" + codegenConfig.getIsolateUser()  + "'" );

            while(rs.next()){
                count = rs.getInt(1);
            }
            if(count == 0){
                //创建用户
                stmt.executeUpdate("insert into user(Host, User, Password,ssl_cipher,x509_issuer,x509_subject) " +
                        "values('%', '" + codegenConfig.getIsolateUser() + "',password('" + codegenConfig.getIsolatePwd() +"'), '', '', '')");
            }
            stmt.close();
            conn.close();


            //重置连接
            GeneratorProperties.setProperty("jdbc.url", codegenConfig.getDbUrl());
            conn = DataSourceProvider.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE " + codegenConfig.getModule() + " DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;");

            //赋权
            stmt.executeUpdate("grant all  on " + codegenConfig.getModule() + ".* to " + codegenConfig.getIsolateUser() + "@'%' ");

            stmt.close();
            conn.close();

            //连接生成的数据库
            GeneratorProperties.setProperty("jdbc.url", tempStore);
            //置空，重新生成
            DataSourceProvider.setDataSource(null);
            conn = DataSourceProvider.getConnection();
            stmt = conn.createStatement();

            Configuration conf = new Configuration();
            conf.setClassForTemplateLoading(this.getClass(), "/templates");
            // setEncoding这个方法一定要设置国家及其编码，不然在flt中的中文在生成html后会变成乱码
            conf.setEncoding(Locale.getDefault(), "UTF-8");
            Template template = conf.getTemplate("database.schema");
            Map data = Maps.newHashMap();
            data.put("dbprefix_module", codegenConfig.getModule()+"_");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            template.process(data, new OutputStreamWriter(bos, "UTF-8"));


            String sql = new String(bos.toByteArray(), "UTF-8");
            String[] segSqls = sql.split(";");
            for(int i = 0; i< segSqls.length; i++) {
                if(segSqls[i].trim().length() > 0) {
                    stmt.executeUpdate(segSqls[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //最后变更codegen需要的数据库连接信息
    }

    private static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        //新建目标目录
        (new File(targetDir)).mkdirs();

        //获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                //源文件
                File sourceFile = file[i];
                if (!("RoleController.java".equals(sourceFile.getName()) || "UserController".equals(sourceFile.getName()))) {
                    //目标文件
                    File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                    Files.copy(sourceFile, targetFile);
                }
            } else if (file[i].isDirectory()) {
                //准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                //准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();

                copyDirectiory(dir1, dir2);
            }
        }
    }

    private static void copyFile(String file, String targetDir) throws IOException {
        File rawFile = new File(file);
        Files.copy(rawFile, new File(targetDir+"/"+rawFile.getName()));
    }
}

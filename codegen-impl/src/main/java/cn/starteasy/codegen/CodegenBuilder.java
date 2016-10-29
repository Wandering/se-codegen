package cn.starteasy.codegen;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

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
        codegenConfig.setGenRootDir(GeneratorProperties.getRequiredProperty("outRoot"));

        CodegenBuilder codegenBuilder = new CodegenBuilder(codegenConfig);
        codegenBuilder.build();
    }

    public void build(){
        if(config.getModule() == null || config.getModule().trim().length() == 0){
            throw new RuntimeException("config's module 不能为空!");
        }
        String appOutRoot = config.getGenRootDir() + "/" + config.getModule();

        //TODO  云端调度，生成对应的数据库 并返回连接信息

        initProps();
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

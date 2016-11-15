package cn.starteasy.codegen;

import java.util.Set;

/**
 * Created by qyang on 2016/10/27.
 */
public class CodegenConfig {
    /** 默认我们的服务器部署在ubuntu，并且在这个特定的目录下存储生成的工程文件 */
    private String genRootDir = "/home/ubuntu/codegen/";

    /** 包信息 */
    private String topDomain = "cn";
    private String company = "starteasy";
    private String module = "sample";

    /** 用于生成代码 默认用于外部生成*/
    private String dbUrl = "jdbc:mysql://repo.startupeasy.cn:33061/";
    private String dbUser = "soeasy";
    private String dbPassword = "S0easy";


    /** 用于替换代码中的数据库连接 */
    /** 分配给用户的独享数据库用户名  */
    private String isolateUrl;
    /** 分配给用户的独享数据库用户名  */
    private String isolateUser;
    /** 分配给用户的独享数据库密码  */
    private String isolatePwd;


    private Set<String> genTbls;
    private String destProjectDir;

    public static CodegenConfig getInstance(){
        return CodegenConfigHolder.instance;
    }

    private static class CodegenConfigHolder{
        private static CodegenConfig instance = new CodegenConfig();
    }

    public String getGenRootDir() {
        return genRootDir;
    }

    public void setGenRootDir(String genRootDir) {
        this.genRootDir = genRootDir;
    }

    /** 业务模板信息 */


    public String makeBasePackageDir(){
        return topDomain + "/" + company + "/" + module;
    }
    public String makeBasePackage(){
        return topDomain + "." + company + "." + module;
    }
    public String getTopDomain() {
        return topDomain;
    }

    public void setTopDomain(String topDomain) {
        this.topDomain = topDomain;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getIsolateUrl() {
        return isolateUrl;
    }

    public void setIsolateUrl(String isolateUrl) {
        this.isolateUrl = isolateUrl;
    }

    public String getIsolateUser() {
        return isolateUser;
    }

    public void setIsolateUser(String isolateUser) {
        this.isolateUser = isolateUser;
    }

    public String getIsolatePwd() {
        return isolatePwd;
    }

    public void setIsolatePwd(String isolatePwd) {
        this.isolatePwd = isolatePwd;
    }

    public Set<String> getGenTbls() {
        return genTbls;
    }

    public void setGenTbls(Set<String> genTbls) {
        this.genTbls = genTbls;
    }

    public String getDestProjectDir() {
        return destProjectDir;
    }

    public void setDestProjectDir(String destProjectDir) {
        this.destProjectDir = destProjectDir;
    }
}

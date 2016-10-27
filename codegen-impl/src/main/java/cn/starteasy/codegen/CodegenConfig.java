package cn.starteasy.codegen;

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

    private String dbUser = "root";
    private String dbPassword = "root";

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
}

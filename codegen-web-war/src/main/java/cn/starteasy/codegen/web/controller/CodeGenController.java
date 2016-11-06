package cn.starteasy.codegen.web.controller;

import cn.org.rapid_framework.generator.Generator;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.DataSourceProvider;
import cn.starteasy.codegen.CodegenBuilder;
import cn.starteasy.codegen.CodegenConfig;
import cn.starteasy.core.common.exception.BizException;
import cn.starteasy.dbmanager.rpcapi.IDBManager;
import cn.starteasy.uaa.context.ClientDetailContext;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qyang on 2016/10/27.
 */
@Controller
public class CodeGenController {

    @Reference(timeout = 100000, retries = 0)
    private IDBManager idbManager;

    @RequestMapping(value = "/api/gen", method = RequestMethod.POST)
    @ResponseBody
    public Boolean login(@RequestBody  Map<String,String> params){
        CodegenConfig codegenConfig = new CodegenConfig();

        if(params != null) {
            if(Strings.isNullOrEmpty(params.get("isolatePwd"))) {
                throw new BizException("1000001", "数据库密码不能为空");
            } else {
                codegenConfig.setIsolatePwd(params.get("isolatePwd"));
            }

            if(params.get("rootDir") != null) {
                codegenConfig.setGenRootDir(params.get("rootDir"));
            }
            if(params.get("topDomain") != null) {
                codegenConfig.setTopDomain(params.get("topDomain"));
            }
            if(params.get("company") != null) {
                codegenConfig.setCompany(params.get("company"));
            }
            if(params.get("module") != null) {
                codegenConfig.setModule(params.get("module"));
            }
        }

        //减少服务层的依赖，  dubbo服务 初始化数据库 在这里调用
        String user = ClientDetailContext.getCurrentUser().getLogin();
        String curUrl = idbManager.createAndGrantUser4DB(user, params.get("isolatePwd"), params.get("module"));
//        codegenConfig.setDbUrl(curUrl);
        codegenConfig.setIsolateUrl(curUrl);
        codegenConfig.setIsolateUser(ClientDetailContext.getCurrentUser().getLogin());

        //2. 生成对应的代码
        CodegenBuilder codegenBuilder = new CodegenBuilder(codegenConfig);
        codegenBuilder.build(true);

        return true;
    }


}

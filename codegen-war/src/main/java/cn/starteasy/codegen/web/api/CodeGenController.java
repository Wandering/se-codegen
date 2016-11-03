package cn.starteasy.codegen.web.api;

import cn.org.rapid_framework.generator.Generator;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.DataSourceProvider;
import cn.starteasy.codegen.CodegenBuilder;
import cn.starteasy.codegen.CodegenConfig;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/gen")
    @ResponseBody
    public Boolean login(Map<String,String> params, HttpServletRequest request){
        CodegenConfig codegenConfig = new CodegenConfig();

        if(params != null) {
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



        //2. 生成对应的代码
        CodegenBuilder codegenBuilder = new CodegenBuilder(codegenConfig);
        codegenBuilder.build(true);

        return true;
    }


}

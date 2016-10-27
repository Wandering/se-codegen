package cn.starteasy.codegen.web.api;

import cn.starteasy.codegen.CodegenBuilder;
import cn.starteasy.codegen.CodegenConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by qyang on 2016/10/27.
 */
@Controller
public class CodeGenController {
    @RequestMapping("/gen")
    @ResponseBody
    public Boolean login(Map<String,String> params){
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

        CodegenBuilder codegenBuilder = new CodegenBuilder(codegenConfig);
        codegenBuilder.build();

        return true;
    }
}

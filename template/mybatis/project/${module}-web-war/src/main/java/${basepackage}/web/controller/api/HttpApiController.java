<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
<#include "/java_copyright.include"/>
package ${basepackage}.web.controller.api;
<#assign hasBusinessPackage = false>
<#if (table.businessPackage!="")>
<#assign hasBusinessPackage = true>
</#if>

import cn.starteasy.core.common.restful.apigen.annotation.ApiDesc;
import cn.starteasy.core.common.restful.apigen.annotation.ApiParam;
import cn.starteasy.core.common.domain.StringWrapper;

import cn.starteasy.core.common.domain.SearchField;
import cn.starteasy.core.common.domain.StringWrapper;
import cn.starteasy.core.common.domain.view.BizData4Page;
import cn.starteasy.core.common.exception.BizException;
import cn.starteasy.core.common.utils.RtnCodeEnum;




<#if hasBusinessPackage>
import ${mergePkgService}.${table.businessPackage}.I${className}Service;
import ${basepackage}.domain.${table.businessPackage}.${className};
import ${mergePkgService}.${table.businessPackage}.I${className}Service;
</#if>
<#if !hasBusinessPackage>
import ${mergePkgService}.I${className}Service;
import ${basepackage}.domain.${className};
import ${mergePkgService}.I${className}Service;
</#if>

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/rest")
public class HttpApiController{
    private static final Logger logger=LoggerFactory.getLogger(HttpApiController.class);

    @ApiDesc(value = "示例api", owner = "作者")
    @RequestMapping(value = "/demo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public StringWrapper childAreas(@ApiParam(param = "uid", desc = "属性1描述") @RequestParam("uid") String uid,
            @ApiParam(param = "appId", desc = "属性2描述") @RequestParam("appId") String appId,
            @ApiParam(param = "sig", desc = "属性3描述") @RequestParam("sig") String sig,
            @ApiParam(param = "areaCode", desc = "属性4描述") @RequestParam("areaCode") String areaCode) {
            return new StringWrapper("Hello World");
    }
}
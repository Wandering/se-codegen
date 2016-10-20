package ${basepackage}.common;

import cn.starteasy.core.common.service.IBaseService;
<#list newtables as table>
<#assign className = table.classNameBo>
import ${basepackage}.service.I${className}Service;
</#list>


import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

import cn.starteasy.core.common.managerui.controller.helpers.BaseServiceMaps;

@Service("${module}ServiceMaps")
public class ServiceMaps extends BaseServiceMaps{

    <#list newtables as table>
    <#assign className = table.classNameBo>
    <#assign classNameLower = className?uncap_first>

    @Autowired
    private I${className}Service ${classNameLower}Service;
    </#list>

    @PostConstruct
    public void init(){
        super.init();
        <#list newtables as table>
            <#assign className = table.classNameBo>
            <#assign classNameLower = className?uncap_first>
        serviceMap.put("${classNameLower}",${classNameLower}Service);
        </#list>
    }

}

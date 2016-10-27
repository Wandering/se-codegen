<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#include "/macro.include"/>
<#include "/java_copyright.include"/>
package ${basepackage}.facade.impl.test;
import cn.starteasy.common.dao.IBaseDAO;
import cn.starteasy.common.domain.BaseDomain;
import cn.starteasy.common.service.IBaseService;
import cn.starteasy.common.service.IPageService;
<#assign hasBusinessPackage = false>
<#if (table.businessPackage!="")>
<#assign hasBusinessPackage = true>
</#if>
<#if hasBusinessPackage>
import ${basepackage}.facade.${table.businessPackage}.I${className}Facade;
</#if>
<#if !hasBusinessPackage>
import ${basepackage}.facade.I${className}Facade;
</#if>


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springme.xml")
public class I${className}FacadeTest{
    @Autowired
    private I${className}Facade ${classNameLower}Facade;

    @Test
    public void test(){
        //showcase
        //assertEquals(1, ${classNameLower}Facade.findAll().size());
    }
}
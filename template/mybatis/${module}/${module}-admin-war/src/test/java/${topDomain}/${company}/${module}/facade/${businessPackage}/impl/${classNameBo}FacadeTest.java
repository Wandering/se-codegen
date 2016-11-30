<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#include "/macro.include"/>
<#include "/java_copyright.include"/>
package ${basepackage}.facade.impl.test;

import ${basepackage}.AdminApplicationApp;
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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplicationApp.class)
public class ${className}FacadeTest{
    @Autowired
    private I${className}Facade ${classNameLower}Facade;

    @Test
    public void test(){
        //showcase
        //assertEquals(1, ${classNameLower}Facade.findAll().size());
    }
}

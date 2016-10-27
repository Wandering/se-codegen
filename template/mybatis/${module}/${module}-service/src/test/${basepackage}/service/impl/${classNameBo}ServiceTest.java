<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#include "/macro.include"/>
<#include "/java_copyright.include"/>
package ${basepackage}.service.impl;
import cn.starteasy.common.dao.IBaseDAO;
import cn.starteasy.common.domain.BaseDomain;
import cn.starteasy.common.service.IBaseService;
import cn.starteasy.common.service.IPageService;

import ${basepackage}.service.I${className}Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springme.xml")
public class ${className}ServiceTest{
    @Autowired
    private I${className}Service ${classNameLower}Service;

    @Test
    public void test(){
        //showcase
        assertEquals(1, ${classNameLower}Service.findAll().size());
    }
}

<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#include "/macro.include"/>
<#include "/java_copyright.include"/>
package ${basepackage}.service.impl;

import ${basepackage}.AdminApplicationApp;
import ${basepackage}.service.I${className}Service;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdminApplicationApp.class)
public class ${className}ServiceTest{
    @Autowired
    private I${className}Service ${classNameLower}Service;

    @Test
    public void test(){
        //showcase
        assertEquals(1, ${classNameLower}Service.viewList(null, Maps.newHashMap(), null).size());
    }
}

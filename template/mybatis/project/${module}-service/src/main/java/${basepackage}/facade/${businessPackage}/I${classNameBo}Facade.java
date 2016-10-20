<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
<#include "/java_copyright.include"/>
<#include "/macro.include"/>
<#assign hasBusinessPackage = false>
<#if (table.businessPackage!="")>
<#assign hasBusinessPackage = true>
</#if>
<#if hasBusinessPackage>
package ${basepackage}.facade.${table.businessPackage};
</#if>
<#if !hasBusinessPackage>
package ${basepackage}.facade;
</#if>

import cn.starteasy.core.common.service.IPersistenceProvider;

public interface I${className}Facade extends IPersistenceProvider{

}

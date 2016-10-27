<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#include "/macro.include"/>
<#include "/java_copyright.include"/>
<#assign hasBusinessPackage = false>
<#if (table.businessPackage!="")>
<#assign hasBusinessPackage = true>
</#if>
<#assign pkType = "Long">

<#if table.getPkColumn()?exists>

<#if table.getPkColumn().sqlType==4>
<#assign pkType = "Integer">
<#elseif table.getPkColumn().sqlType==12>
<#assign pkType = "String">
<#elseif table.getPkColumn().sqlType==-5>
<#assign pkType = "Long">
</#if>

</#if>
<#if hasBusinessPackage>
package ${basepackage}.service.${table.businessPackage};
</#if>
<#if !hasBusinessPackage>
package ${basepackage}.service;
</#if>
import cn.starteasy.core.common.service.IBaseService;
import cn.starteasy.core.common.service.IPageService;
<#if hasBusinessPackage>
import ${basepackage}.dao.${table.businessPackage}.I${className}DAO;
import ${basepackage}.domain.${table.businessPackage}.${className};
</#if>
<#if !hasBusinessPackage>
import ${basepackage}.dao.I${className}DAO;
import ${basepackage}.domain.${className};
</#if>

public interface I${className}Service extends IBaseService<${pkType}, I${className}DAO, ${className}>,IPageService<I${className}DAO, ${className}>{

}

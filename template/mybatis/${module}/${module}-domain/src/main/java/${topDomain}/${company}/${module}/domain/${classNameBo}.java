<#assign className = table.classNameBo>

<#include "/macro.include"/>
<#assign classNameLower = className?uncap_first>

<#assign isCbd = false>

<#list table.columns as column>
    <#if column=='creator'>
        <#assign isCbd = true>
    </#if>
</#list>
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

<#assign hasBusinessPackage = false>
<#if (table.businessPackage!="")>
<#assign hasBusinessPackage = true>
</#if>
<#include "/java_copyright.include"/>
<#if hasBusinessPackage>
package ${basepackage}.domain.${table.businessPackage};
</#if>
<#if !hasBusinessPackage>
package ${basepackage}.domain;
</#if>
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
<#if isCbd>
import cn.starteasy.core.common.domain.CreateBaseDomain;
<#else>
import cn.starteasy.core.common.domain.BaseDomain;
</#if>

import java.util.*;

public class ${className} extends <#if isCbd>CreateBaseDomain<${pkType}><#else>BaseDomain<${pkType}></#if>{
<#list table.columns as column>
<#if column='id'||column='creator'||column='createDate'||column='lastModifier'||column='lastModDate'||column='status'>
<#else>
    /** ${column.remarks} */
    private ${column.possibleShortJavaType} ${column.columnNameFirstLower};
</#if>
</#list>

<@generateConstructor className/>
<@generateJavaColumns/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.columnName}",get${column.columnName}())
		</#list>
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ${className} == false) return false;
		if(this == obj) return true;
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
}

<#macro generateJavaColumns>
<#list table.columns as column>
    <#if column.isDateTimeColumn>

    </#if>
<#if column='id'||column='creator'||column='createDate'||column='lastModifier'||column='lastModDate'||column='status'>
<#else>
    public void set${column.columnName}(${column.possibleShortJavaType} value) {
        this.${column.columnNameFirstLower} = value;
    }

    public ${column.possibleShortJavaType} get${column.columnName}() {
        return this.${column.columnNameFirstLower};
    }
</#if>
</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>

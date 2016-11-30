
-- model
<#list tables as table>
<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
INSERT INTO `${module}_model`(`id`,`name`,`tblName`,`description`, `creator`, `createDate`, `lastModifier`, `lastModDate`, `status`)
VALUES('${table.seq}','${table.name}','${table.sqlName}','${table.description}', 0, 1, 0, 1, 10);
</#list>


-- parent resource
<#list parentRes?keys as key>
<#assign prId = parentRes[key].seq>
<#assign prNumber = parentRes[key].number>
INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`)
VALUES('${prId}','/admin/${bizSys}/${key}','${prId}',0,'${prNumber}','${prNumber}','${key}',0,${times},0,${times},null,null, '');
</#list>

-- resource
<#assign resourceId=1/>
<#assign resourceGridId=1/>
<#assign resourceActionId=1/>
<#list tables as table>

<#assign className = table.classNameBo>
<#assign classNameFirstLower = table.classNameFirstLower>
<#assign classNameAllLower = table.classNameBo?lower_case>


INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`)
VALUES('${table.seq}','/admin/${bizSys}/${classNameAllLower}','${table.seq}','${table.parentId}','${table.number}','${table.longnumber}','${table.resName}',0,${times},0,${times},null,'${table.seq}', '${classNameAllLower}');

  <#assign className = table.classNameBo>
  <#assign classNameFirstLower = table.classNameFirstLower>
  <#assign idJavaType = table.idColumn.javaType>
  <#macro mapperEl value>${r"#{"}${value}}</#macro>
  <#macro namespace>${basepackage}.${persistence}</#macro>

  -- resource_action
  <#list actions as ac>
      INSERT INTO `${module}_resource_action` (`id`,`resourceId`,`name`,`actionAlias`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`)
      VALUES(${resourceActionId},${table.seq},'${ac.name}','${ac.action}',0,${times},0,${times},'${ac.name}');
      <#assign resourceActionId=resourceActionId+1/>
  </#list>

  -- resource_grid
  <#list table.columns as column>
      INSERT INTO `${module}_resource_grid` (`id`,`resId`,`displayName`,`colId`,`orderNum`,`width`,`editoptions`,`edittype`,`unformat`,`description`,`moduleName`, `creator`, `createDate`, `lastModifier`, `lastModDate`, `status`)
      VALUES(${resourceGridId},${table.seq},'${column.name}','${column.sqlName}',${column_index},200,'{}',null,'','${column.description}','${classNameAllLower}', 0, 1, 0, 1, 0);
      <#assign resourceGridId=resourceGridId+1/>
  </#list>
<#assign resourceId=resourceId+1/>
</#list>


update ${module}_resource_grid SET editable ="false" where colId="createDate" ;
update ${module}_resource_grid SET editable = "false" where colId="creator" ;
update ${module}_resource_grid SET editable = "false" where colId="lastModifier" ;
update ${module}_resource_grid SET editable = "false" where colId="lastModDate" ;
update ${module}_resource_grid SET editable = "false" where colId="status" ;


---初始化数据 值执行一次
INSERT INTO `${module}_role` (`id`,`name`,`description`) VALUES(1, 'admin', '管理员');

INSERT INTO `${module}_role_user` (`id`,`userId`,`roleId`) VALUES(1, 1, 1);

insert into ${module}_role_resource(`resourceId`, `resourceActionId`, `roleId`)
select r.id, ra.id, 1 from ${module}_resource as r, `${module}_resource_action` ra;

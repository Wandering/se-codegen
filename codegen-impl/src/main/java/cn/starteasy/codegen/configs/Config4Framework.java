package cn.starteasy.codegen.configs;

/**
 * 匹配 如易云框架 的一些配置信息
 * Created by qyang on 2016/11/14.
 */
public class Config4Framework {
    public static final String[] alreadyTbls = new String[]{
        "_data_model", "_model", "_resource", "_resource_action", "_resource_grid",
                "_role", "_role_resource", "_role_user", "_user_data", "_datagroup", "_datagroup_data", "_user_datagroup",
                "_adminuser", "_adminusertoken", "_user", "_usertoken", "_resource_design", "_resource_search"};

    public static final String[] defaultColumns = new String[]{"createDate","lastModifier","lastModDate",
            "dataModelId","dataId","groupId","tblName",
            "isAdmin","datagroupId","bizDimension","employeeId",
            "modelId","assignUrl","whereSql",
            "resourceId","resourceActionId","roleId","userId",
            "orderNum","parentId","longNumber","bizModelName",
            "resId","displayName","colId","orderNum","moduleName",
            "resourceId","divId","actionScript","actionAlias"};
}

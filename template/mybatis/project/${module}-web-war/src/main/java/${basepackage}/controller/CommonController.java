<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
<#include "/java_copyright.include"/>
package ${basepackage}.controller;


import cn.starteasy.core.common.adminui.controller.helpers.BasePersistenceProviderMaps;
import cn.starteasy.core.common.adminui.controller.helpers.BaseServiceMaps;
import cn.starteasy.core.common.adminui.controller.AbstractCommonController;

import cn.starteasy.common.service.IBaseService;
import ${basepackage}.common.PersistenceProviderMaps;
import ${basepackage}.common.ServiceMaps;


import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/admin/${module}")
public class CommonController extends AbstractCommonController{
    @Autowired
    private ServiceMaps serviceMaps;

    @Autowired
    private PersistenceProviderMaps persistenceProviderMaps;

    @Override
    protected BaseServiceMaps getServiceMaps() {
        return serviceMaps;
    }

    @Override
    protected BasePersistenceProviderMaps getPersistenceProviderMaps() {
        return persistenceProviderMaps;
    }

    @Override
    protected IBaseService getExportService() {
        return null;
    }

    @Override
    public boolean getEnableDataPerm() {
        return false;
    }

    @Override
    protected void innerHandleDel(String mainObj, Map dataMap) {
        getServiceMaps().get(mainObj).delete(dataMap.get("id"));
    }

    @Override
    protected void innerHandleAdd(String mainObj, Map dataMap) {
        Map<String, Object> newDataMap = (Map<String, Object>)dataMap;
        Map<String, Object> realDataMap = new HashMap<String, Object>();
        if(mainObj.equals("role"))
        {
            for (String key : newDataMap.keySet()) {

                if(!StringUtils.isNullOrEmpty(newDataMap.get(key).toString())){
                    realDataMap.put(key,newDataMap.get(key));
                }
            }
            getServiceMaps().get(mainObj).insertMap(realDataMap);

        }else
        {
            super.innerHandleAdd(mainObj, dataMap);
        }
    }
}

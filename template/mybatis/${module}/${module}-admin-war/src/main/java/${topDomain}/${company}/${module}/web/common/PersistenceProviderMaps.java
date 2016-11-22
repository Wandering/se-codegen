<#include "/java_copyright.include"/>
package ${basepackage}.web.common;

import cn.starteasy.core.common.ServiceManager;
import cn.starteasy.core.common.adminui.controller.helpers.BasePersistenceProviderMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.starteasy.core.common.adminui.backend.facade.IResourceDesignFacade;

import javax.annotation.PostConstruct;

/**
 * Created by xule on 16-1-11.
 * 有负责业务CUD操作时需要添加相关facade服务到这里，facade中覆写对应的接口
 */
@Service("${module}PersistenceProviderMaps")
public class PersistenceProviderMaps extends BasePersistenceProviderMaps {
    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private IResourceDesignFacade resourceDesignFacade;
//    @Autowired
//    private IReportMailTemplateFacade reportMailTemplateFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();

        providerMap.put("resourcedesign", resourceDesignFacade);
        serviceManager.batchAddPersistenceProvider(providerMap);
    }

}

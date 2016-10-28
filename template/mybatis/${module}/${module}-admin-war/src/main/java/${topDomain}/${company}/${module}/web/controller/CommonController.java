/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 *
 * Project Name: basedata
 * $Id:  CommonController.java 2015-07-29 12:04:59 $
 */

package ${basepackage}.web.controller;

import cn.starteasy.core.common.adminui.controller.AbstractCommonController;
import cn.starteasy.core.common.adminui.controller.helpers.BasePersistenceProviderMaps;
import cn.starteasy.core.common.adminui.controller.helpers.BaseServiceMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping(value="/admin/${module}")
public class CommonController extends AbstractCommonController {
    @Autowired
    private BasePersistenceProviderMaps persistenceProviderMaps;

    @Autowired
    private BaseServiceMaps serviceMaps;

    @Override
    protected BaseServiceMaps getServiceMaps() {
        return serviceMaps;
    }



    @Override
    protected BasePersistenceProviderMaps getPersistenceProviderMaps() {
        return persistenceProviderMaps;
    }

//    @Override
//    protected IBaseService getExportService() {
//        return null;
//    }

    @Override
    public boolean getEnableDataPerm() {
        return false;
    }
}

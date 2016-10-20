/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: basedata
 * $Id:  ClassesController.java 2015-07-29 12:04:59 $
 */

package ${basepackage}.web.controller;

import cn.starteasy.core.common.adminui.backend.domain.Resource;
import cn.starteasy.core.common.adminui.controller.AbstractCommonController;
import cn.starteasy.core.common.adminui.controller.helpers.ActionPermHelper;
import cn.starteasy.core.common.adminui.controller.helpers.BasePersistenceProviderMaps;
import cn.starteasy.core.common.adminui.controller.helpers.BaseServiceMaps;
import cn.starteasy.core.common.adminui.controller.helpers.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping(value="/admin/ucm")
public class UcmCommonController extends AbstractCommonController {
//    @Autowired
//    private ServiceMaps serviceMaps;

    @Override
    protected BaseServiceMaps getServiceMaps() {
        return null;
    }



    @Override
    protected BasePersistenceProviderMaps getPersistenceProviderMaps() {
        return null;
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

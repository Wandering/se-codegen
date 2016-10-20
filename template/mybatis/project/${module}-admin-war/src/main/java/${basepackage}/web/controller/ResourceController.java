/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: dap
 * $Id:  ResourceController.java 2015-05-05 23:13:36 $
 */

package ${basepackage}.web.controller;

import cn.starteasy.core.common.adminui.backend.domain.Model;
import cn.starteasy.core.common.adminui.backend.domain.Resource;
import cn.starteasy.core.common.adminui.backend.service.IModelService;
import cn.starteasy.core.common.adminui.backend.service.IResourceService;
import cn.starteasy.core.common.adminui.controller.AbstractAdminController;
import cn.starteasy.core.common.adminui.controller.helpers.ActionPermHelper;
import cn.starteasy.core.common.domain.BizStatusEnum;
import cn.starteasy.core.common.domain.persistent.SearchEnum;
import cn.starteasy.core.common.domain.persistent.SqlOrderEnum;
import cn.starteasy.core.common.domain.persistent.utils.ConditionBuilder;
import cn.starteasy.core.common.domain.persistent.utils.SorterBuilder;
import cn.starteasy.core.common.domain.view.BizData4Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/ucm")
public class ResourceController extends AbstractAdminController<IResourceService> {


    @Autowired
    private IResourceService resourceService;

    @Autowired
    private ActionPermHelper actionPermHelper;
    @Autowired
    private IModelService modelService;

    /**
     * 页面主请求
     *
     * @param request
     * @param response
     * @return 返回菜单数据、表格描述元数据、当前主描述  如本页面为org
     */
    @RequestMapping(value = "/resource")
    public ModelAndView renderMainView(HttpServletRequest request, HttpServletResponse response) {


        return doRenderMainView(request, response);
    }

    /**
     * 获取所有的组织信息
     *
     * @return
     */
    @RequestMapping(value = "/resources")
    @ResponseBody
    public BizData4Page findAllResources(HttpServletRequest request, HttpServletResponse response) {
        return doPage(request, response);
    }

    @Override
    protected void enhancePageConditions(HttpServletRequest request, Map conditions) {
//        addProductConditions(request,conditions);
    }
    @RequestMapping(value = "/presources")
    @ResponseBody
    public BizData4Page presources(HttpServletRequest request, HttpServletResponse response) {

        BizData4Page page = new BizData4Page();
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", "0");
        map.put("status", BizStatusEnum.N.getCode());
//        AppUtils.getProductMap(map,request);
//        List<Resource> resources = resourceService.queryList(map, "id", "asc");
        Resource resource = new Resource();
        resource.setId(0l);
        resource.setName("无");
//        resources.add(0, resource);
//        page.setRows(resources);
        return page;

    }


    @RequestMapping(value = "/getModels")
    @ResponseBody
    public BizData4Page getModels(HttpServletRequest request, HttpServletResponse response) {

        BizData4Page page = new BizData4Page();
        Map<String, Object> map = new HashMap<>();
        List<Model> models = modelService.viewList(null, map, SorterBuilder.sorterList("id", SqlOrderEnum.ASC));
        Model model = new Model();
        model.setId(0l);
        model.setName("无");
        models.add(0, model);
        page.setRows(models);
        return page;

    }

    @RequestMapping(value = "/getDropResources")
    @ResponseBody
    public BizData4Page getDropResources(HttpServletRequest request, HttpServletResponse response) {

        BizData4Page page = new BizData4Page();
        Map<String, Object> map = new HashMap<>();
        List<Resource> resources = resourceService.viewList(null, map, SorterBuilder.sorterList("id", SqlOrderEnum.ASC));
        page.setRows(resources);
        return page;

    }

    @RequestMapping("/resource/query")
    @ResponseBody
    public Resource queryExSchool(@RequestParam("id") String id) {
        Resource resource = (Resource) resourceService.view(Long.valueOf(id));
        return resource;
    }

    /**
     * 获取当前用户所有可见菜单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryUserMenus")
    @ResponseBody
    public List<Resource> queryUserResource(HttpServletRequest request, HttpServletResponse response) {
        List<Resource> resourceList = actionPermHelper.getResourcePerm("demo");
        return resourceList;
    }

    @RequestMapping(value = "/resourceParents")
    @ResponseBody
    public List<Resource> resourceParents() {
        return resourceService.viewList(null, ConditionBuilder.condition("parentId", SearchEnum.eq, "0"), SorterBuilder.sorterList("id", SqlOrderEnum.ASC));
    }


//    private List<BaseDataDto> changeDictArea2BaseDateDto(List<DictArea> _list) {
//        List<BaseDataDto> list = new ArrayList<>();
//        for (DictArea dictArea : _list) {
//            list.add(new BaseDataDto(dictArea.getId(), dictArea.getCode(), dictArea.getName()));
//        }
//        return list;
//    }

    @Override
    protected IResourceService getMainService() {
        return resourceService;
    }

    @Override
    protected String getBizSys() {
        return "ucm";
    }

    @Override
    protected String getMainObjName() {
        return "resource";
    }

    @Override
    protected String getViewTitle() {
        return "资源管理";
    }

    @Override
    protected String getParentTitle() {
        return "系统管理";
    }

    @Override
    public boolean getEnableDataPerm() {
        return false;
    }
}

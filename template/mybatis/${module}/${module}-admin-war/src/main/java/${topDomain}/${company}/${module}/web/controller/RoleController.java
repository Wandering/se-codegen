/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 *
 * Project Name: cv
 * $Id:  RoleController.java 2015-01-27 15:38:39 $
 */

package ${basepackage}.web.controller;

        import cn.starteasy.core.common.domain.persistent.SearchEnum;
        import cn.starteasy.core.common.domain.persistent.utils.ConditionBuilder;
        import cn.starteasy.core.common.domain.view.BizData4Page;
        import cn.starteasy.core.common.adminui.controller.AbstractAdminController;
        import cn.starteasy.core.common.adminui.backend.domain.Resource;
        import cn.starteasy.core.common.adminui.backend.domain.ResourceAction;
        import cn.starteasy.core.common.adminui.backend.domain.RoleResource;
        import cn.starteasy.core.common.adminui.backend.service.IResourceActionService;
        import cn.starteasy.core.common.adminui.backend.service.IResourceService;
        import cn.starteasy.core.common.adminui.backend.service.IRoleResourceService;
        import cn.starteasy.core.common.adminui.backend.service.IRoleService;
        import cn.starteasy.core.common.utils.UserContext;
        import cn.starteasy.core.common.adminui.backend.domain.dto.AssignDTO;
        import cn.starteasy.core.common.adminui.backend.domain.dto.AssignDetailDTO;
        import cn.starteasy.core.common.adminui.backend.domain.dto.ResourceDTO;
        import com.google.common.collect.Lists;
        import org.apache.commons.lang3.StringUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.servlet.ModelAndView;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import ${basepackage}.web.controller.BaseController;

@Controller
@RequestMapping(value = "/admin/${module}")
public class RoleController extends BaseController<IRoleService> {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IResourceActionService resourceActionService;
    @Autowired
    private IRoleResourceService roleResourceService;

    /**
     * 页面主请求
     *
     * @param request
     * @param response
     * @return 返回菜单数据、表格描述元数据、当前主描述  如本页面为org
     */
    @RequestMapping(value = "/role")
    public ModelAndView renderMainView(HttpServletRequest request, HttpServletResponse response) {


        return doRenderMainView(request, response);
    }

    /**
     * 获取所有的组织信息
     *
     * @return
     */
    @RequestMapping(value = "/roles")
    @ResponseBody
    public BizData4Page findAllRoles(HttpServletRequest request, HttpServletResponse response) {
        return doPage(request, response);
    }

    /**
     * 给角色分配资源
     *
     * @param request
     * @param response
     * @param assign
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/assign")
    public Object assign(HttpServletRequest request,
                         HttpServletResponse response, AssignDTO assign) {

        String result = null;
        // 首页删除该角色的所有权限
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", assign.getObjId());


        roleResourceService.deleteByCondition(map);

        // 保存新的角色
        if (StringUtils.isNotBlank(assign.getResources())) {
            List<AssignDetailDTO> details = com.alibaba.fastjson.JSON
                    .parseArray(assign.getResources(), AssignDetailDTO.class);
            for (AssignDetailDTO assignDetail : details) {

                map.put("resourceId", assignDetail.getParentResourceId());
                map.put("resourceActionId", assignDetail.getResourceId());

                // 注意顶级父元素parentId ==0;
                RoleResource roleResource = new RoleResource();
                roleResource.setCreateDate(System.currentTimeMillis());
                roleResource.setCreator(UserContext.getCurrentUser().getId());
                roleResource.setRoleId(Integer.valueOf(assign.getObjId()));
                roleResource.setLastModDate(System.currentTimeMillis());
                roleResource.setLastModifier(UserContext.getCurrentUser().getId());
                roleResource.setStatus(1);

                Resource resource = resourceService.view(Long
                        .valueOf(assignDetail.getParentResourceId()));
                // 说明是顶级或者二级元素
                if (assignDetail.getParentResourceId() == 0 || resource == null
                        || resource.getParentId() == 0) {
                    roleResource.setResourceActionId(0);
                    roleResource.setResourceId(assignDetail.getResourceId());
                } else {
                    // 三级
                    roleResource.setResourceActionId(assignDetail
                            .getResourceId());
                    roleResource.setResourceId(assignDetail
                            .getParentResourceId());
                }

                roleResourceService.create(roleResource);
            }
            result = "分配成功";
        }
        return result;
    }

    @RequestMapping(value = "/role/getAllResources")
    @ResponseBody
    public List<ResourceDTO> getAllResources(HttpServletRequest request,
                                             HttpServletResponse response) {

        List<ResourceDTO> resourceDTOs = Lists.newArrayList();
        String roleId = request.getParameter("roleId");

        // 获取所有父辈级菜单
        List<Resource> parentResources = resourceService
                .viewList(null, ConditionBuilder.condition("parentId", SearchEnum.eq, 0), null);

        for (Resource resource : parentResources) {
            ResourceDTO resourceDTO = new ResourceDTO();
            resourceDTO.setId(resource.getId() + "");
            resourceDTO.setParentResourceId(0);
            resourceDTO.setResourceId(resource.getId());
            resourceDTO.setResourceName(resource.getName());

            List<Resource> childrens = resourceService.viewList(null, ConditionBuilder.condition("parentId", SearchEnum.eq, resource.getId()), null);

            // 获取action

            for (Resource children : childrens) {
                // 获取二级资源
                ResourceDTO childrenDTO = new ResourceDTO();
                childrenDTO.setId(children.getId() + "");
                childrenDTO.setParentResourceId(resource.getId());
                childrenDTO.setResourceId(children.getId());
                childrenDTO.setResourceName(children.getName());

                List<ResourceAction> actions = resourceActionService.viewList(null, ConditionBuilder.condition("resourceId", SearchEnum.eq, children.getId()), null);


                for (ResourceAction action : actions) {
                    //获取三级资源
                    ConditionBuilder conditionBuilder = new ConditionBuilder();
                    conditionBuilder.and("roleId", SearchEnum.eq, roleId);
                    conditionBuilder.and("resourceActionId", SearchEnum.eq, action.getId());
                    conditionBuilder.and("resourceId", SearchEnum.eq, action.getResourceId());

                    // 说明该权限属于该角色
                    Map<String, Object> param = conditionBuilder.build();
                    param.put("groupOp", "and");
                    RoleResource rr = roleResourceService.viewOne(null, param, null);

                    ResourceDTO actionDTO = new ResourceDTO();
                    if (rr != null) {
                        actionDTO.setRoleId(Long.valueOf(roleId));
                    }

                    actionDTO.setId(action.getId() + "");
                    actionDTO.setParentResourceId(children.getId());
                    actionDTO.setResourceId(action.getId());
                    actionDTO.setResourceName(action.getName());

                    childrenDTO.getResourceInfos().add(actionDTO);
                }
                resourceDTO.getResourceInfos().add(childrenDTO);

            }
            resourceDTOs.add(resourceDTO);
        }

        return resourceDTOs;
    }


    @Override
    protected IRoleService getMainService() {
        return roleService;
    }

    @Override
    protected String getBizSys() {
        return "${module}";
    }

    @Override
    protected String getMainObjName() {
        return "role";
    }

    @Override
    protected String getViewTitle() {
        return "角色管理";
    }

    @Override
    protected String getParentTitle() {
        return "基础管理";
    }

    @Override
    public boolean getEnableDataPerm() {
        return true;
    }
}

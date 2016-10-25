/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cv
 * $Id:  AdminController.java 2015-01-27 15:38:37 $
 */

package ${basepackage}.web.controller;

import cn.starteasy.core.common.adminui.backend.domain.dto.AssignDTO;
import cn.starteasy.core.common.adminui.backend.domain.dto.AssignDetailDTO;
import cn.starteasy.core.common.adminui.backend.domain.dto.RoleDTO;
        import cn.starteasy.core.common.adminui.backend.service.IAdminUserService;
import cn.starteasy.core.common.domain.UserDomain;
import cn.starteasy.core.common.domain.view.BizData4Page;
import cn.starteasy.core.common.adminui.controller.AbstractAdminController;
import cn.starteasy.core.common.adminui.backend.domain.Role;
import cn.starteasy.core.common.adminui.backend.domain.RoleUser;
import cn.starteasy.core.common.adminui.backend.domain.AdminUser;
import cn.starteasy.core.common.adminui.backend.service.IRoleService;
import cn.starteasy.core.common.adminui.backend.service.IRoleUserService;
import cn.starteasy.core.common.protocol.ResponseT;
import cn.starteasy.core.common.service.IPageService;
import cn.starteasy.core.common.utils.UserContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ${basepackage}.web.controller.BaseController;


/**
 * 注意：具体的业务系统将##dap###替换成appName
 */


@Controller
@RequestMapping(value = "/admin/${module}")
public class AdminUserController extends BaseController {
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IRoleUserService iRoleUserService;
    @Autowired
    private IAdminUserService iAdminuserService;

    /**
     * 页面主请求
     *
     * @param request
     * @param response
     * @return 返回菜单数据、表格描述元数据、当前主描述  如本页面为org
     */
    @RequestMapping(value = "/adminuser")
    public ModelAndView renderMainView(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {


        return doRenderMainView(request, response);
    }

    /**
     * 获取所有的组织信息
     *
     * @return
     */
    @RequestMapping(value = "/adminusers")
    @ResponseBody
    public BizData4Page findAllAdmins(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int currentPage = 1;
        int pageSize = 10;
        if (!StringUtils.isEmpty(page)) {
            currentPage = Integer.valueOf(page);
        }

        if (!StringUtils.isEmpty(rows)) {
            pageSize = Integer.valueOf(rows);
        }

        return iAdminuserService.queryPage(null, null, currentPage, pageSize, pageSize, null);
    }


    /**
     * 重写save/update用户
     *
     * @param request
     * @param response
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/commonsave/adminuser")
    public String save(HttpServletRequest request,
                       HttpServletResponse response, User user) {


        UserDomain currentUser = UserContext.getCurrentUser();

        //TODO
        return "true";
    }

    /**
     * 重写删除用户
     *
     * @param request
     * @param response
     * @param user
     * @return
     */
    @RequestMapping(value = "/commondel/adminuser")
    public ModelAndView delete(HttpServletRequest request,
                               HttpServletResponse response, User user) {

        return doRenderMainView(request, response);
    }


    /**
     * 获取所有角色资源
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "/adminuser/getAllResources")
    @ResponseBody
    public List<RoleDTO> getAllResource(HttpServletRequest request,
                                        HttpServletResponse response) {

        String userId = request.getParameter("userId");
        List<Role> roles = iRoleService.viewList(null, null, null);
        List<RoleDTO> roleDTOs = Lists.newArrayList();

        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setParentResourceId(role.getId());
            roleDTO.setResourceId(role.getId());
            roleDTO.setResourceName(role.getName());

            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("roleId", role.getId());

            RoleUser roleUser = iRoleUserService.viewOne(null, map, null);
            if (roleUser != null) {
                roleDTO.setUserId(Long.valueOf(roleUser.getUserId().toString()));
            }
            roleDTOs.add(roleDTO);

        }
        return roleDTOs;

    }


    /**
     * 给某一个用户分配角色
     *
     * @param request
     * @param response
     * @param assign
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/adminuser/assign")
    public Object assign(HttpServletRequest request,
                         HttpServletResponse response, AssignDTO assign) {
        String result = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", assign.getObjId());

        /**
         * 先删除该用户的所有角色
         */
        iRoleUserService.deleteByCondition(map);

        if (assign != null && StringUtils.isNotBlank(assign.getResources())) {
            List<AssignDetailDTO> details = JSON.parseArray(
                    assign.getResources(), AssignDetailDTO.class);
            for (AssignDetailDTO detail : details) {
                RoleUser roleUser = new RoleUser();
                roleUser.setCreateDate(System.currentTimeMillis());
                roleUser.setCreator(Long.valueOf(UserContext.getCurrentUser().getId().toString()));
                roleUser.setRoleId(detail.getResourceId());
                roleUser.setUserId(assign.getObjId());
                iRoleUserService.create(roleUser);
            }
            result = "分配成功";

        }

        return result;
    }


    @Override
    protected IPageService getMainService() {
        return null;
    }

    @Override
    protected String getBizSys() {
        return "lms";
    }

    @Override
    protected String getMainObjName() {
        return "adminuser";
    }

    @Override
    protected String getViewTitle() {
        return "运营人员管理";
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
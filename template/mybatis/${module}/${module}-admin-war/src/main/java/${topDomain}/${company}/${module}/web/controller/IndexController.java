package ${basepackage}.web.controller;

import cn.starteasy.core.common.adminui.backend.domain.AdminUser;
import cn.starteasy.core.common.adminui.backend.domain.Role;
import cn.starteasy.core.common.adminui.backend.service.IAdminUserService;
import cn.starteasy.core.common.adminui.backend.service.IRoleService;
import cn.starteasy.core.common.adminui.controller.helpers.MenuUtils;
import cn.starteasy.core.common.domain.persistent.SearchEnum;
import cn.starteasy.core.common.domain.persistent.utils.ConditionBuilder;
import cn.starteasy.core.common.adminui.controller.AbstractAdminController;
import cn.starteasy.core.common.adminui.controller.helpers.ActionPermHelper;
import cn.starteasy.core.common.adminui.backend.service.IResourceService;
import cn.starteasy.core.common.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import cn.starteasy.core.common.adminui.controller.helpers.MenuUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/${module}")
public class IndexController extends
        AbstractAdminController<IResourceService> {

    @Autowired
    private IResourceService resourceService;
    @Autowired
    private ActionPermHelper actionPermHelper;
    @Autowired
    private IAdminUserService adminUserService;
    /**
     * 进入登陆页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/index", method = {RequestMethod.GET,
            RequestMethod.POST})
    public ModelAndView index(HttpServletRequest request,
                              HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(getMainObjName());
        //从用户上下文读取用户id
        Long userId = (Long) UserContext.getCurrentUser().getId();
        //读取用户完整信息
        AdminUser adminUser = adminUserService.view(userId);
        //获取用户权限组菜单
        List resourceList = actionPermHelper.getResourcePerm();
        //获取用户角色
        Role role = actionPermHelper.getRoleByUserId(userId);
        mav.addObject("resources",  MenuUtils.getTreeMenu(resourceList));
        mav.addObject("bizSys", getBizSys());
        mav.addObject("mainObj", getMainObjName());
        //注入给module当前用户名、图片和权限名称
        mav.addObject("username", adminUser.getLogin());
        mav.addObject("photo", adminUser.getPhoto());
        //用户拥有权限组时不可能为空
        mav.addObject("power",role==null?"":role.getName());
        return mav;
    }


    @Override
    public boolean getEnableDataPerm() {

        return true;
    }

    @Override
    protected String getBizSys() {

        return "${module}";
    }

    @Override
    protected String getMainObjName() {

        return "index";
    }

    @Override
    protected String getParentTitle() {
        return null;
    }

    @Override
    protected String getViewTitle() {
        return null;
    }


    @Override
    protected IResourceService getMainService() {
        return null;
    }


}

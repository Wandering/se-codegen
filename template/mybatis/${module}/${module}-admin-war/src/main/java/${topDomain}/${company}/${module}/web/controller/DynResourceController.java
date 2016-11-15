package ${basepackage}.web.controller;

import cn.starteasy.core.common.adminui.backend.domain.ResourceGrid;
import cn.starteasy.core.common.adminui.backend.service.IDataModelService;
import cn.starteasy.core.common.adminui.controller.DynController;
import cn.starteasy.core.common.domain.persistent.SearchEnum;
import cn.starteasy.core.common.domain.persistent.Sorter;
import cn.starteasy.core.common.domain.persistent.SqlOrderEnum;
import cn.starteasy.core.common.domain.persistent.utils.ConditionBuilder;
import cn.starteasy.core.common.domain.view.BizData4Page;
import cn.starteasy.core.common.service.IPageService;
import cn.starteasy.core.common.utils.BizData4PageBuilder;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Line;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * 动态报表的实现
 *
 * Created by qyang on 2016/11/10.
 */
@Controller
@RequestMapping(value="/admin/${module}")
public class DynResourceController extends DynController {

    @RequestMapping(value="/resource_{rpCode}")
    public ModelAndView render(@PathVariable("rpCode") String rpCode, HttpServletRequest request, HttpServletResponse response){
        return super.mainRender(super.rsModePrefix, rpCode, request, response);
    }

    /**
     * 获取表格数据
     * @return
     */
    @RequestMapping(value="/resource_{rpCode}s")
    @ResponseBody
    public BizData4Page getTblDatas(@PathVariable("rpCode") String rpCode, HttpServletRequest request,HttpServletResponse response){

        return super.getTblDatas(rpCode, request, response);
    }

    @Override
    protected IPageService getMainService() {
        return null;
    }

    @Override
    protected String getBizSys() {
        return "${module}";
    }

    @Override
    protected String getMainObjName() {
        return mainObjName;
    }

    @Override
    protected String getViewTitle() {
        return viewTitle;
    }

    @Override
    protected String getParentTitle() {
        return "业务报表";
    }

    @Override
    public boolean getEnableDataPerm() {
        return false;
    }
}

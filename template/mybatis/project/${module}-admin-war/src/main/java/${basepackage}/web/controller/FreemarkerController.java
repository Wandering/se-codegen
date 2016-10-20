package ${basepackage}.web.controller;

import cn.starteasy.core.common.exception.BizException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * TODO 一句话描述该类用途
 * <p>
 * 创建时间: 16/9/25 下午10:38<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Controller
public class FreemarkerController {
    @RequestMapping("/")
    public String web(Map<String,Object> model){
        model.put("time",new Date());
        model.put("message", "only test");
        return "web";//返回的内容就是templetes下面文件的名称
    }


    @RequestMapping("/index")
    public String index(Map<String,Object> model){
        model.put("time",new Date());
        model.put("message", "only test");
        return "index";//返回的内容就是templetes下面文件的名称
    }

    @RequestMapping("/show/error")
    public String error(Map<String,Object> model){
        //BizException bizException = new BizException("1111111", "only test error");

        throw new BizException("1111111", "only test error");
//        model.put("time",new Date());
//        model.put("message", "only test");
//        return "view/index";//返回的内容就是templetes下面文件的名称
    }
}

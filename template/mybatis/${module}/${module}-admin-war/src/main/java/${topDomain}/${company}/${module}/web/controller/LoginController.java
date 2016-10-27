package ${basepackage}.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * TODO 一句话描述该类用途
 * <p>
 * 创建时间: 16/9/26 上午10:56<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Map<String,Object> model){

        return "login";//返回的内容就是templetes下面文件的名称
    }

    @RequestMapping("/sidebar")
    public String sidebar(Map<String,Object> model){

        return "view/sidebar";//返回的内容就是templetes下面文件的名称
    }

    @RequestMapping("/api/test")
    public String apiTest(Map<String,Object> model){

        return "view/sidebar";//返回的内容就是templetes下面文件的名称
    }
}

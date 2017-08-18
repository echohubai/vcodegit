package vcode.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vcode.enums.ConstantEnum;
import vcode.service.JedisService;

import javax.annotation.Resource;

/**
 * Created by hubai on 2017/7/18.
 * 登录页面Controller
 */
@Controller
public class IndexController {
    @Resource
    private JedisService jedisService;
    private String AppID = ConstantEnum.APPID.toString();
    /*
    * 滑块验证登录请求处理
    * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showSlideIndex() {
        return "slide";
    }
    /*
    * 拼图验证请求处理
    * */
    @RequestMapping(value = "/puzzle", method = RequestMethod.GET)
    public String showPuzzleIndex() {
        return "puzzle";
    }
}

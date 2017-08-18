package vcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vcode.dao.JedisClient;
import vcode.service.LoginService;
import vcode.utils.Messager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by hubai on 2017/7/24.
 */
@Controller
public class LoginController {
    @Resource
    private LoginService loginService;
    @RequestMapping(value = "/puzzleLogin",method= RequestMethod.POST)
    @ResponseBody
    public Messager slideLogin( @RequestParam("token")String token,@RequestParam("username")String userName, @RequestParam("password")String passWord,@RequestParam("validate")String validate){
       return loginService.login(userName,validate,passWord,token);
    }
    @RequestMapping(value = "/slideLogin",method= RequestMethod.POST)
    @ResponseBody
    public Messager puzzleLogin( @RequestParam("token")String token,@RequestParam("username")String userName, @RequestParam("password")String passWord,@RequestParam("validate")String validate){
        return loginService.login(userName,validate,passWord,token);
    }
}

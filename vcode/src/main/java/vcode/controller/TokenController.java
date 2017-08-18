package vcode.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vcode.dao.JedisClient;
import vcode.service.TokenService;
import vcode.utils.TokenGenerator;

import javax.annotation.Resource;

/**
 * Created by hubai on 2017/7/20.
 * Token生成控制类
 */
@Controller
public class TokenController {
    @Resource
   private TokenService tokenService;
    /*
    * 滑块验证，token生成
    * */
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public String getSlideToken(@RequestParam("ts") String t,@RequestParam("type") String type) {
        String mType = type.trim();
        if ("slide".equals(mType)) {
            return tokenService.getTokenForSlide(t);
        }
        if ("puzzle" .equals(mType)) {
            return tokenService.getTokenForPuzzle(t);
        }
        return null;
    }

}

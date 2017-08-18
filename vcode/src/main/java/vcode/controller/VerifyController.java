package vcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vcode.model.PictureModel;
import vcode.service.PictureModelService;
import vcode.service.VerifyService;
import vcode.utils.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hubai on 2017/7/20.
 * 信息校验控制
 */
@Controller
public class VerifyController {
    @Resource
    private VerifyService verifyService;

   /*
   * 滑动验证校验
   * 返回校验结果
   * */
    @RequestMapping(value = "/SlideVerify", method = RequestMethod.POST)
    @ResponseBody
    public String slideVerify(@RequestParam("trace") String trace, @RequestParam("token") String token, @RequestParam("AppID") String AppID) throws Exception {
        return verifyService.getSlideVerifyResult(trace, token, AppID);
    }
    /*
    * 拼图校验
    * 返回校验结果
    * */
    @RequestMapping(value = "/puzzle/PuzzleVerify", method = RequestMethod.POST)
    @ResponseBody
    public String puzzleVerify(@RequestParam("trace") String trace, @RequestParam("token") String token, @RequestParam("AppID") String AppID,@RequestParam("slideWidth") double slideWidth) throws Exception {
        return verifyService.getPuzzleVerifyResult(trace, token, AppID,slideWidth);
    }
}

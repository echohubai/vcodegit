package vcode.controller;


import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vcode.model.Picture;
import vcode.service.PictureModelService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liqiang on 2017/7/25.
 * Test
 */
@Controller
public class TestController {
    @Resource
    private PictureModelService pictureModelService;

    @RequestMapping(value = "/PictureLoad", method = RequestMethod.GET)
    public String PictureLoad(Model model) {

        return "PictureLoad";
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public String save(HttpServletRequest request, @RequestParam("file1") CommonsMultipartFile file1,@RequestParam("file2") CommonsMultipartFile file2,@RequestParam("file3") CommonsMultipartFile file3)
    {
        pictureModelService.PictureLoad(request,file1,file2,file3);

        return "redirect:/product/saler/ShowAllProduct";
    }

    @RequestMapping(value = "/PictureRandShow", method = RequestMethod.GET)
    public String PictureRandShow(Model model) {

        return "PictureRandShow";
    }


    @RequestMapping(value = "/PictureLoadTest", method = RequestMethod.GET)
    public String PictureLoadTest(Model model) {

        return "PictureLoadTest";
    }

    @RequestMapping(value = "/saveTest.do", method = RequestMethod.POST)
    public String saveTest(HttpServletRequest request, @RequestParam("file1") CommonsMultipartFile file1,String num)
    {
        pictureModelService.PictureLoadTest(request,file1,num);

        return "redirect:/bootstrapTest1";
    }

    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    @ResponseBody
    public String getPicture() {
        List<Picture> picture=pictureModelService.selectPictureModel();

        net.sf.json.JSONArray array=null;
        try{
            array= net.sf.json.JSONArray.fromObject(picture);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("array");

        return array.toString();
    }

    @RequestMapping(value = "/deletePicture/{pMid}.do", method = RequestMethod.GET)
    public String deletePicture(@PathVariable Long pMid, HttpServletRequest request) {
        pictureModelService.deletePictureById(pMid);

        return "redirect:/bootstrapTest1";
    }

}

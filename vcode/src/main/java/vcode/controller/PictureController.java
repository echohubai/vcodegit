package vcode.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vcode.model.Picture;
import vcode.service.PictureModelService;
import vcode.utils.PagedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 功能概要：UserController
 * 
 * @author linbingwen
 * @since  2015年9月28日 
 */
@Controller
public class PictureController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private PictureModelService pictureModelService;

	@RequestMapping("/ShowAllPicture")
	public String ShowAllPicture(){
		return "ShowAllPicture";
	}
	

    @RequestMapping(value="/list.do", method= RequestMethod.POST)
    @ResponseBody
    public String list(Integer pageNumber,Integer pageSize ,String userName) {
		logger.info("分页查询用户信息列表请求入参：pageNumber{},pageSize{}", pageNumber,pageSize);
		try {
			PagedResult<Picture> pageResult = pictureModelService.queryByPage(pageNumber,pageSize);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }


	@RequestMapping(value = "/PictureLoad", method = RequestMethod.GET)
	public String PictureLoad(Model model) {

		return "PictureLoad";
	}

	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
	public String save(HttpServletRequest request, @RequestParam("file1") CommonsMultipartFile file1, @RequestParam("file2") CommonsMultipartFile file2, @RequestParam("file3") CommonsMultipartFile file3)
	{
		pictureModelService.PictureLoad(request,file1,file2,file3);

		return "redirect:/product/saler/ShowAllProduct";
	}



	@RequestMapping(value = "/PictureRandShow", method = RequestMethod.GET)
	public String PictureRandShow(Model model) {

		return "PictureRandShow";
	}


	@RequestMapping(value = "/AddPictureModel", method = RequestMethod.GET)
	public String AddPictureModel(Model model) {

		return "AddPictureModel";
	}

	@RequestMapping(value = "/savePictureModel.do", method = RequestMethod.POST)
	public String savePictureModel(HttpServletRequest request, @RequestParam("file1") CommonsMultipartFile file1,String num)
	{
		pictureModelService.SavePictureModel(request,file1,num);

		return "redirect:/ShowAllPicture";
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

		return "redirect:/ShowAllPicture";
	}
}

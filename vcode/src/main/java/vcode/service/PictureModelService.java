package vcode.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vcode.model.Picture;
import vcode.model.PictureModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import vcode.utils.PagedResult;

/**
 * Created by liqiang on 2017/7/25.
 * 随机获取图片服务
 */
public interface PictureModelService {
    List<PictureModel> selectPictureByRand();
    void PictureLoad(HttpServletRequest request, CommonsMultipartFile file1, CommonsMultipartFile file2, CommonsMultipartFile file3);
    //随机从数据库读图片
    PictureModel selectPicture();
    JSONObject getPic(String token,int failedCount);




    List<Picture> selectPictureModel();
    PictureModel  selectPictureMByRand();


    void deletePictureById(Long pMid);


    PagedResult<Picture> queryByPage(Integer pageNo, Integer pageSize);

    void SavePictureModel(HttpServletRequest request, CommonsMultipartFile file1,String num);

}

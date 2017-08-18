package vcode.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vcode.dao.PictureDao;
import vcode.dao.PictureMDao;
import vcode.dao.PictureSDao;
import vcode.enums.ConstantEnum;
import vcode.model.Picture;
import vcode.model.PictureModel;
import vcode.model.PictureSon;
import vcode.model.RedisModel;
import vcode.service.JedisService;
import vcode.service.PictureModelService;
import vcode.utils.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by liqiang on 2017/7/25.
 */
@Service

@Transactional(rollbackFor = Exception.class)
public class PictureModelServiceImpl implements PictureModelService {
    @Resource
    private PictureDao pictureDao;

    @Resource
    private PictureMDao pictureMDao;
    @Resource
    private PictureSDao pictureSDao;

    @Resource
    private JedisService jedisService;

    /*
      随机从数据库里读取图片
     */
    public List<PictureModel> selectPictureByRand() {
        return pictureDao.selectPictureByRand();
    }

    /*
      上传图片到数据库
     */
    public void PictureLoad(HttpServletRequest request, CommonsMultipartFile file1, CommonsMultipartFile file2, CommonsMultipartFile file3) {
        PictureModel pictureModel = new PictureModel();
        if (!file1.isEmpty()) {
            String type = file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = request.getSession().getServletContext()
                    .getRealPath("/images/" + filename);// 存放位置
            System.out.print(path);
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file1.getInputStream(), destFile);// 复制临时文件到指定目录下
                pictureModel.setFullPicture("/images/" + filename);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (!file2.isEmpty()) {
            String type = file2.getOriginalFilename().substring(
                    file2.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = request.getSession().getServletContext()
                    .getRealPath("/images/" + filename);// 存放位置
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file2.getInputStream(), destFile);// 复制临时文件到指定目录下
                pictureModel.setBgPicture("/images/" + filename);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (!file3.isEmpty()) {
            String type = file3.getOriginalFilename().substring(
                    file3.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = request.getSession().getServletContext()
                    .getRealPath("/images/" + filename);// 存放位置
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file3.getInputStream(), destFile);// 复制临时文件到指定目录下
                pictureModel.setSlicePicture("/images/" + filename);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        pictureDao.save(pictureModel);
    }

    public PictureModel selectPicture() {


        PictureModel pictureModel = selectPictureMByRand();
        return pictureModel;
    }

    public PictureModel selectPictureMByRand() {
        List<PictureSon> PictureList = pictureSDao.selectPictureSByRand();
        PictureSon pictureSon = PictureList.get(0);
        String BgPicture=pictureSon.getBgPicture();
        String pKid=pictureSon.getPKid();
        String  Width="18";
        String Height="12";
        //  List<Picture> pictureList=pictureMDao.selectPictureM(pKid);
        //  Picture picture=pictureList.get(0);

        //  System.out.print("2222");

        PictureModel pictureModel=new PictureModel();

        // pictureModel.setFullPicture(picture.getFullPicture());
        pictureModel.setPictureHeight(Height);
        pictureModel.setPictureWidth(Width);
        pictureModel.setBgPicture(BgPicture);
        pictureModel.setSlicePicture(pictureSon.getSlicePicture());
        pictureModel.setSliceLeftTopX(pictureSon.getSliceLeftTopX());
        pictureModel.setSliceLeftTopY(pictureSon.getSliceLeftTopY());
        System.out.print("11111");
        return pictureModel;
    }

    /**
     * 返回图片方法
     * @param token
     * @param failedCount
     * @return JSONObject格式数据
     */
    public JSONObject getPic(String token,int failedCount) {
        JSONObject ret = new JSONObject();
        RedisModel redisModel = new RedisModel();
        PictureModel pictureModel = selectPicture();
        redisModel.setPictureModel(pictureModel);
        redisModel.setFailedCount(failedCount);
        redisModel.setAppID(ConstantEnum.PUZZLEAPPID.toString());
        jedisService.set(token,redisModel,600);
        ret.put("token", token);
        ret.put("AppID",redisModel.getAppID());
        ret.put("bgPicture", pictureModel.getBgPicture());
        ret.put("slicePicture", pictureModel.getSlicePicture());
        ret.put("leftTopY", pictureModel.getSliceLeftTopY());
        return ret;
    }

    /*
     随机从数据库里读取图片
    */
    public List<Picture> selectPictureModel() {
        return pictureMDao.selectPicture();
    }

    /*
   删除图片
  */
    public void deletePictureById(Long pMid) {
        Picture picture=pictureMDao.selectPictureById(pMid);
        String pKid=picture.getPKid();
        System.out.print(pKid);
        pictureSDao.deletePictureSon(pKid);
        System.out.print("22");
        pictureMDao.deletePicture(pMid);
        System.out.print("33");
    }

    /*
 * 获取图片方法
 * */
    public PagedResult<Picture> queryByPage(Integer pageNo,Integer pageSize ) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);  //startPage是告诉拦截器说我要开始分页了。分页参数是这两个。
        return BeanUtil.toPagedResult(pictureMDao.selectPicture());
    }

    /*
     根据上传图片模板和切割次数进行切割处理
    */
    public void PictureLoadTest(HttpServletRequest request, CommonsMultipartFile file1,String num) {

        Picture picture = new Picture();
        PictureSon pictureSon = new PictureSon();
        String PictureWidth="18";
        String PitureHeigth="12";
        int N=Integer.parseInt(num);
        if (!file1.isEmpty()) {
            String type = file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().indexOf("."));// 取文件格式后缀名



            String filename3 = "Full_"+System.currentTimeMillis() + type;// 取当前时间戳作为文件名


            String path3 = request.getSession().getServletContext()
                    .getRealPath("/images/" + filename3);// 存放位置

            picture.setFullPicture("/images/" + filename3);
            picture.setPictureWidth(PictureWidth);
            picture.setPictureHeight(PitureHeigth);
            picture.setPNum(num);

            //设置外键
            String pKid= UuidUtils.generateShortUuid();

            System.out.print(pKid);

            picture.setPKid(pKid);
            //保存主表信息
            pictureMDao.save(picture);

            File destFile3 = new File(path3);//full_picture
            try {
                //图片的本地地址
                FileUtils.copyInputStreamToFile(file1.getInputStream(), destFile3);// 复制临时文件到指定目录下


                //循环体

                for(int i=0;i<N;i++) {

                    //产生随机位置

                    int randNum_X = RandomUtils.getRandPictureX(300, 100);
                    int randNum_Y = RandomUtils.getRandPictureY(200, 100);
                    String leftTop_X = RandomUtils.getleftTop_X(randNum_X);
                    String leftTop_Y = RandomUtils.getleftTop_Y(randNum_Y);
                    //生成图片路径
                    String filename1 = "Slice_" + System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String filename2 = "Bg_" + System.currentTimeMillis() + type;// 取当前时间戳作为文件名

                    //切割图片接口
                    PictureUtils.PictureReduct(path3, randNum_X, randNum_Y, filename1, filename2);


                    pictureSon.setBgPicture("/images/" + filename2);
                    pictureSon.setSlicePicture("/images/" + filename1);
                    pictureSon.setSliceLeftTopX(leftTop_X);
                    pictureSon.setSliceLeftTopY(leftTop_Y);
                    pictureSon.setPKid(pKid);

                    //保存子表信息
                    pictureSDao.save(pictureSon);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


}




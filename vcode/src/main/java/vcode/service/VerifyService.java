package vcode.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vcode.dao.JedisClient;
import vcode.dao.JedisClientSingle;
import vcode.enums.ConstantEnum;
import vcode.model.PictureModel;
import vcode.model.RedisModel;
import vcode.utils.*;

import javax.annotation.Resource;

import static vcode.utils.TraceUtils.getYVariance;

/**
 * Created by hubai on 2017/7/24.
 * 验证失败服务
 */
@Service
public class VerifyService {
    @Resource
    private JedisService jedisService;

    @Resource
    private PictureModelService pictureModelService;


    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = null;
    /*
    * 滑动滑块验证判断
    * 输入鼠标轨迹参数，token和AppID
    * 输出Messger对象
    * */
    public String getSlideVerifyResult(String trace,String token,String AppID){
        logger=Logger.getLogger(VerifyService.class);
        //logger.info("slideTrace:"+trace);
        JSONObject ret = new JSONObject();
        String validate = vertify(token,AppID,trace);
        RedisModel redisModel = jedisService.get(token,RedisModel.class);
        int failedCount = redisModel.getFailedCount();
        if (validate!=null){
            redisModel.setValidate(validate);
            jedisService.set(token,redisModel,600);
            ret.put("flag",true);
            ret.put("validate",validate);
            return ret.toString();
        }
        if (failedCount>3){
            ret.put("flag",false);
            ret.put("msg","failed is too much");
            jedisService.delParam(token);
        }
        failedCount++;
        redisModel.setFailedCount(failedCount);
        jedisService.set(token,redisModel,600);
        ret.put("flag",false);
        return ret.toString();
    }
    /*
     拼图验证判断
     sliderWidth获取页面滑块宽度，进行比例计算
     先验证滑动距离
     再Token和AppID
     最后轨迹
     */
    public String getPuzzleVerifyResult(String trace,String token,String AppID,double sliderWidth) {
        logger=Logger.getLogger(VerifyService.class);
        //logger.info("PuzzleTrace:"+trace);

        JSONObject ret = new JSONObject();
        System.out.print("元素宽度" + sliderWidth);
        System.out.print("\n");
        RedisModel redisModel = jedisService.get(token, RedisModel.class);
        double leftTopX = Double.valueOf(redisModel.getPictureModel().getSliceLeftTopX());
        String[] tracedata = getDecTrace(token, trace);
        double slideLength = getSlideLength(tracedata);//获取滑动距离
        int failedCount =redisModel.getFailedCount();//从缓存中获取之前验证失败次数
        System.out.print("\n");
        System.out.print("滑动距离" + slideLength);
        double postion = leftTopX*sliderWidth/18;//校准距离X
        final double ROFFSET = 5;//允许右偏差距离
        final double LOFFSET = 5;//允许左偏差距离
        System.out.print("\n");
        System.out.print("校准位置" + postion);
            if (slideLength >= postion - LOFFSET) {
                if (slideLength <= postion + ROFFSET) {
                    String validate = vertify(token, AppID, trace);
                    if (validate != null) {
                        redisModel.setValidate(validate);
                        jedisService.set(token,redisModel,180);
                      ret.put("flag",true);
                      ret.put("validate",validate);
                      return ret.toString();
                    }
                }
            }
               failedCount++;
        System.out.print("失败次数" + failedCount);
        System.out.print("\n");
            if (failedCount> 4){
                ret.put("flag",false);
                ret.put("msg","failed");
                jedisService.delParam(token);
                return ret.toString();
            }
        ret = pictureModelService.getPic(token,failedCount);
        ret.put("flag",false);
        return ret.toString();
    }

    /*
    * 验证用户信息，
    * 返回validate
    * */
    private String vertify(String token,String AppID,String trace){
            try {
                if (preVerify(token, AppID)) {
                    String validate = traceVerify(trace, token);
                    //Todo 建立更加精确的算法进行人机行为识别
                    return validate;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        private boolean preVerify(String token,String AppID){
             RedisModel redisModel = jedisService.get(token,RedisModel.class);
             String rAppID = redisModel.getAppID();
             return AppID.equals(rAppID);
        }
    /*
      根据轨迹特征值判断,返回生成validate参数
      y坐标标准差
      平均速度
      滑动时间
     */
    private String traceVerify(String trace,String token){
        String validate;
        String tracedata[]= getDecTrace(token,trace);
        double yVariance =getYVar(tracedata);
        double vAver = getAverV( tracedata);
        double slideTime = getSideTime(tracedata);
        System.out.print("Y方差："+yVariance);
        System.out.print("\n");
        System.out.print("X速度平均："+vAver);
        System.out.print("\n");
        System.out.print("滑动时间："+slideTime);
        System.out.print("\n");
        validate = TraceVerification.traceVerification(slideTime, vAver, yVariance);
       return validate;
    }

    /*
    解密鼠标轨迹字符串，返回数组："[action:x:y:time]"
     */
    private String[]  getDecTrace(String token,String trace){
        logger=Logger.getLogger(VerifyService.class);
        String decTrace="";
        final String KEY = token.substring(10,26);//取token16位作为解密的key
        try {
            decTrace = EncryptUtils.aesDecrypt(trace, KEY);
            //logger.info("slideTrace:"+decTrace);
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] tracedata = decTrace.split("-");
        return tracedata;
    }
    /*
      获取Y轴标准差
     */
    private double getYVar(String[] tracedata){
       return TraceUtils.getYVariance(tracedata);
    }
    /*
      获取滑动时间
     */
    private double getSideTime(String[] tracedata){
        double slideTime = TraceUtils.getSlideTime(tracedata);
        return  slideTime;
    }
    /*
      获取平均速度
     */
     private double getAverV(String[] tracedata){
         double vAver = TraceUtils.getXAverspeed(tracedata);
         return vAver;
     }
     /*
       获取鼠标抬起时滑动距离
      */
     private double getSlideLength(String[] tracedata){
              return TraceUtils.getSlideLength(tracedata);
     }
}

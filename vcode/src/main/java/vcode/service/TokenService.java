package vcode.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.org.mozilla.javascript.internal.Token;
import vcode.dao.JedisClient;
import vcode.dao.JedisClientSingle;
import vcode.enums.ConstantEnum;
import vcode.model.PictureModel;
import vcode.model.RedisModel;
import vcode.utils.Messager;
import vcode.utils.TokenGenerator;

import javax.annotation.Resource;

/**
 * Created by hubai on 2017/7/24.
 * 初始化时，Token服务
 */
@Service
public class TokenService {
    @Resource
    private JedisService jedisService;
    @Resource
    private PictureModelService pictureModelService;
    /*
    * 生成Token方法
    * 只在初始化的时候调用
    * 返回token和图片相关信息
    * */
    public String getTokenForPuzzle(String t){
        String token;
        if (t!=null) {
            token = TokenGenerator.tokenGenertor();
            JSONObject ret = pictureModelService.getPic(token,0);
            ret.put("flag",true);
            return ret.toString();
        }
        return null;
    }
  /*生成Token方法
    * 只在初始化的时候调用
    * 返回token
  * */
  public String getTokenForSlide(String t) {
      JSONObject ret = new JSONObject();
      String token;
      String AppID =ConstantEnum.SLIDEAPPID.toString();
      if (t != null) {
          token = TokenGenerator.tokenGenertor();
          RedisModel redisModel = new RedisModel();
          redisModel.setFailedCount(0);
          redisModel.setAppID(AppID);
          jedisService.set(token,redisModel,600);
          ret.put("token", token);
          ret.put("AppID",AppID);
          ret.put("flag", true);
          return ret.toString();
      }
      return null;
  }

}

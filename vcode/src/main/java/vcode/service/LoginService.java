package vcode.service;

import org.springframework.stereotype.Service;

import vcode.model.RedisModel;
import vcode.utils.Messager;

import javax.annotation.Resource;

/**
 * Created by hubai on 2017/8/7.
 * 用户验证成功后登陆服务
 */
@Service
public class LoginService {
    @Resource
    private JedisService jedisService;
    /*
    * 登录验证方法
    * */
    public Messager login(String useName,String validate,String pwd,String token){
        Messager result = new Messager();
        String rvalidate = jedisService.get(token, RedisModel.class).getValidate();
        System.out.print("RValidate"+rvalidate);
        if (validate.equals(rvalidate)){
            jedisService.delParam(token);  //ToDo 与数据库中的userName和password比较
            result.setFlag(true);
            return result;
        }
        return null;
    }
}

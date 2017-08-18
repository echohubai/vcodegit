package vcode.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vcode.dao.JedisClient;

import javax.annotation.Resource;

/**
 * Created by hubai on 2017/8/7.
 * Redis缓存操作
 */
@Service
public class JedisService {
    @Resource
    private JedisClient jedisClient;

   /*
   * 删除缓存中的参数
   * */
   @Transactional
   public void delParam(String  key){
       jedisClient.del(key);
   }
    /**
     * 向缓存中设置对象
     * @param key
     * @param value
     * @return
     */
    public  boolean  set(String key,Object value,int time){

        try {
            String objectJson = JSON.toJSONString(value);
            jedisClient.set(key,objectJson);
            jedisClient.expire(key,time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
    }
   }
    /**
     * 根据key 获取对象
     * @param key
     * @return
     */
    public  <T> T get(String key,Class<T> clazz){

        try {
            String value = jedisClient.get(key);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

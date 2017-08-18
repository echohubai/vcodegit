package vcode.utils;

import vcode.enums.ConstantEnum;

/**
 * Created by hubai on 2017/7/19.
 * Token生成工具
 */
public class TokenGenerator {
   //生成Token：常量+UUID MD5加密
    public static String tokenGenertor(){
        String token = EncryptUtils.md5(ConstantEnum.TOKENCONSTAN.toString())+EncryptUtils.md5(UuidUtils.generateShortUuid());
        return token;

    }
}

package vcode.utils;

import java.util.Random;

/**
 * Created by hubai on 2017/8/7.
 */
public class TokenUtils {
    //获取原始Token，即初始化时候的Token
    public static String getOrginalToken(String token){
        return token.substring(0,token.length()-3);
    }
    /**
     * 获取随机字母数字组合
     *
     * @param length
     *            字符串长度
     * @return
     */
    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                str += (char) (65 + random.nextInt(26));// 取得大写字母
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }
}

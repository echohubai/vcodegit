package vcode.utils;

import vcode.enums.ConstantEnum;

/**
 * Created by hubai on 2017/7/24.
 * 用户轨迹人机判别
 */
public class TraceVerification {
    /**
     * @Param slideTime 滑动时间
     * @param xAverV x方向平局速度
     * @param  yVar  y方向方差，判断滑动轨迹是否为直线
     * @return validate 验证成功后的验证码
     */

    public static String traceVerification(double slideTime,double xAverV,double yVar){
        String validaste;
        String param = String.valueOf(slideTime+xAverV+yVar);
        if (slideTime>80.0 && xAverV <2.5 && yVar>0){
              validaste = EncryptUtils.md5(ConstantEnum.VALIDATECONSTANT.toString())+EncryptUtils.md5(param)+EncryptUtils.md5(UuidUtils.generateShortUuid());
              return validaste;
        }
        else {
            return null;
        }
    }
}

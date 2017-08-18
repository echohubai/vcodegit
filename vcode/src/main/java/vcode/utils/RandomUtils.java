package vcode.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by liqiang on 2017/8/11.
 */
public class RandomUtils {
    public static int getRandPictureX(int Max_X,int Min_X) {
        Random rand =new Random();
        int randNum_X=rand.nextInt(Max_X-Min_X+1)+Min_X;
        System.out.println(randNum_X);
        return randNum_X;
    }
    public static int getRandPictureY(int Max_Y,int Min_Y) {
        Random rand =new Random();
        int randNum_Y=rand.nextInt(Max_Y-Min_Y+1)+Min_Y;
        System.out.println(randNum_Y);
        return randNum_Y;
    }

    public static String getleftTop_X(int randNum_X) {
        float leftTop_XOrigin=(float)(18*randNum_X)/511;//8.911938
        BigDecimal b1=new BigDecimal(leftTop_XOrigin);
        float f1=b1.setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println("leftTop_X:"+leftTop_XOrigin);
        System.out.println("leftTopX:"+ String.valueOf(f1));
        return String.valueOf(f1);
    }
    public static String getleftTop_Y(int randNum_Y) {
        float leftTop_YOrigin=(float)(12*randNum_Y)/340;
        BigDecimal b2=new BigDecimal(leftTop_YOrigin);
        float f2=b2.setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println("leftTop_Y:"+leftTop_YOrigin);
        System.out.println("leftTopY:"+ String.valueOf(f2));
        return String.valueOf(f2);
    }
}

package vcode.utils;

/**
 * Created by hubai on 2017/7/20.
 */
public class TraceUtils {
    /*
     获取X坐标滑动平均速度
     */
    public static double getXAverspeed(String data[]) {
        int len = data.length;
        System.out.print("数组长度：" + len);
        System.out.print("\n");
        double[] x ;
        double[] t ;
        double[] v = new double[len];
        x = dataExtract(data, 1);
        t = dataExtract(data, 3);

        for (int i = 1; i < len; i++) {
            if (t[i] - t[i - 1] != 0) {
                v[i] = (x[i] - x[i - 1]) / (t[i] - t[i - 1]);
            }
        }
        System.out.print("最大X轴速度：" + StatisticUtils.getMax(v));
        System.out.print("\n");
        return StatisticUtils.getAverage(v);
    }
    /*
     获取Y轴方差
     */
    public static double getYVariance(String[] trace){
        return StatisticUtils.getStandardDiviation(dataExtract(trace,2));
    }
    /*
     获取滑动时间
     */
    public static double getSlideTime(String[] trace){
        return StatisticUtils.getMax(dataExtract(trace,3));
    }
    private static double[] dataExtract(String data[],int j){
        int len = data.length;
        double[] result = new double[len];
        for (int i = 0; i < len; i++) {
            result[i] = Double.valueOf(data[i].split(":")[j]);
        }
        return result;

    }
        /*
        获取鼠标抬起时滑动距离
         */
        public static double getSlideLength(String[] trace){
            double[] x = dataExtract(trace,1);
            int len = x.length;
           return x[len-1]-x[0];

        }

}

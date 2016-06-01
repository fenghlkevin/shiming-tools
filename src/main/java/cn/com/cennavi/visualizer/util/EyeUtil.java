package cn.com.cennavi.visualizer.util;

import java.text.DecimalFormat;

/**
 * Created by fengh on 2016/6/1.
 */
public class EyeUtil {

    public static String getRealEye(double eye) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (eye == 3.9) {
            return "0.08";
        } else if (eye == 3.8) {
            return "0.06";
        } else if (eye == 3.7) {
            return "0.04";
        } else if (eye == 3.6) {
            return "0.02";
        } else if(eye>4L){
            return df.format(eye);
        }else{
            return "0";
        }
    }
}

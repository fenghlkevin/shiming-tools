package cn.com.cennavi.visualizer.util;

import cn.com.cennavi.kfgis.util.SBase64;
import cn.com.cennavi.kfgis.util.csv.*;
import cn.com.cennavi.kfgis.util.csv.CSVReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.List;

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
        } else if (eye == 5L) {
            return "5.0";
        } else if (eye == 4L) {
            return "4.0";
        } else if (eye > 4L) {
            return df.format(eye);
        } else {
            return "0";
        }
    }

    public static void main(String[] args) throws IOException {
        String file="C:\\Users\\fengh\\personal\\temp\\if_template_1 - 副本 (2).if";
        FileInputStream fis=new FileInputStream(file);
        byte[] bs=new byte[fis.available()];
        fis.read(bs);
        String s=new String(bs,"gbk");
        System.out.println(s);
        System.out.println(SBase64.encode(bs).length());
        cn.com.cennavi.kfgis.util.csv.CSVReader csvReader = new cn.com.cennavi.kfgis.util.csv.CSVReaderImpl(new StringReader(s), cn.com.cennavi.kfgis.util.csv.CSVReader.DEFAULT_SEPARATOR, CSVReader.DEFAULT_QUOTE_CHARACTER, 1);
        List<String[]> ss=csvReader.readAll();
        System.out.println(ss.size());

    }
}

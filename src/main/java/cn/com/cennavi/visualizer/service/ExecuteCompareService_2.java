package cn.com.cennavi.visualizer.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ExecuteCompareService_2 {
    public void compare(java.util.List<ExecuteParseExcelService.Student> students, ExecuteParams params, ExecuteIfTemplateService ifTemplateService) throws ParseException {

        for (ExecuteParseExcelService.Student student : students) {
            Map<String, String> replaceMap = new HashMap<String, String>();

            ExecuteParseExcelService.CompareEye compareEye = new ExecuteParseExcelService.CompareEye();
            boolean no_last2 = student.getLast2_check_left() <= 0 && student.getLast2_check_right() <= 0;
            boolean no_last1 = student.getLast1_check_left() <= 0 && student.getLast1_check_right() <= 0;

            int count = 0;
            if (no_last2 && no_last1) {
                count = 1;
            } else if (!no_last2 && no_last1) {
                count = 2;
            } else if (no_last2 && !no_last1) {
                count = 2;
            } else if (!no_last2 && !no_last1) {
                count = 3;
            }


            replaceMap.put("{student_name}", student.getStudent_name());
            replaceMap.put("{count}", String.valueOf(count));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            Date d = sdf.parse(params.getThis_date());
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(d.getTime());

            replaceMap.put("{the_year}", String.valueOf(c.get(Calendar.YEAR)));
            replaceMap.put("{the_month}", String.valueOf(c.get(Calendar.MONTH)));
            replaceMap.put("{last2_year}", getEyeDesc(student.getLast2_check_left(), student.getLast2_check_right(), params.getLast2_date()));
            replaceMap.put("{last1_year}", getEyeDesc(student.getLast1_check_left(), student.getLast1_check_right(), params.getLast_date()));


            ExecuteIfTemplateService.IfTemplate vision = ifTemplateService.getIf_vision().get("5.3");
            replaceMap.put("{vision}", vision == null ? "" : vision.getDesc());

            replaceMap.put("{sign_str}", params.getSign_str());
            replaceMap.put("{sign_date}", params.getSign_date());

            student.setReplaceMap(replaceMap);
        }
    }


    private String getEyeDesc(double left, double right, String date) {
        if (left <= 0 && right <= 0) {
            return "";
        }

        return date + "右眼" + (right <= 0 ? "无数据" : right) + "左眼" + (left <= 0 ? "无数据" : right);


    }


}

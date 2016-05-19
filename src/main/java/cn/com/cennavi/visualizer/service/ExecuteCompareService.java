package cn.com.cennavi.visualizer.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ExecuteCompareService {
    public void compare(java.util.List<ExecuteParseExcelService.Student> students, ExecuteParams params, ExecuteIfTemplateService ifTemplateService) {

        for (ExecuteParseExcelService.Student student : students) {
            Map<String, String> replaceMap = new HashMap<String, String>();

            ExecuteParseExcelService.CompareEye compareEye = new ExecuteParseExcelService.CompareEye();
            boolean no_last2 = student.getLast2_check_left() <= 0 && student.getLast2_check_right() <= 0;
            boolean no_last1 = student.getLast1_check_left() <= 0 && student.getLast1_check_right() <= 0;
            boolean no_this = student.getThis_check_left() <= 0 && student.getThis_check_right() <= 0;

            //boolean no_left = student.getLast2_check_left() <= 0 && student.getLast1_check_left() <= 0 && student.getThis_check_left() <= 0;
            //boolean no_right = student.getLast2_check_right() <= 0 && student.getLast1_check_right() <= 0 && student.getThis_check_right() <= 0;

            int count;
            int left_change, right_change;
            int left_right_diff;

            if (no_this && no_last1 && no_last2) {
                continue;
            } else {
                if (no_last2 && no_last1) {
                    compareEye.setThis_left(student.getThis_check_left());
                    compareEye.setThis_right(student.getThis_check_right());
                    count = 1;
                    left_change = 0;
                    right_change = 0;
                } else if (no_last1 && no_this) {
                    compareEye.setThis_left(student.getLast2_check_left());
                    compareEye.setThis_right(student.getLast2_check_right());
                    count = 1;
                    left_change = 0;
                    right_change = 0;
                } else if (no_last2 && no_this) {
                    compareEye.setThis_left(student.getLast1_check_left());
                    compareEye.setThis_right(student.getLast1_check_right());
                    count = 1;
                    left_change = 0;
                    right_change = 0;
                } else if (no_this) {
                    compareEye.setThis_left(student.getLast1_check_left());
                    compareEye.setThis_right(student.getLast1_check_right());

                    compareEye.setLast_left(student.getLast2_check_left());
                    compareEye.setLast_right(student.getLast2_check_right());

                    count = 2;

                    left_change = (int) ((compareEye.getLast_left() - compareEye.getThis_left()) * 10);
                    right_change = (int) ((compareEye.getLast_right() - compareEye.getThis_right()) * 10);
                } else if (no_last1) {
                    compareEye.setThis_left(student.getThis_check_left());
                    compareEye.setThis_right(student.getThis_check_right());

                    compareEye.setLast_left(student.getLast2_check_left());
                    compareEye.setLast_right(student.getLast2_check_right());

                    count = 2;

                    left_change = (int) ((compareEye.getLast_left() - compareEye.getThis_left()) * 10);
                    right_change = (int) ((compareEye.getLast_right() - compareEye.getThis_right()) * 10);
                } else if (no_last2) {
                    compareEye.setThis_left(student.getThis_check_left());
                    compareEye.setThis_right(student.getThis_check_right());

                    compareEye.setLast_left(student.getLast1_check_left());
                    compareEye.setLast_right(student.getLast1_check_right());

                    count = 2;

                    left_change = (int) ((compareEye.getLast_left() - compareEye.getThis_left()) * 10);
                    right_change = (int) ((compareEye.getLast_right() - compareEye.getThis_right()) * 10);
                } else {
                    compareEye.setLast_left(Math.min(student.getLast2_check_left(), student.getLast1_check_left()));
                    compareEye.setLast_right(Math.min(student.getLast2_check_right(), student.getLast1_check_right()));

                    compareEye.setThis_left(student.getThis_check_left());
                    compareEye.setThis_right(student.getThis_check_right());

                    count = 3;

                    left_change = (int) ((compareEye.getThis_left() - compareEye.getLast_left()) * 10);
                    right_change = (int) (( compareEye.getThis_right()- compareEye.getLast_right()) * 10);
                }
            }

            left_right_diff = Math.abs(((int) ((compareEye.getThis_left() - compareEye.getThis_right()) * 10)));

            replaceMap.put("{student_name}", student.getStudent_name());
            replaceMap.put("{count}", String.valueOf(count));
            ExecuteIfTemplateService.IfTemplate eye_state = ifTemplateService.getIf_EyeState().get(getEyeState(left_change, right_change));
            replaceMap.put("{eye_state}", eye_state == null ? "" : eye_state.getDesc());
            replaceMap.put("{last2_year}", getEyeDesc(student.getLast2_check_left(), student.getLast2_check_right(), params.getLast2_date()));
            replaceMap.put("{last1_year}", getEyeDesc(student.getLast1_check_left(), student.getLast1_check_right(), params.getLast_date()));
            replaceMap.put("{this_year}", getEyeDesc(student.getThis_check_left(), student.getThis_check_right(), params.getThis_date()));

            replaceMap.put("{eye_right_state}", right_change <= 0 ? "下降" : "提升");
            replaceMap.put("{right_diff}", String.valueOf(Math.abs(right_change)));
            replaceMap.put("{eye_left_state}", left_change <= 0 ? "下降" : "提升");
            replaceMap.put("{left_diff}", String.valueOf(Math.abs(left_change)));

            ExecuteIfTemplateService.IfTemplate descentSpeed = ifTemplateService.getIf_DescentSpeed().get(getDescentSpeed(left_change, right_change));
            replaceMap.put("{descent_speed}", descentSpeed == null ? "" : descentSpeed.getDesc());

            ExecuteIfTemplateService.IfTemplate parallax = ifTemplateService.getIf_parallax().get(String.valueOf(left_right_diff));
            replaceMap.put("{parallax}", parallax == null ? "" : parallax.getDesc());

            ExecuteIfTemplateService.IfTemplate vision = ifTemplateService.getIf_vision().get(getVision(compareEye.getThis_left(), compareEye.getThis_right()));
            replaceMap.put("{vision}", vision == null ? "" : vision.getDesc());

            replaceMap.put("{sign_str}",params.getSign_str());
            replaceMap.put("{sign_date}",params.getSign_date());

            student.setReplaceMap(replaceMap);
        }
    }

    private String getVision(double left, double right) {
        return left <= right ? String.valueOf(left) : String.valueOf(right);
    }

    private String getDescentSpeed(int left_change, int right_change) {
        return left_change <= right_change ? String.valueOf(left_change) : String.valueOf(right_change);
    }

    private String getEyeDesc(double left, double right, String date) {
        if (left <= 0 && right <= 0) {
            return "";
        }

        return date+"右眼" + (right <= 0 ? "无数据" : right) + "左眼" + (left <= 0 ? "无数据" : right);


    }

    private String getEyeState(int left_change, int right_change) {
        if (left_change < 0 || right_change < 0) {
            return "down";
        } else if (left_change > 0 || right_change > 0) {
            return "up";
        } else if (left_change == 0 && right_change == 0) {
            return "same";
        }

        return "same";
    }
}

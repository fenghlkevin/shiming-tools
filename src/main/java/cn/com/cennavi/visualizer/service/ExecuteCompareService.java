package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.visualizer.util.EyeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ExecuteCompareService {


    public void compare(java.util.List<ExecuteParseExcelService.Student> students, ExecuteParams params, ExecuteIfTemplateService ifTemplateService) {

        for (ExecuteParseExcelService.Student student : students) {
//            if(student.getStudent_name().equalsIgnoreCase("尚尔涵")){
//                System.out.println();
//            }
            Map<String, String> replaceMap = new HashMap<String, String>();

            ExecuteParseExcelService.CompareEye compareEye = new ExecuteParseExcelService.CompareEye();
            boolean no_last2 = student.getLast2_check_left() <= 0 && student.getLast2_check_right() <= 0;
            boolean no_last1 = student.getLast1_check_left() <= 0 && student.getLast1_check_right() <= 0;
            boolean no_this = student.getThis_check_left() <= 0 && student.getThis_check_right() <= 0;

            boolean no_left = student.getLast2_check_left() <= 0 && student.getLast1_check_left() <= 0 && student.getThis_check_left() <= 0;
            boolean no_right = student.getLast2_check_right() <= 0 && student.getLast1_check_right() <= 0 && student.getThis_check_right() <= 0;

            int count;
            int left_change = 0, right_change = 0;
            int left_right_diff;

            if (no_this && no_last1 && no_last2) {
                count = 3;
            } else if (no_last2 && no_last1) {
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
//                compareEye.setLast_left(Math.min(student.getLast2_check_left(), student.getLast1_check_left()));
//                compareEye.setLast_right(Math.min(student.getLast2_check_right(), student.getLast1_check_right()));
                compareEye.setLast_left(student.getLast2_check_left());
                compareEye.setLast_right(student.getLast2_check_right());

                compareEye.setThis_left(student.getThis_check_left());
                compareEye.setThis_right(student.getThis_check_right());

                count = 3;

                left_change = (int) ((compareEye.getThis_left() * 10 - compareEye.getLast_left() * 10));
                right_change = (int) ((compareEye.getThis_right() * 10 - compareEye.getLast_right() * 10));
            }
//            }

            left_right_diff = Math.abs(((int) ((compareEye.getThis_left() * 10 - compareEye.getThis_right() * 10))));

            replaceMap.put("{student_name}", student.getStudent_name());
            replaceMap.put("{count}", String.valueOf(count));
            ExecuteIfTemplateService.IfTemplate eye_state = ifTemplateService.getIf_template_condition().get("eye_state").get(getEyeState(left_change, right_change));
            replaceMap.put("{eye_state}", eye_state == null ? "" : eye_state.getDesc());
            replaceMap.put("{last2_year}", getEyeDesc(student.getLast2_check_left(), student.getLast2_check_right(), params.getLast2_date()));
            replaceMap.put("{last1_year}", getEyeDesc(student.getLast1_check_left(), student.getLast1_check_right(), params.getLast_date()));
            replaceMap.put("{this_year}", getEyeDesc(student.getThis_check_left(), student.getThis_check_right(), params.getThis_date()));

            replaceMap.put("{eye_right_state}", right_change <= 0 ? "下降" : "提升");
            replaceMap.put("{right_diff}", String.valueOf(Math.abs(right_change)));
            replaceMap.put("{eye_left_state}", left_change <= 0 ? "下降" : "提升");
            replaceMap.put("{left_diff}", String.valueOf(Math.abs(left_change)));

            ExecuteIfTemplateService.IfTemplate descentSpeed = ifTemplateService.getIf_template_condition().get("descent_speed").get(getDescentSpeed(left_change, right_change));
            replaceMap.put("{descent_speed}", descentSpeed == null ? "" : descentSpeed.getDesc());

            ExecuteIfTemplateService.IfTemplate parallax = ifTemplateService.getIf_template_condition().get("parallax").get(String.valueOf(left_right_diff));
            replaceMap.put("{parallax}", (parallax == null || (no_left || no_right)) ? "" : parallax.getDesc());

            ExecuteIfTemplateService.IfTemplate vision = ifTemplateService.getIf_template_condition().get("vision").get(getVision(compareEye.getThis_left(), compareEye.getThis_right()));
            replaceMap.put("{vision}", vision == null ? "" : vision.getDesc());

            ExecuteIfTemplateService.IfTemplate suggest = ifTemplateService.getIf_template_condition().get("suggest").get(getSuggest(compareEye.getThis_left(), compareEye.getThis_right(), left_right_diff));
            replaceMap.put("{suggest}", suggest == null ? "" : suggest.getDesc());

            Map<String, ExecuteIfTemplateService.IfTemplate> vision_warn_map = ifTemplateService.getIf_template_condition().get("vision_warn");
            if (vision_warn_map != null) {
                ExecuteIfTemplateService.IfTemplate vision_warn = vision_warn_map.get(getVision_warn(left_change, right_change));
                replaceMap.put("{vision_warn}", vision_warn == null ? "" : vision_warn.getDesc());
            }


//            ExecuteIfTemplateService.IfTemplate warning = ifTemplateService.getIf_template_condition().get("warning").get(getSuggest(compareEye.getThis_left(), compareEye.getThis_right(), left_right_diff));
//            replaceMap.put("{warning}", warning == null ? "" : warning.getDesc());

            replaceMap.put("{sign_str}", params.getSign_str());
            replaceMap.put("{sign_date}", params.getSign_date());

            student.setReplaceMap(replaceMap);
        }
    }

    private String getVision_warn(int left_change, int right_change) {
//        if(left_change>=0&&right_change>=0){
//            int max = Math.max(left_change, right_change);
//            if(max>5){
//                return "warn_more_p";
//            }else{
//                return "warn_"+max+"_p";
//            }
//        }
         if(left_change<0&&right_change<0){
            int min = Math.min(left_change, right_change);
            if(min<-7){
                return "warn_-more";
            }else{
                return "warn_"+min;
            }
        }else{
             int max = Math.max(left_change, right_change);
             if(max>7){
                 return "warn_more";
             }else{
                 return "warn_"+max;
             }
        }
    }

    private String getSuggest(double left, double right, int diff) {
        String diffS = String.valueOf(diff);
        if (diff > 5) {
            diffS = "more";
        }
        if (left <= 0) {
            return String.valueOf(right) + "_noleft";
        } else if (right <= 0) {
            return String.valueOf(left) + "_noright";
        } else {
            return left <= right ? String.valueOf(left) + "_p" + diffS : String.valueOf(right) + "_p" + diffS;
        }
    }

    private String getEyeDesc(double left, double right, String date) {
//        if (left <= 0 && right <= 0) {
//            return "";
//        }

        return date + "右眼" + (right <= 0 ? "无数据" : EyeUtil.getRealEye(right)) + "左眼" + (left <= 0 ? "无数据" : EyeUtil.getRealEye(left));


    }

    private String getVision(double left, double right) {
        if (left <= 0) {
            return String.valueOf(right) + "_noleft";
        } else if (right <= 0) {
            return String.valueOf(left) + "_noright";
        } else {
            return left <= right ? String.valueOf(left) : String.valueOf(right);
        }

    }

    private String getDescentSpeed(int left_change, int right_change) {
        return left_change <= right_change ? String.valueOf(left_change) : String.valueOf(right_change);
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

package cn.com.cennavi.visualizer.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengh on 2016/5/13.
 */
//@Service
public class ExecuteGroupService {

    public Map<String, List<String[]>> execute(List<ExecuteParseExcelService.Student> students) throws IOException {
        DecimalFormat df = new DecimalFormat("#.#");
        Map<String, List<String[]>> group = new HashMap<String, List<String[]>>();

        for (ExecuteParseExcelService.Student student : students) {
            String key = student.getMschool() + "_" + student.getMgrade() + "_" + student.getMclass() + "_" + student.getMgroup();
            List<String[]> groupStudents = null;
            if (!group.containsKey(key)) {
                groupStudents = new ArrayList<String[]>();
                group.put(key, groupStudents);
            }
            groupStudents = group.get(key);

            String[] line = new String[12];

            line[0] = String.valueOf(groupStudents.size() + 1);
            line[1] = student.getStudent_name();
            line[2] = df.format(student.getLast2_check_right());
            line[3] = df.format(student.getLast2_check_left());
            line[4] = df.format(student.getLast1_check_right());
            line[5] = df.format(student.getLast1_check_left());
            line[6] = getCompare(student.getLast2_check_right(), student.getLast1_check_right());
            line[7] = getCompare(student.getLast2_check_left(), student.getLast1_check_left());
            line[8] = df.format(student.getThis_check_right());
            line[9] = df.format(student.getThis_check_left());
            line[10] = getCompare(student.getLast1_check_right(), student.getThis_check_right());
            line[11] = getCompare(student.getLast1_check_left(), student.getThis_check_left());
            groupStudents.add(line);
        }
        return group;
    }

    private String getCompare(double last, double thiz) {
        if (last == -1 || thiz == -1) {
            return "";
        } else {
            double t = (thiz - last) * 10;
            return String.valueOf(new BigDecimal(t).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());

        }
    }
}

package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.util.SBase64;
import cn.com.cennavi.visualizer.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fengh on 2016/5/6.
 */
public class ExecuteParseExcelService {

    public List<Student> execute(ExecuteParams params) throws IOException {
        Workbook workbook = new HSSFWorkbook(new ByteArrayInputStream(SBase64.decode(params.getInput_file())));

        Sheet sheet = workbook.getSheet(params.getSheet_no());
        int allrow = params.getEnd_row() <= 0 ? sheet.getLastRowNum() : params.getEnd_row();

        List<Student> students = new ArrayList<Student>();
        for (int i = params.getStart_row() - 1; i <= allrow; i++) {

            Row row = sheet.getRow(i);
            Student student = new Student();

            student.setStudent_name(ExcelUtil.getCellValue(row, params.getStudent_name_no()));

            student.setLast2_check_right(getEyeValue(new Double(ExcelUtil.getCellValue(row, params.getLast2_check(), "-1"))));
            student.setLast2_check_left(getEyeValue(new Double(ExcelUtil.getCellValue(row, params.getLast2_check() + 1, "-1"))));
            student.setLast1_check_right(getEyeValue(new Double(ExcelUtil.getCellValue(row, params.getLast1_check(), "-1"))));
            student.setLast1_check_left(getEyeValue(new Double(ExcelUtil.getCellValue(row, params.getLast1_check() + 1, "-1"))));
            if (!"2".equalsIgnoreCase(params.getDomethod())) {
                student.setThis_check_right(getEyeValue(new Double(ExcelUtil.getCellValue(row, params.getThis_check(), "-1"))));
                student.setThis_check_left(getEyeValue(new Double(ExcelUtil.getCellValue(row, params.getThis_check() + 1, "-1"))));
                student.setMschool(ExcelUtil.getCellValue(row, params.getMclass()));
                student.setMgrade(ExcelUtil.getCellValue(row, params.getMgrade()));
                student.setMclass(ExcelUtil.getCellValue(row, params.getMclass()));
                student.setMgroup(ExcelUtil.getCellValue(row, params.getMgroup()));
            }

            students.add(student);
        }
        return students;
    }

    private double getEyeValue(Double d) {
        if (d.doubleValue() >= 4) {
            return d;
        } else {
            if (d.doubleValue() == 0.08) {
                return 3.9D;
            } else if (d.doubleValue() == 0.06) {
                return 3.8D;
            } else if (d.doubleValue() == 0.04) {
                return 3.7D;
            } else if (d.doubleValue() == 0.02) {
                return 3.6D;
            } else {
                return -1D;
            }
        }

    }

    public static class CompareEye {
        private double last_left;

        private double last_right;

        private double this_left;

        private double this_right;

        private int count = 0;

        private String eye_state;

        private String last2_year;

        private String last1_year;

        private String this_year;

        private String eye_left_state;

        private String eye_right_state;

        private int eye_left_diff;

        private int eye_right_diff;

        private ExecuteIfTemplateService.IfTemplate a;

        private ExecuteIfTemplateService.IfTemplate b;

        private ExecuteIfTemplateService.IfTemplate c;

        public double getLast_left() {
            return last_left;
        }

        public void setLast_left(double last_left) {
            this.last_left = last_left;
        }

        public double getLast_right() {
            return last_right;
        }

        public void setLast_right(double last_right) {
            this.last_right = last_right;
        }

        public double getThis_left() {
            return this_left;
        }

        public void setThis_left(double this_left) {
            this.this_left = this_left;
        }

        public double getThis_right() {
            return this_right;
        }

        public void setThis_right(double this_right) {
            this.this_right = this_right;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getEye_state() {
            return eye_state;
        }

        public void setEye_state(String eye_state) {
            this.eye_state = eye_state;
        }

        public String getLast2_year() {
            return last2_year;
        }

        public void setLast2_year(String last2_year) {
            this.last2_year = last2_year;
        }

        public String getLast1_year() {
            return last1_year;
        }

        public void setLast1_year(String last1_year) {
            this.last1_year = last1_year;
        }

        public String getThis_year() {
            return this_year;
        }

        public void setThis_year(String this_year) {
            this.this_year = this_year;
        }

        public String getEye_left_state() {
            return eye_left_state;
        }

        public void setEye_left_state(String eye_left_state) {
            this.eye_left_state = eye_left_state;
        }

        public String getEye_right_state() {
            return eye_right_state;
        }

        public void setEye_right_state(String eye_right_state) {
            this.eye_right_state = eye_right_state;
        }

        public int getEye_left_diff() {
            return eye_left_diff;
        }

        public void setEye_left_diff(int eye_left_diff) {
            this.eye_left_diff = eye_left_diff;
        }

        public int getEye_right_diff() {
            return eye_right_diff;
        }

        public void setEye_right_diff(int eye_right_diff) {
            this.eye_right_diff = eye_right_diff;
        }

        public ExecuteIfTemplateService.IfTemplate getA() {
            return a;
        }

        public void setA(ExecuteIfTemplateService.IfTemplate a) {
            this.a = a;
        }

        public ExecuteIfTemplateService.IfTemplate getB() {
            return b;
        }

        public void setB(ExecuteIfTemplateService.IfTemplate b) {
            this.b = b;
        }

        public ExecuteIfTemplateService.IfTemplate getC() {
            return c;
        }

        public void setC(ExecuteIfTemplateService.IfTemplate c) {
            this.c = c;
        }
    }

    public static class Student {

        private Map<String, String> replaceMap;

        private String student_name;

        private String mschool;

        private String mgrade;

        private String mclass;

        private String mgroup;

        private double last2_check_right;
        private double last2_check_left;

        private double last1_check_right;
        private double last1_check_left;

        private double this_check_right;
        private double this_check_left;

        public Map<String, String> getReplaceMap() {
            return replaceMap;
        }

        public void setReplaceMap(Map<String, String> replaceMap) {
            this.replaceMap = replaceMap;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getMschool() {
            return mschool;
        }

        public void setMschool(String mschool) {
            this.mschool = mschool;
        }

        public String getMgrade() {
            return mgrade;
        }

        public void setMgrade(String mgrade) {
            this.mgrade = mgrade;
        }

        public String getMclass() {
            return mclass;
        }

        public void setMclass(String mclass) {
            this.mclass = mclass;
        }

        public String getMgroup() {
            return mgroup;
        }

        public void setMgroup(String mgroup) {
            this.mgroup = mgroup;
        }

        public double getLast2_check_right() {
            return last2_check_right;
        }

        public void setLast2_check_right(double last2_check_right) {
            this.last2_check_right = last2_check_right;
        }

        public double getLast2_check_left() {
            return last2_check_left;
        }

        public void setLast2_check_left(double last2_check_left) {
            this.last2_check_left = last2_check_left;
        }

        public double getLast1_check_right() {
            return last1_check_right;
        }

        public void setLast1_check_right(double last1_check_right) {
            this.last1_check_right = last1_check_right;
        }

        public double getLast1_check_left() {
            return last1_check_left;
        }

        public void setLast1_check_left(double last1_check_left) {
            this.last1_check_left = last1_check_left;
        }

        public double getThis_check_right() {
            return this_check_right;
        }

        public void setThis_check_right(double this_check_right) {
            this.this_check_right = this_check_right;
        }

        public double getThis_check_left() {
            return this_check_left;
        }

        public void setThis_check_left(double this_check_left) {
            this.this_check_left = this_check_left;
        }
    }


}



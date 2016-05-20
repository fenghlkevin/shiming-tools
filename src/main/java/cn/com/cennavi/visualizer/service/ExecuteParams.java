package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.common.param.AbstractReqParams;
import cn.com.cennavi.kfgis.framework.annotation.Ignore;
import cn.com.cennavi.kfgis.framework.annotation.NotNullValid;

/**
 * Created by fengh on 2016/5/13.
 */
public class ExecuteParams extends AbstractReqParams {

    private String license;

    private String domethod;

    private String input_file;

    private String last2_date;

    private String last_date;

    private String this_date;

    private String sign_date;

    @NotNullValid(false)
    private String sign_str;

    private String sheet_no;

    private int student_name_no;

    private int mgrade;

    private int mclass;

    private int mgroup;

    private int mschool;

    private int start_row;

    @NotNullValid(false)
    private int end_row;

    private int last2_check;

    private int last1_check;

    private int this_check;

    private String word_template;

    @NotNullValid(false)
    private String if_file;

    public String getInput_file() {
        return input_file;
    }

    public void setInput_file(String input_file) {
        this.input_file = input_file;
    }

    public String getLast2_date() {
        return last2_date;
    }

    public void setLast2_date(String last2_date) {
        this.last2_date = last2_date;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getThis_date() {
        return this_date;
    }

    public void setThis_date(String this_date) {
        this.this_date = this_date;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public String getSign_str() {
        return sign_str;
    }

    public void setSign_str(String sign_str) {
        this.sign_str = sign_str;
    }

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

    public int getStudent_name_no() {
        return student_name_no;
    }

    public void setStudent_name_no(int student_name_no) {
        this.student_name_no = student_name_no;
    }

    public int getMgrade() {
        return mgrade;
    }

    public void setMgrade(int mgrade) {
        this.mgrade = mgrade;
    }

    public int getMclass() {
        return mclass;
    }

    public void setMclass(int mclass) {
        this.mclass = mclass;
    }

    public int getMgroup() {
        return mgroup;
    }

    public void setMgroup(int mgroup) {
        this.mgroup = mgroup;
    }

    public int getMschool() {
        return mschool;
    }

    public void setMschool(int mschool) {
        this.mschool = mschool;
    }

    public int getStart_row() {
        return start_row;
    }

    public void setStart_row(int start_row) {
        this.start_row = start_row;
    }

    public int getEnd_row() {
        return end_row;
    }

    public void setEnd_row(int end_row) {
        this.end_row = end_row;
    }

    public String getWord_template() {
        return word_template;
    }

    public void setWord_template(String word_template) {
        this.word_template = word_template;
    }

    public String getIf_file() {
        return if_file;
    }

    public void setIf_file(String if_file) {
        this.if_file = if_file;
    }

    public int getLast2_check() {
        return last2_check;
    }

    public void setLast2_check(int last2_check) {
        this.last2_check = last2_check;
    }

    public int getLast1_check() {
        return last1_check;
    }

    public void setLast1_check(int last1_check) {
        this.last1_check = last1_check;
    }

    public int getThis_check() {
        return this_check;
    }

    public void setThis_check(int this_check) {
        this.this_check = this_check;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDomethod() {
        return domethod;
    }

    public void setDomethod(String domethod) {
        this.domethod = domethod;
    }
}

package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.framework.util.ObjUtil;
import cn.com.cennavi.kfgis.util.SBase64;
import cn.com.cennavi.kfgis.util.csv.CSVReader;
import cn.com.cennavi.kfgis.util.csv.CSVReaderImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengh on 2016/5/16.
 */
public class ExecuteIfTemplateService {


//    private Map<String, IfTemplate> if_vision = new HashMap<String, IfTemplate>();
//
//    private Map<String, IfTemplate> if_suggest = new HashMap<String, IfTemplate>();
//
//    private Map<String, IfTemplate> if_parallax = new HashMap<String, IfTemplate>();
//
//    private Map<String, IfTemplate> if_DescentSpeed = new HashMap<String, IfTemplate>();
//
//    private Map<String, IfTemplate> if_EyeState = new HashMap<String, IfTemplate>();

    private Map<String,Map<String, IfTemplate>> if_template_condition=new HashMap<String, Map<String, IfTemplate>>();

    public void execute(String fileBase64) throws IOException {
        byte[] bs = SBase64.decode(fileBase64);

        Reader reader = new InputStreamReader(new ByteArrayInputStream(bs));

        CSVReader csvReader = new CSVReaderImpl(reader, CSVReader.DEFAULT_SEPARATOR, CSVReader.DEFAULT_QUOTE_CHARACTER, 1);

        String line[] = null;

        while ((line = csvReader.readNext()) != null) {
            if(ObjUtil.isEmpty(line)){
                continue;
            }
            IfTemplate ifTemplate = new IfTemplate();

            ifTemplate.setPosition(line[0].trim());
            //ifTemplate.setType(new Integer(line[1].trim()));
            ifTemplate.setCondition(line[1].trim());
            ifTemplate.setDesc(line[2].trim());


            if(!if_template_condition.containsKey(ifTemplate.getPosition())){
                if_template_condition.put(ifTemplate.getPosition(),new HashMap<String, IfTemplate>());
            }
            Map<String, IfTemplate> if_map=if_template_condition.get(ifTemplate.getPosition());
            if_map.put(ifTemplate.getCondition(), ifTemplate);


//
//            Map<String, IfTemplate> if_map = null;
//            if (ifTemplate.getCondition() .equalsIgnoreCase("vision")) {
//                if_map = if_vision;
//            } else if (ifTemplate.getCondition() .equalsIgnoreCase("parallax")) {
//                if_map = if_parallax;
//            } else if (ifTemplate.getCondition() .equalsIgnoreCase("DescentSpeed")) {
//                if_map = if_DescentSpeed;
//            } else if (ifTemplate.getCondition() .equalsIgnoreCase("EyeState")) {
//                if_map = if_EyeState;
//            }else if(ifTemplate.getCondition().equalsIgnoreCase("suggest")){
//                if_map=if_suggest;
//            }
//
//            if (if_map != null) {
//                if_map.put(ifTemplate.getCondition(), ifTemplate);
//            }

        }
    }

//    public Map<String, IfTemplate> getIf_vision() {
//        return if_vision;
//    }
//
//    public void setIf_vision(Map<String, IfTemplate> if_vision) {
//        this.if_vision = if_vision;
//    }
//
//    public Map<String, IfTemplate> getIf_parallax() {
//        return if_parallax;
//    }
//
//    public void setIf_parallax(Map<String, IfTemplate> if_parallax) {
//        this.if_parallax = if_parallax;
//    }
//
//    public Map<String, IfTemplate> getIf_DescentSpeed() {
//        return if_DescentSpeed;
//    }
//
//    public void setIf_DescentSpeed(Map<String, IfTemplate> if_DescentSpeed) {
//        this.if_DescentSpeed = if_DescentSpeed;
//    }
//
//    public Map<String, IfTemplate> getIf_EyeState() {
//        return if_EyeState;
//    }
//
//    public void setIf_EyeState(Map<String, IfTemplate> if_EyeState) {
//        this.if_EyeState = if_EyeState;
//    }


    public Map<String, Map<String, IfTemplate>> getIf_template_condition() {
        return if_template_condition;
    }

    public void setIf_template_condition(Map<String, Map<String, IfTemplate>> if_template_condition) {
        this.if_template_condition = if_template_condition;
    }

    public static class IfTemplate {
        private String position;

        private int type;

        private String condition;

        private String desc;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

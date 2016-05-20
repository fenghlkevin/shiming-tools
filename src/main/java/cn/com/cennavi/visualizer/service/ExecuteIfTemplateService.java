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


    private Map<String, IfTemplate> if_vision = new HashMap<String, IfTemplate>();

    private Map<String, IfTemplate> if_parallax = new HashMap<String, IfTemplate>();

    private Map<String, IfTemplate> if_DescentSpeed = new HashMap<String, IfTemplate>();

    private Map<String, IfTemplate> if_EyeState = new HashMap<String, IfTemplate>();

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
            ifTemplate.setType(new Integer(line[1].trim()));
            ifTemplate.setCondition(line[2].trim());
            ifTemplate.setDesc(line[3].trim());
            Map<String, IfTemplate> if_map = null;
            if (ifTemplate.getType() == 1) {
                if_map = if_vision;
            } else if (ifTemplate.getType() == 2) {
                if_map = if_parallax;
            } else if (ifTemplate.getType() == 3) {
                if_map = if_DescentSpeed;
            } else if (ifTemplate.getType() == 4) {
                if_map = if_EyeState;
            }

            if (if_map != null) {
                if_map.put(ifTemplate.getCondition(), ifTemplate);
            }

        }
    }

    public Map<String, IfTemplate> getIf_vision() {
        return if_vision;
    }

    public void setIf_vision(Map<String, IfTemplate> if_vision) {
        this.if_vision = if_vision;
    }

    public Map<String, IfTemplate> getIf_parallax() {
        return if_parallax;
    }

    public void setIf_parallax(Map<String, IfTemplate> if_parallax) {
        this.if_parallax = if_parallax;
    }

    public Map<String, IfTemplate> getIf_DescentSpeed() {
        return if_DescentSpeed;
    }

    public void setIf_DescentSpeed(Map<String, IfTemplate> if_DescentSpeed) {
        this.if_DescentSpeed = if_DescentSpeed;
    }

    public Map<String, IfTemplate> getIf_EyeState() {
        return if_EyeState;
    }

    public void setIf_EyeState(Map<String, IfTemplate> if_EyeState) {
        this.if_EyeState = if_EyeState;
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

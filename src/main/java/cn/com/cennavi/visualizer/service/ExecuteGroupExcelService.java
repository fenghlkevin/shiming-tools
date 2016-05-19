package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.visualizer.util.CSVWriter;
import cn.com.cennavi.visualizer.util.CSVWriterImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengh on 2016/5/19.
 */
public class ExecuteGroupExcelService {

    public Map<String, byte[]> execute(Map<String, List<String[]>> groups) {
        Map<String, byte[]> reMap=new HashMap<String, byte[]>();
        for(Map.Entry<String,List<String[]>> entry:groups.entrySet()){
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(baos);
            CSVWriter csvWriter=new CSVWriterImpl(osw);
            List<String[]> value=entry.getValue();
            for(String[] line:value){
                csvWriter.writeNext(line);
            }

            reMap.put(entry.getKey(),baos.toByteArray());

        }
        return reMap;
    }
}

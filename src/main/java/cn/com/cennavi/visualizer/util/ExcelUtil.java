package cn.com.cennavi.visualizer.util;

import cn.com.cennavi.kfgis.framework.util.ObjUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by fengh on 2016/5/16.
 */
public class ExcelUtil {

    public static String getCellValue(Row row, int index,String defaultValue) {
        String temp=getCellValue(row,index);
        if(ObjUtil.isEmpty(temp)||"#N/A".equalsIgnoreCase(temp.trim())||"0".equalsIgnoreCase(temp.trim())){
            return defaultValue;
        }
        return temp;
    }
    public static String getCellValue(Row row, int index) {
        HSSFCell cell= (HSSFCell) row.getCell(index-1);
        DecimalFormat df = new DecimalFormat("#.##");
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
                return df.format(cell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_STRING:
                // System.out.println(cell.getStringCellValue());
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case HSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return "";
    }
}

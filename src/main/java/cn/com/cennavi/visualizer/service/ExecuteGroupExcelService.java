package cn.com.cennavi.visualizer.service;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengh on 2016/5/19.
 */
public class ExecuteGroupExcelService {

    public byte[] execute(Map<String, List<String[]>> groups) throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook();


        Map<String, byte[]> reMap = new HashMap<String, byte[]>();
        for (Map.Entry<String, List<String[]>> entry : groups.entrySet()) {

            Sheet sheet = wb.createSheet(entry.getKey());
            createRow1(wb,sheet);
            createRow2(wb,sheet);
            createRow3(wb,sheet);
            createRow4(wb,sheet);
            List<String[]> tempList=entry.getValue();
            for(int i=0;i<tempList.size();i++){
                createDataRow(wb,sheet,i+4,tempList.get(i));
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);

        return out.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        createRow1(wb,sheet);
        createRow2(wb,sheet);
        createRow3(wb,sheet);
        createRow4(wb,sheet);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\fengh\\personal\\temp\\workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    private static void createRow1(HSSFWorkbook wb, Sheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("视  力  监  测  记  录  表");
        CellStyle cellStyle = cell.getCellStyle();

        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                14  //last column  (0-based)
        ));
    }

    private static void createRow2(HSSFWorkbook wb, Sheet sheet){
        Row row = sheet.createRow(1);
        Cell row2cell1 = row.createCell(0);
        row2cell1.setCellValue("学校：");
        Cell row2cell13 = row.createCell(13);
        row2cell13.setCellValue("班级：年  班 组");
    }

    private static void createRow3(HSSFWorkbook wb, Sheet sheet){
        Row row = sheet.createRow(2);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("序号");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell1.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                3, //last row  (0-based)
                0, //first column (0-based)
                0  //last column  (0-based)
        ));

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("姓名");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell2.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                3, //last row  (0-based)
                1, //first column (0-based)
                1  //last column  (0-based)
        ));

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("第1次视力检测");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell3.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                2, //first column (0-based)
                3  //last column  (0-based)
        ));

        Cell cell5 = row.createCell(4);
        cell5.setCellValue("第2次视力检测");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell5.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                4, //first column (0-based)
                5  //last column  (0-based)
        ));

        Cell cell7 = row.createCell(6);
        cell7.setCellValue("与第一次对比结果");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell7.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                6, //first column (0-based)
                7  //last column  (0-based)
        ));

        Cell cell9 = row.createCell(8);
        cell9.setCellValue("第3次视力检测");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell9.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                8, //first column (0-based)
                9  //last column  (0-based)
        ));

        Cell cell11 = row.createCell(10);
        cell11.setCellValue("与第一次对比结果");
        //cell1.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);//
        cell11.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                10, //first column (0-based)
                11  //last column  (0-based)
        ));

        Cell cell13 = row.createCell(12);
        cell13.setCellValue("备     注");
        cell13.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cell13.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                3, //last row  (0-based)
                12, //first column (0-based)
                14  //last column  (0-based)
        ));


    }

    private static void createRow4(HSSFWorkbook wb, Sheet sheet){
        Row row = sheet.createRow(3);

        int cellStrart=2;
        for(int i=0;i<5;i++){
            Cell cell3 = row.createCell(cellStrart++);
            cell3.setCellValue("右眼");

            Cell cell4 = row.createCell(cellStrart++);
            cell4.setCellValue("左眼");
        }
    }

    private static void createDataRow(HSSFWorkbook wb, Sheet sheet,int rowindex,String[] line){
        Row row = sheet.createRow(rowindex);
        for(int i=0;i<line.length;i++){
            Cell cell3 = row.createCell(i);
            cell3.setCellValue(line[i]);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                rowindex, //first row (0-based)
                rowindex, //last row  (0-based)
                12, //first column (0-based)
                14  //last column  (0-based)
        ));



    }

//    private static CellStyle createBorderedStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setBorderRight(CellStyle.BORDER_THIN);
//        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderBottom(CellStyle.BORDER_THIN);
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderLeft(CellStyle.BORDER_THIN);
//        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderTop(CellStyle.BORDER_THIN);
//        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        return style;
//    }
}

package com.augwit.carl.demo.utils;

        import org.apache.poi.hssf.usermodel.*;
        import org.apache.poi.ss.usermodel.CellType;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.IOException;
        import java.io.InputStream;


/**
 * @Author: Carl
 * @Description:
 * @Date: Created in 2:03 PM 10/30/2017
 * @Modified By:
 */
public class UploadExcel {

    private HSSFSheet s;

    public UploadExcel(HSSFSheet s) {
        this.s = s;
    }

    public static HSSFSheet getSheet(MultipartFile file){
        HSSFSheet sheet=null;
        try {
            InputStream is= file.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(is);
            sheet =wb.getSheetAt(0);
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    public Object getCellValue(int rowNum,int colNum){

        HSSFRow row=s.getRow(rowNum);
        if(row==null){
            row=s.createRow(rowNum);
        }
        HSSFCell cell=row.getCell(colNum);

        if(cell==null){
            cell=row.createCell(colNum);
            cell.setCellValue("null value");
        }

        Object cellValue = "";
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case BLANK:
                cellValue = "null blank";
                break;
            case _NONE:
                cellValue = "none";
                break;
            case ERROR:
                cellValue="error value";
                break;
            case BOOLEAN:
                cellValue=cell.getBooleanCellValue();
                break;
            case FORMULA:
                cellValue=cell.getCellFormula();
                break;
            default:
                cellValue="未填写";
                break;
        }
        return cellValue;
    }
}

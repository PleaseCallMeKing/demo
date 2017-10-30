package com.augwit.carl.demo.utils;

        import org.apache.poi.hssf.usermodel.HSSFCell;
        import org.apache.poi.hssf.usermodel.HSSFSheet;
        import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

    //  CellReference reference=new CellReference();

    private HSSFSheet s;

    public UploadExcel(HSSFSheet s) {
        this.s = s;
    }

    public static HSSFSheet getSheet(MultipartFile file){
        HSSFSheet sheet=null;
        try {
            InputStream is= file.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(is);
            //wb.getActiveSheetIndex();
            sheet =wb.getSheetAt(0);
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    public HSSFCell getCell(int rowNum,int colNum){
        HSSFCell cell=s.getRow(rowNum).getCell(colNum);
        if(cell != null||cell.equals("")){
            return cell;
        }else {

        }
        return cell;
    }
}

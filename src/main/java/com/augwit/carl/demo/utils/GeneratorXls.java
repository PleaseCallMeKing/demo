package com.augwit.carl.demo.utils;

import com.augwit.carl.demo.domain.City;
import org.apache.poi.hssf.usermodel.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: Carl
 * @Description:
 * @Date: Created in 2:05 PM 10/26/2017
 * @Modified By:
 */

public class GeneratorXls {

    private HSSFWorkbook wb=new HSSFWorkbook();;
    private HSSFSheet sheet=wb.createSheet();
    private HSSFRow row;
    private HSSFCellStyle style=wb.createCellStyle();
    private HSSFFont font=wb.createFont();

    public  void  createXls(City city){

        arrangeRow0();

        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        style.setFont(font);
        row.setRowStyle(style);

        wb.setSheetName(0,"city");


        HSSFCell cityId=row.createCell(0);
        cityId.setCellValue(city.getCityId());
        HSSFCell cityName=row.createCell(1);
        cityName.setCellValue(city.getCityName());
        HSSFCell cityArea=row.createCell(2);
        cityArea.setCellValue(city.getCityArea());
        HSSFCell province=row.createCell(3);
        province.setCellValue(city.getProvince());

        row=sheet.createRow(Math.toIntExact(city.getCityId()));


        if(true){
            try {
                FileOutputStream out=new FileOutputStream("city.xls");
                wb.write(out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                wb.write();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
    void arrangeRow0(){
        row=sheet.createRow(0);
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");
        style.setFont(font);
        row.setRowStyle(style);

        HSSFCell cityId=row.createCell(0);
        cityId.setCellValue("city_id");
        HSSFCell cityName=row.createCell(1);
        cityName.setCellValue("city_name");
        HSSFCell cityArea=row.createCell(2);
        cityArea.setCellValue("city_area");
        HSSFCell province=row.createCell(3);
        province.setCellValue("province");

    }


}

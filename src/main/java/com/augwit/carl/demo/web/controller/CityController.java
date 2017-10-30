package com.augwit.carl.demo.web.controller;

import com.augwit.carl.demo.domain.City;
import com.augwit.carl.demo.persistence.CityRepository;
import com.augwit.carl.demo.utils.GeneratorXls;
import com.augwit.carl.demo.utils.UploadExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Carl
 * @Description:
 * @Date: Created in 10:10 AM 10/26/2017
 * @Modified By:
 */
@Controller
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @RequestMapping("/uploadExcel")
    @ResponseBody
    public String uploadExcel(MultipartFile file){

        HSSFSheet sheet=UploadExcel.getSheet(file);
        UploadExcel uploadExcel=new UploadExcel(sheet);

            int rowNum=sheet.getPhysicalNumberOfRows();

            for(int i=1;i<rowNum;i++) {

                City city=new City();

                Double idDouble=uploadExcel.getCell(i,0).getNumericCellValue();
                city.setCityId(idDouble.longValue());

                city.setCityName(uploadExcel.getCell(i,1).getStringCellValue());

                city.setCityArea(uploadExcel.getCell(i,2).getNumericCellValue());

                city.setProvince(uploadExcel.getCell(i,3).getStringCellValue());

                city.setPostalCode(uploadExcel.getCell(i,4).getStringCellValue());

                cityRepository.save(city);
            }
        return "Success Uploaded !";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create(String name,Double area,String province){
        String cityId="";
        try {
            City city=new City(name,area,province);
            cityRepository.save(city);
            GeneratorXls xls=new GeneratorXls();
            xls.createXls(city);
            cityId=String.valueOf(city.getCityId());

        }catch (Exception ex){
            return "Error creating the city:"+ex.toString();
        }

        return  "City succesfully created with id="+cityId;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Long id){
        try {
            City city=cityRepository.findOne(id);
            cityRepository.delete(city);
        }catch (Exception ex){
            return "Error deleting the city:"+ex.toString();
        }
        return "City succesfully deleted!";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateCity(Long id,String name,Double area,String province){
        try {
            City city=cityRepository.findOne(id);
            city.setCityName(name);
            city.setCityArea(area);
            city.setProvince(province);
            cityRepository.save(city);
        }catch (Exception ex){
            return "Error updating the City:"+ex.toString();
        }
        return "City succesfully updated!";
    }

}

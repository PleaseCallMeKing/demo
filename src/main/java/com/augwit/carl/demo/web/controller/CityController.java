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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
        int cellNum=sheet.getRow(0).getLastCellNum();

        for(int i=1;i<rowNum;i++) {
            City city=new City();
            List<Object> list=new ArrayList<>();
            for(int j=0;j<cellNum;j++){
                list.add(uploadExcel.getCellValue(i,j));
            }
            city.setCityId(((Double) list.get(0)).longValue());
            city.setCityName((String) list.get(1));
            city.setCityArea((Double) list.get(2));
            city.setProvince((String) list.get(3));
            city.setPostalCode((String) list.get(4));
            cityRepository.save(city);
            }
        return "Success Uploaded !";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/downloadExcel")
    public String downloadExcel(City city){

        return "downloadExcel";
    }
    @RequestMapping(value = "/create",params = {"createCity"})
    @ResponseBody
    public String create(@ModelAttribute(value = "city") City city){
        try {
            cityRepository.save(city);
        }catch (Exception ex){
            return "Error creating the city:"+ex.toString();
        }
        return  "City succesfully created ";
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

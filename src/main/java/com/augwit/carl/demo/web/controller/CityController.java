package com.augwit.carl.demo.web.controller;

import com.augwit.carl.demo.domain.City;
import com.augwit.carl.demo.persistence.CityRepository;
import com.augwit.carl.demo.utils.UploadExcel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

        //创建一个HashMap来存放Excel标题的字段
        Map<String,Integer> titles=new HashMap();

        //获得Excel中第一行的标题字段
        for (int j=0;j<cellNum;j++){
            Object cellValue=uploadExcel.getCellValue(0,j);
            titles.put((String) cellValue,j);
        }
        //将数据塞入表中
        for(int i=1;i<rowNum;i++) {
            City city=new City();

            Integer colCityId=titles.get("城市id");
            Object valueCityId=uploadExcel.getCellValue(i,colCityId);
            Double doubleCityId= (Double) valueCityId;
            city.setCityId(doubleCityId.longValue());

            Integer colCityName=titles.get("城市名称");
            Object valueCityName=uploadExcel.getCellValue(i,colCityName);
            city.setCityName((String) valueCityName);

            Integer colCityArea=titles.get("城市面积");
            Object valueCityArea=uploadExcel.getCellValue(i,colCityArea);
            city.setCityArea((Double) valueCityArea);

            Integer colProvince=titles.get("所属省份");
            Object valueProvince=uploadExcel.getCellValue(i,colProvince);
            city.setProvince((String) valueProvince);

            Integer colPostalCode=titles.get("邮编");
            Object valuePostalCode=uploadExcel.getCellValue(i,colPostalCode);
            city.setPostalCode((String) valuePostalCode);

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

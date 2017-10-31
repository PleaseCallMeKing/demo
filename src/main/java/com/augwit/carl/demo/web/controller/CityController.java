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

        //1.把标题值获得 2.把值对应为一个有顺序的数组 3.city中装入对应好顺序的数组。
        //建立一个有序的数组
        ArrayList<String> orderedTitle=new ArrayList<>();
        orderedTitle.add(0,"城市id");
        orderedTitle.add(1,"城市名称");
        orderedTitle.add(2,"城市面积");
        orderedTitle.add(3,"所属省份");
        orderedTitle.add(4,"邮编");
        ArrayList<String> titles=new ArrayList<>();
        for(int z=0;z<cellNum;z++){
            String title= (String) uploadExcel.getCellValue(0,z);
            titles.add(title);
        }
        for(int j=0;j<orderedTitle.size();j++){
            int n=titles.indexOf(orderedTitle.get(j));
            if(n!=j){
                Collections.swap(orderedTitle,j,n);
            }
        }

        //通过Excel标题获得所在列号，然后把列号赋给Map中对应于数据库表的名称的Value，Map(名称,对应列号)
        //在city获得值时根据Map的列号获得值。

        for(int i=1;i<rowNum;i++) {
            City city=new City();
            List<Object> list=new ArrayList<>();
            for(int j=0;j<cellNum;j++){
                list.add(uploadExcel.getCellValue(i,j));
            }
            city.setCityId(((Double) list.get(orderedTitle.indexOf("城市id"))).longValue());
            city.setCityName((String) list.get(orderedTitle.indexOf("城市名称")));
            city.setCityArea((Double) list.get(orderedTitle.indexOf("城市面积")));
            city.setProvince((String) list.get(orderedTitle.indexOf("所属省份")));
            city.setPostalCode((String) list.get(orderedTitle.indexOf("邮编")));
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

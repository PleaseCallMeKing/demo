package com.augwit.carl.demo.web.controller;

import com.augwit.carl.demo.domain.City;
import com.augwit.carl.demo.persistence.CityRepository;
import com.augwit.carl.demo.utils.GeneratorXls;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
    public String uploadExcel(MultipartFile file){
        try {
            InputStream is=file.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(is);
            HSSFSheet sheet =wb.getSheetAt(0);
            int rowNum=sheet.getPhysicalNumberOfRows();

            ArrayList<Long> ids=new ArrayList<>();
            ArrayList<String> names=new ArrayList<>();
            ArrayList<Double> areas=new ArrayList<>();
            ArrayList<String> provinces=new ArrayList<>();
            for(int i=0;i<rowNum;i++) {
                City city=new City();

                System.out.println( sheet.getRow(i).getCell(0).getNumericCellValue());

                Double idDouble=sheet.getRow(i).getCell(0).getNumericCellValue();
                Long idLong=idDouble.longValue();
               //ids.set( i,idLong);
                city.setCityId(idLong);

               // names.set(i,sheet.getRow(i).getCell(1).getStringCellValue()) ;
                city.setCityName(sheet.getRow(i).getCell(1).getStringCellValue());

             //   areas.set(i,  sheet.getRow(i).getCell(2).getNumericCellValue()) ;
                city.setCityArea(sheet.getRow(i).getCell(2).getNumericCellValue());

             //   provinces.set(i,sheet.getRow(i).getCell(3).getStringCellValue()) ;
                city.setProvince(sheet.getRow(i).getCell(3).getStringCellValue());

                cityRepository.save(city);
            }
            //System.out.println(ids.get(0));
            for (int j=0;j<ids.size();j++){
                City city=new City();
                //city.setCityId(ids.get(j));
                city.setCityName(names.get(j));
                city.setCityArea(areas.get(j));
                city.setProvince(provinces.get(j));
                cityRepository.save(city);
            }


           /* Map<Integer, String> map = new HashMap();
            for (int i = 0; i < rowNum; i++) {
                Row row=sheet.getRow(i);
                int colNum=row.getPhysicalNumberOfCells();
                for (int j = 0; j < colNum; j++) {
                    Cell cell=row.getCell(j);
                    map.put(j,cell.getStringCellValue());
                }
                city=new City();
                city.setCityId(Long.valueOf(map.get(0)));
                city.setCityName(map.get(1));
                city.setCityArea(Float.valueOf(map.get(2)));
                city.setProvince(map.get(3));
                cityRepository.save(city);
            }
            System.out.println(map.toString());*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "greeting";
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

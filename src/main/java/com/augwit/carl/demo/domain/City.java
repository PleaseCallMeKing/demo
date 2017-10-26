package com.augwit.carl.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @Author: Carl
 * @Description:
 * @Date: Created in 6:57 PM 10/25/2017
 * @Modified By:
 */
@Entity
@Table(name="city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id")
    private Long cityId;

    @NotNull
    @Column(name = "city_name")
    private String cityName;

    @NotNull
    @Column(name = "city_area")
    private Float cityArea;

    @NotNull
    @Column(name = "city_province")
    private String province;

    public City() {
    }


    public City(String cityName) {
        this.cityName = cityName;
    }

    public City(String cityName, Float cityArea) {
        this.cityName = cityName;
        this.cityArea = cityArea;
    }

    public City(String cityName, Float cityArea, String province) {
        this.cityName = cityName;
        this.cityArea = cityArea;
        this.province = province;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Float getCityArea() {
        return cityArea;
    }

    public void setCityArea(Float cityArea) {
        this.cityArea = cityArea;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (cityId != null ? !cityId.equals(city.cityId) : city.cityId != null) return false;
        if (cityName != null ? !cityName.equals(city.cityName) : city.cityName != null) return false;
        if (cityArea != null ? !cityArea.equals(city.cityArea) : city.cityArea != null) return false;
        return province != null ? province.equals(city.province) : city.province == null;
    }

    @Override
    public int hashCode() {
        int result = cityId != null ? cityId.hashCode() : 0;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (cityArea != null ? cityArea.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", cityArea=" + cityArea +
                ", province='" + province + '\'' +
                '}';
    }
}

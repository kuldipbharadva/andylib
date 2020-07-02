package com.example.libusage.pagination.pg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

@SerializedName("city_data")
@Expose
private List<CityDatum> cityData = null;
@SerializedName("total_page")
@Expose
private Integer totalPage;

public List<CityDatum> getCityData() {
return cityData;
}

public void setCityData(List<CityDatum> cityData) {
this.cityData = cityData;
}

public Integer getTotalPage() {
return totalPage;
}

public void setTotalPage(Integer totalPage) {
this.totalPage = totalPage;
}

}
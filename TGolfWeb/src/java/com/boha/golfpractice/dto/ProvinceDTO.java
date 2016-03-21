/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.dto;

import com.boha.golfpractice.data.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aubreymalabie
 */
public class ProvinceDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer provinceID;
    private String provinceName;
    private Double latitude;
    private Double longitude;
    private String webKey;
    private Integer countryID;
    private List<GolfCourseDTO> golfCourseList;

    public ProvinceDTO() {
    }

    public ProvinceDTO(Province a) {
        this.provinceID = a.getProvinceID();
        provinceName = a.getProvinceName();
        latitude = a.getLatitude();
        longitude = a.getLongitude();
        webKey = a.getWebKey();
        countryID = a.getCountry().getCountryID();
                
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public List<GolfCourseDTO> getGolfCourseList() {
        return golfCourseList;
    }

    public void setGolfCourseList(List<GolfCourseDTO> golfCourseList) {
        this.golfCourseList = golfCourseList;
    }

    public Integer getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provinceID != null ? provinceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvinceDTO)) {
            return false;
        }
        ProvinceDTO other = (ProvinceDTO) object;
        if ((this.provinceID == null && other.provinceID != null) || (this.provinceID != null && !this.provinceID.equals(other.provinceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.Province[ provinceID=" + provinceID + " ]";
    }
    
}

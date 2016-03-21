/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.dto;

import com.boha.golfpractice.data.GolfCourse;
import com.boha.golfpractice.data.Hole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
public class GolfCourseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer golfCourseID, provinceID;
    private String golfCourseName;
    private Double latitude;
    private Double longitude;
    private String email;
    private String cellphone;
    private List<HoleDTO> holeList;
    private List<PracticeSessionDTO> practiceSessionList;

    public GolfCourseDTO() {
    }

    public GolfCourseDTO(GolfCourse a) {
        this.golfCourseID = a.getGolfCourseID();
        golfCourseName = a.getGolfCourseName();
        latitude = a.getLatitude();
        longitude = a.getLongitude();
        email = a.getEmail();
        cellphone = a.getCellphone();
        if (a.getProvince() != null) {
            provinceID = a.getProvince().getProvinceID();
        }
        if (a.getHoleList() != null) {
            holeList = new ArrayList<>();
            for (Hole hole : a.getHoleList()) {
                holeList.add(new HoleDTO(hole));
            }
        }
    }

    public Integer getGolfCourseID() {
        return golfCourseID;
    }

    public void setGolfCourseID(Integer golfCourseID) {
        this.golfCourseID = golfCourseID;
    }

    public String getGolfCourseName() {
        return golfCourseName;
    }

    public Integer getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    public void setGolfCourseName(String golfCourseName) {
        this.golfCourseName = golfCourseName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @XmlTransient
    public List<HoleDTO> getHoleList() {
        if (holeList == null) {
            holeList = new ArrayList<>();
        }
        return holeList;
    }

    public void setHoleList(List<HoleDTO> holeList) {
        this.holeList = holeList;
    }

    @XmlTransient
    public List<PracticeSessionDTO> getPracticeSessionList() {
        if (practiceSessionList == null) {
            practiceSessionList = new ArrayList<>();
        }
        return practiceSessionList;
    }

    public void setPracticeSessionList(List<PracticeSessionDTO> practiceSessionList) {
        this.practiceSessionList = practiceSessionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (golfCourseID != null ? golfCourseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GolfCourseDTO)) {
            return false;
        }
        GolfCourseDTO other = (GolfCourseDTO) object;
        if ((this.golfCourseID == null && other.golfCourseID != null) || (this.golfCourseID != null && !this.golfCourseID.equals(other.golfCourseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.GolfCourse[ golfCourseID=" + golfCourseID + " ]";
    }
    
}

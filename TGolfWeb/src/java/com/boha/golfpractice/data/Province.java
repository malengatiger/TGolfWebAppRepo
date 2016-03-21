/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "province")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT p FROM Province p"),
    @NamedQuery(name = "Province.findByProvinceID", query = "SELECT p FROM Province p WHERE p.provinceID = :provinceID"),
    @NamedQuery(name = "Province.findByProvinceName", query = "SELECT p FROM Province p WHERE p.provinceName = :provinceName"),
    @NamedQuery(name = "Province.findByLatitude", query = "SELECT p FROM Province p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "Province.findByLongitude", query = "SELECT p FROM Province p WHERE p.longitude = :longitude"),
    @NamedQuery(name = "Province.findByWebKey", query = "SELECT p FROM Province p WHERE p.webKey = :webKey")})
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "provinceID")
    private Integer provinceID;
    @Size(max = 100)
    @Column(name = "provinceName")
    private String provinceName;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Size(max = 100)
    @Column(name = "webKey")
    private String webKey;
    @JoinColumn(name = "countryID", referencedColumnName = "countryID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;
    @OneToMany(mappedBy = "province", fetch = FetchType.EAGER)
    private List<GolfCourse> golfCourseList;

    public Province() {
    }

    public Province(Integer provinceID) {
        this.provinceID = provinceID;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

  
    @XmlTransient
    public List<GolfCourse> getGolfCourseList() {
        return golfCourseList;
    }

    public void setGolfCourseList(List<GolfCourse> golfCourseList) {
        this.golfCourseList = golfCourseList;
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
        if (!(object instanceof Province)) {
            return false;
        }
        Province other = (Province) object;
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

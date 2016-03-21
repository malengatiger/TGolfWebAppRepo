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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findByCountryID", query = "SELECT c FROM Country c WHERE c.countryID = :countryID"),
    @NamedQuery(name = "Country.findByCountryName", query = "SELECT c FROM Country c WHERE c.countryName = :countryName"),
    @NamedQuery(name = "Country.findByLatitude", query = "SELECT c FROM Country c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Country.findByLongitude", query = "SELECT c FROM Country c WHERE c.longitude = :longitude"),
    @NamedQuery(name = "Country.findByCountryCode", query = "SELECT c FROM Country c WHERE c.countryCode = :countryCode"),
    @NamedQuery(name = "Country.findByWebKey", query = "SELECT c FROM Country c WHERE c.webKey = :webKey")})
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "countryID")
    private Integer countryID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "countryName")
    private String countryName;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "countryCode")
    private String countryCode;
    @Size(max = 100)
    @Column(name = "webKey")
    private String webKey;
    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Province> provinceList;

    public Country() {
    }

    public Country(Integer countryID) {
        this.countryID = countryID;
    }

    public Country(Integer countryID, String countryName, String countryCode) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    @XmlTransient
    public List<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryID != null ? countryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryID == null && other.countryID != null) || (this.countryID != null && !this.countryID.equals(other.countryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.Country[ countryID=" + countryID + " ]";
    }
    
}

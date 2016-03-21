/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "golfCourse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GolfCourse.findAll", query = "SELECT g FROM GolfCourse g"),
    @NamedQuery(name = "GolfCourse.findByGolfCourseID", query = "SELECT g FROM GolfCourse g WHERE g.golfCourseID = :golfCourseID"),
    @NamedQuery(name = "GolfCourse.findByGolfCourseName", query = "SELECT g FROM GolfCourse g WHERE g.golfCourseName = :golfCourseName"),
    @NamedQuery(name = "GolfCourse.findByLatitude", query = "SELECT g FROM GolfCourse g WHERE g.latitude = :latitude"),
    @NamedQuery(name = "GolfCourse.findByLongitude", query = "SELECT g FROM GolfCourse g WHERE g.longitude = :longitude"),
    @NamedQuery(name = "GolfCourse.findByEmail", query = "SELECT g FROM GolfCourse g WHERE g.email = :email"),
    @NamedQuery(name = "GolfCourse.findByCellphone", query = "SELECT g FROM GolfCourse g WHERE g.cellphone = :cellphone")})
public class GolfCourse implements Serializable {

    @JoinColumn(name = "provinceID", referencedColumnName = "provinceID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Province province;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "golfCourseID")
    private Integer golfCourseID;
    @Size(max = 200)
    @Column(name = "golfCourseName")
    private String golfCourseName;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Size(max = 200)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "cellphone")
    private String cellphone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfCourse", fetch = FetchType.EAGER)
    private List<Hole> holeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfCourse", fetch = FetchType.EAGER)
    private List<PracticeSession> practiceSessionList;

    public GolfCourse() {
    }

    public GolfCourse(Integer golfCourseID) {
        this.golfCourseID = golfCourseID;
    }

    public Integer getGolfCourseID() {
        return golfCourseID;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setGolfCourseID(Integer golfCourseID) {
        this.golfCourseID = golfCourseID;
    }

    public String getGolfCourseName() {
        return golfCourseName;
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
    public List<Hole> getHoleList() {
        return holeList;
    }

    public void setHoleList(List<Hole> holeList) {
        this.holeList = holeList;
    }

    @XmlTransient
    public List<PracticeSession> getPracticeSessionList() {
        return practiceSessionList;
    }

    public void setPracticeSessionList(List<PracticeSession> practiceSessionList) {
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
        if (!(object instanceof GolfCourse)) {
            return false;
        }
        GolfCourse other = (GolfCourse) object;
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

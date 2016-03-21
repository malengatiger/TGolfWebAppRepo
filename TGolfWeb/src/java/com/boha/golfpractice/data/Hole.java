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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "hole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hole.findByCourse", 
            query = "SELECT h FROM Hole h where h.golfCourse.golfCourseID = :golfCourseID order by h.holeNumber"),
    @NamedQuery(name = "Hole.findByHoleID", query = "SELECT h FROM Hole h WHERE h.holeID = :holeID"),
    @NamedQuery(name = "Hole.findByHoleNumber", query = "SELECT h FROM Hole h WHERE h.holeNumber = :holeNumber"),
    @NamedQuery(name = "Hole.findByLengthFromRed", query = "SELECT h FROM Hole h WHERE h.lengthFromRed = :lengthFromRed"),
    @NamedQuery(name = "Hole.findByLengthFromBlue", query = "SELECT h FROM Hole h WHERE h.lengthFromBlue = :lengthFromBlue"),
    @NamedQuery(name = "Hole.findByLengthFromWhite", query = "SELECT h FROM Hole h WHERE h.lengthFromWhite = :lengthFromWhite"),
    @NamedQuery(name = "Hole.findByLengthFromBlack", query = "SELECT h FROM Hole h WHERE h.lengthFromBlack = :lengthFromBlack"),
    @NamedQuery(name = "Hole.findByPar", query = "SELECT h FROM Hole h WHERE h.par = :par")})
public class Hole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "holeID")
    private Integer holeID;
    @Column(name = "holeNumber")
    private Integer holeNumber;
    @Column(name = "lengthFromRed")
    private Integer lengthFromRed;
    @Column(name = "lengthFromBlue")
    private Integer lengthFromBlue;
    @Column(name = "lengthFromWhite")
    private Integer lengthFromWhite;
    @Column(name = "lengthFromBlack")
    private Integer lengthFromBlack;
    @Column(name = "par")
    private Integer par;
    @JoinColumn(name = "golfCourseID", referencedColumnName = "golfCourseID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private GolfCourse golfCourse;
    @OneToMany(mappedBy = "hole", fetch = FetchType.EAGER)
    private List<HoleStat> holeStatList;

    public Hole() {
    }

    public Hole(Integer holeID) {
        this.holeID = holeID;
    }

    public Integer getHoleID() {
        return holeID;
    }

    public void setHoleID(Integer holeID) {
        this.holeID = holeID;
    }

    public Integer getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(Integer holeNumber) {
        this.holeNumber = holeNumber;
    }

    public Integer getLengthFromRed() {
        return lengthFromRed;
    }

    public void setLengthFromRed(Integer lengthFromRed) {
        this.lengthFromRed = lengthFromRed;
    }

    public Integer getLengthFromBlue() {
        return lengthFromBlue;
    }

    public void setLengthFromBlue(Integer lengthFromBlue) {
        this.lengthFromBlue = lengthFromBlue;
    }

    public Integer getLengthFromWhite() {
        return lengthFromWhite;
    }

    public void setLengthFromWhite(Integer lengthFromWhite) {
        this.lengthFromWhite = lengthFromWhite;
    }

    public Integer getLengthFromBlack() {
        return lengthFromBlack;
    }

    public void setLengthFromBlack(Integer lengthFromBlack) {
        this.lengthFromBlack = lengthFromBlack;
    }

    public Integer getPar() {
        return par;
    }

    public void setPar(Integer par) {
        this.par = par;
    }

    public GolfCourse getGolfCourse() {
        return golfCourse;
    }

    public void setGolfCourse(GolfCourse golfCourse) {
        this.golfCourse = golfCourse;
    }

 

    @XmlTransient
    public List<HoleStat> getHoleStatList() {
        return holeStatList;
    }

    public void setHoleStatList(List<HoleStat> holeStatList) {
        this.holeStatList = holeStatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (holeID != null ? holeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hole)) {
            return false;
        }
        Hole other = (Hole) object;
        if ((this.holeID == null && other.holeID != null) || (this.holeID != null && !this.holeID.equals(other.holeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.Hole[ holeID=" + holeID + " ]";
    }
    
}

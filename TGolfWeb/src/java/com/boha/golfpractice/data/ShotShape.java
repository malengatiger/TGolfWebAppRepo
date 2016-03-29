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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "shotShape")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShotShape.findAll", query = "SELECT s FROM ShotShape s order by s.shape"),
    @NamedQuery(name = "ShotShape.findByShotShapeID", query = "SELECT s FROM ShotShape s WHERE s.shotShapeID = :shotShapeID"),
    @NamedQuery(name = "ShotShape.findByShape", query = "SELECT s FROM ShotShape s WHERE s.shape = :shape")})
public class ShotShape implements Serializable {

    @OneToMany(mappedBy = "shotShape", fetch = FetchType.EAGER)
    private List<ClubUsed> clubUsedList;

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shotShapeID")
    private Integer shotShapeID;
    @Size(max = 100)
    @Column(name = "shape")
    private String shape;
    

    public ShotShape() {
    }

    public ShotShape(Integer shotShapeID) {
        this.shotShapeID = shotShapeID;
    }

    public Integer getShotShapeID() {
        return shotShapeID;
    }

    public void setShotShapeID(Integer shotShapeID) {
        this.shotShapeID = shotShapeID;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shotShapeID != null ? shotShapeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShotShape)) {
            return false;
        }
        ShotShape other = (ShotShape) object;
        if ((this.shotShapeID == null && other.shotShapeID != null) || (this.shotShapeID != null && !this.shotShapeID.equals(other.shotShapeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.ShotShape[ shotShapeID=" + shotShapeID + " ]";
    }

    @XmlTransient
    public List<ClubUsed> getClubUsedList() {
        return clubUsedList;
    }

    public void setClubUsedList(List<ClubUsed> clubUsedList) {
        this.clubUsedList = clubUsedList;
    }

    
}

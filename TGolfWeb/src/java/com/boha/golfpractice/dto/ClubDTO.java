/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.dto;

import com.boha.golfpractice.data.Club;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
public class ClubDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer clubID;
    private String clubName;
    private Integer sequence;
    private List<ClubUsedDTO> clubUsedList;

    public ClubDTO() {
    }

    public ClubDTO(Club a) {
        this.clubID = a.getClubID();
        this.clubName = a.getClubName();
        this.sequence = a.getSequence();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getClubID() {
        return clubID;
    }

    public void setClubID(Integer clubID) {
        this.clubID = clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @XmlTransient
    public List<ClubUsedDTO> getClubUsedList() {
        return clubUsedList;
    }

    public void setClubUsedList(List<ClubUsedDTO> clubUsedList) {
        this.clubUsedList = clubUsedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clubID != null ? clubID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClubDTO)) {
            return false;
        }
        ClubDTO other = (ClubDTO) object;
        if ((this.clubID == null && other.clubID != null) || (this.clubID != null && !this.clubID.equals(other.clubID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.Club[ clubID=" + clubID + " ]";
    }
    
}

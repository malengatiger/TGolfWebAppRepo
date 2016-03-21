/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.dto;

import com.boha.golfpractice.data.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aubreymalabie
 */
public class HoleStatDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer holeStatID;
    private Boolean fairwayHit;
    private Boolean fairwayBunkerHit;
    private Integer distanceToPin;
    private Boolean greenInRegulation;
    private Integer numberOfPutts;
    private Boolean greensideBunkerHit;
    private Integer score;
    private String remarks;
    private Boolean inRough;
    private Boolean inWater;
    private Boolean outOfBounds;
    private Integer practiceSessionID, mistakes;
    private HoleDTO hole;
    private Double lengthOfPutt;
    private List<ClubUsedDTO> clubUsedList;

    public HoleStatDTO() {
    }

    public HoleStatDTO(HoleStat a) {
        this.holeStatID = a.getHoleStatID();
        fairwayHit = a.getFairwayHit();
        fairwayBunkerHit = a.getFairwayBunkerHit();
        distanceToPin = a.getDistanceToPin();
        greenInRegulation = a.getGreenInRegulation();
        numberOfPutts = a.getNumberOfPutts();
        greensideBunkerHit = a.getGreensideBunkerHit();
        greenInRegulation = a.getGreenInRegulation();
        lengthOfPutt = a.getLengthOfPutt();
        score = a.getScore();
        remarks = a.getRemarks();
        inRough = a.getInRough();
        inWater = a.getInWater();
        outOfBounds = a.getOutOfBounds();
        mistakes = a.getMistakes();
        if (a.getPracticeSession() != null) {
            practiceSessionID = a.getPracticeSession().getPracticeSessionID();
        }
        if (a.getHole() != null) {
            hole = new HoleDTO(a.getHole());
        }
    }

    public Integer getMistakes() {
        return mistakes;
    }

    public void setMistakes(Integer mistakes) {
        this.mistakes = mistakes;
    }

    public HoleDTO getHole() {
        return hole;
    }

    public void setHole(HoleDTO hole) {
        this.hole = hole;
    }

    public Double getLengthOfPutt() {
        return lengthOfPutt;
    }

    public void setLengthOfPutt(Double lengthOfPutt) {
        this.lengthOfPutt = lengthOfPutt;
    }

    
    public Integer getHoleStatID() {
        return holeStatID;
    }

    public void setHoleStatID(Integer holeStatID) {
        this.holeStatID = holeStatID;
    }

    public Boolean getFairwayHit() {
        return fairwayHit;
    }

    public void setFairwayHit(Boolean fairwayHit) {
        this.fairwayHit = fairwayHit;
    }

    public Boolean getFairwayBunkerHit() {
        return fairwayBunkerHit;
    }

    public void setFairwayBunkerHit(Boolean fairwayBunkerHit) {
        this.fairwayBunkerHit = fairwayBunkerHit;
    }

    public Integer getDistanceToPin() {
        return distanceToPin;
    }

    public void setDistanceToPin(Integer distanceToPin) {
        this.distanceToPin = distanceToPin;
    }

    public Boolean getGreenInRegulation() {
        return greenInRegulation;
    }

    public void setGreenInRegulation(Boolean greenInRegulation) {
        this.greenInRegulation = greenInRegulation;
    }

    public Integer getNumberOfPutts() {
        return numberOfPutts;
    }

    public void setNumberOfPutts(Integer numberOfPutts) {
        this.numberOfPutts = numberOfPutts;
    }

    public Boolean getGreensideBunkerHit() {
        return greensideBunkerHit;
    }

    public void setGreensideBunkerHit(Boolean greensideBunkerHit) {
        this.greensideBunkerHit = greensideBunkerHit;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getInRough() {
        return inRough;
    }

    public void setInRough(Boolean inRough) {
        this.inRough = inRough;
    }

    public Boolean getInWater() {
        return inWater;
    }

    public void setInWater(Boolean inWater) {
        this.inWater = inWater;
    }

    public Boolean getOutOfBounds() {
        return outOfBounds;
    }

    public void setOutOfBounds(Boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public Integer getPracticeSessionID() {
        return practiceSessionID;
    }

    public void setPracticeSessionID(Integer practiceSessionID) {
        this.practiceSessionID = practiceSessionID;
    }

    public List<ClubUsedDTO> getClubUsedList() {
        if (clubUsedList == null) {
            clubUsedList = new ArrayList<>();
        }
        return clubUsedList;
    }

    public void setClubUsedList(List<ClubUsedDTO> clubUsedList) {
        this.clubUsedList = clubUsedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (holeStatID != null ? holeStatID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoleStatDTO)) {
            return false;
        }
        HoleStatDTO other = (HoleStatDTO) object;
        if ((this.holeStatID == null && other.holeStatID != null) || (this.holeStatID != null && !this.holeStatID.equals(other.holeStatID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.HoleStat[ holeStatID=" + holeStatID + " ]";
    }
    
}

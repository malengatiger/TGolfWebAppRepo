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
@Table(name = "holeStat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HoleStat.findByPlayer", 
            query = "SELECT h FROM HoleStat h where h.practiceSession.player.playerID = :playerID order by h.practiceSession.practiceSessionID"),
    
})
public class HoleStat implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lengthOfPutt")
    private Double lengthOfPutt;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "holeStatID")
    private Integer holeStatID;
    @Column(name = "fairwayHit")
    private Boolean fairwayHit;
    @Column(name = "fairwayBunkerHit")
    private Boolean fairwayBunkerHit;
    @Column(name = "distanceToPin")
    private Integer distanceToPin;
    @Column(name = "greenInRegulation")
    private Boolean greenInRegulation;
    @Column(name = "numberOfPutts")
    private Integer numberOfPutts;
    @Column(name = "greensideBunkerHit")
    private Boolean greensideBunkerHit;
    @Column(name = "score")
    private Integer score;
    @Size(max = 512)
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "inRough")
    private Boolean inRough;
    @Column(name = "inWater")
    private Boolean inWater;
    @Column(name = "outOfBounds")
    private Boolean outOfBounds;
    @Column(name = "mistakes")
    private Integer mistakes;
    @JoinColumn(name = "practiceSessionID", referencedColumnName = "practiceSessionID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PracticeSession practiceSession;
    @JoinColumn(name = "holeID", referencedColumnName = "holeID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Hole hole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "holeStat", fetch = FetchType.EAGER)
    private List<ClubUsed> clubUsedList;

    public HoleStat() {
    }

    public HoleStat(Integer holeStatID) {
        this.holeStatID = holeStatID;
    }

    public Integer getHoleStatID() {
        return holeStatID;
    }

    public void setHoleStatID(Integer holeStatID) {
        this.holeStatID = holeStatID;
    }

    public Integer getMistakes() {
        return mistakes;
    }

    public void setMistakes(Integer mistakes) {
        this.mistakes = mistakes;
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

    public PracticeSession getPracticeSession() {
        return practiceSession;
    }

    public void setPracticeSession(PracticeSession practiceSession) {
        this.practiceSession = practiceSession;
    }

    public Hole getHole() {
        return hole;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }

   

    @XmlTransient
    public List<ClubUsed> getClubUsedList() {
        return clubUsedList;
    }

    public void setClubUsedList(List<ClubUsed> clubUsedList) {
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
        if (!(object instanceof HoleStat)) {
            return false;
        }
        HoleStat other = (HoleStat) object;
        if ((this.holeStatID == null && other.holeStatID != null) || (this.holeStatID != null && !this.holeStatID.equals(other.holeStatID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.HoleStat[ holeStatID=" + holeStatID + " ]";
    }

    public Double getLengthOfPutt() {
        return lengthOfPutt;
    }

    public void setLengthOfPutt(Double lengthOfPutt) {
        this.lengthOfPutt = lengthOfPutt;
    }
    
}

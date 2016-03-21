/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.data;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "practiceSession")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PracticeSession.findByPlayer", 
            query = "SELECT p FROM PracticeSession p where p.player.playerID = :playerID order by p.sessionDate desc"),
    
    @NamedQuery(name = "PracticeSession.findOpenByPlayer", 
            query = "SELECT p FROM PracticeSession p where p.player.playerID = :playerID and p.closed = FALSE"),
    
    @NamedQuery(name = "PracticeSession.getPlayerSessionsInPeriod", 
            query = "SELECT p FROM PracticeSession p where p.player.playerID = :playerID and p.closed = TRUE and p.sessionDate BETWEEN :fromDate and :toDate"),
    
    
})
public class PracticeSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "practiceSessionID")
    private Integer practiceSessionID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sessionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionDate;
    @Column(name = "numberOfHoles")
    private Integer numberOfHoles;
    @Column(name = "totalStrokes")
    private Integer totalStrokes;
    @Column(name = "underPar")
    private Integer underPar;
    @Column(name = "overPar")
    private Integer overPar;
    @Column(name = "par")
    private Integer par;
    @Column(name = "closed")
    private Boolean closed;
    @Column(name = "totalMistakes")
    private Integer totalMistakes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "practiceSession", fetch = FetchType.EAGER)
    private List<HoleStat> holeStatList;
    @OneToMany(mappedBy = "practiceSession", fetch = FetchType.EAGER)
    private List<VideoUpload> videoUploadList;
    @JoinColumn(name = "playerID", referencedColumnName = "playerID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Player player;
    @JoinColumn(name = "golfCourseID", referencedColumnName = "golfCourseID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private GolfCourse golfCourse;

    public PracticeSession() {
    }

    public PracticeSession(Integer practiceSessionID) {
        this.practiceSessionID = practiceSessionID;
    }

    public PracticeSession(Integer practiceSessionID, Date sessionDate) {
        this.practiceSessionID = practiceSessionID;
        this.sessionDate = sessionDate;
    }

    public Integer getPracticeSessionID() {
        return practiceSessionID;
    }

    public void setPracticeSessionID(Integer practiceSessionID) {
        this.practiceSessionID = practiceSessionID;
    }

    public Integer getTotalMistakes() {
        return totalMistakes;
    }

    public void setTotalMistakes(Integer totalMistakes) {
        this.totalMistakes = totalMistakes;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Integer getNumberOfHoles() {
        return numberOfHoles;
    }

    public void setNumberOfHoles(Integer numberOfHoles) {
        this.numberOfHoles = numberOfHoles;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Integer getTotalStrokes() {
        return totalStrokes;
    }

    public void setTotalStrokes(Integer totalStrokes) {
        this.totalStrokes = totalStrokes;
    }

    public Integer getUnderPar() {
        return underPar;
    }

    public void setUnderPar(Integer underPar) {
        this.underPar = underPar;
    }

    public Integer getOverPar() {
        return overPar;
    }

    public void setOverPar(Integer overPar) {
        this.overPar = overPar;
    }

    public Integer getPar() {
        return par;
    }

    public void setPar(Integer par) {
        this.par = par;
    }

    @XmlTransient
    public List<HoleStat> getHoleStatList() {
        return holeStatList;
    }

    public void setHoleStatList(List<HoleStat> holeStatList) {
        this.holeStatList = holeStatList;
    }

    @XmlTransient
    public List<VideoUpload> getVideoUploadList() {
        return videoUploadList;
    }

    public void setVideoUploadList(List<VideoUpload> videoUploadList) {
        this.videoUploadList = videoUploadList;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GolfCourse getGolfCourse() {
        return golfCourse;
    }

    public void setGolfCourse(GolfCourse golfCourse) {
        this.golfCourse = golfCourse;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (practiceSessionID != null ? practiceSessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PracticeSession)) {
            return false;
        }
        PracticeSession other = (PracticeSession) object;
        if ((this.practiceSessionID == null && other.practiceSessionID != null) || (this.practiceSessionID != null && !this.practiceSessionID.equals(other.practiceSessionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.PracticeSession[ practiceSessionID=" + practiceSessionID + " ]";
    }
    
}

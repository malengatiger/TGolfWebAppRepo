/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.dto;

import com.boha.golfpractice.data.PracticeSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
public class PracticeSessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer practiceSessionID;
    private Long sessionDate;
    private Integer numberOfHoles;
    private Integer totalStrokes;
    private Integer underPar;
    private Integer overPar;
    private Integer par;
    private Boolean closed;
    private String golfCourseName;
    private List<HoleStatDTO> holeStatList;
    private List<VideoUploadDTO> videoUploadList;
    private Integer playerID;
    private Integer golfCourseID;
    private Integer totalMistakes;
    private GolfCourseDTO golfCourse;

    public PracticeSessionDTO() {
    }

    public PracticeSessionDTO(Integer practiceSessionID) {
        this.practiceSessionID = practiceSessionID;
    }

    public PracticeSessionDTO(PracticeSession a) {
        this.practiceSessionID = a.getPracticeSessionID();
        this.sessionDate = a.getSessionDate().getTime();
        numberOfHoles = a.getNumberOfHoles();
        totalStrokes = a.getTotalStrokes();
        underPar = a.getUnderPar();
        overPar = a.getOverPar();
        closed = a.getClosed();
        par = a.getPar();
        totalMistakes = a.getTotalMistakes();
        
        if (a.getPlayer() != null) {
            playerID = a.getPlayer().getPlayerID();
        }
        if (a.getGolfCourse() != null) {
            golfCourseName = a.getGolfCourse().getGolfCourseName();
            golfCourseID = a.getGolfCourse().getGolfCourseID();
            golfCourse = new GolfCourseDTO(a.getGolfCourse());
        }
    }

    public Integer getTotalMistakes() {
        return totalMistakes;
    }

    public void setTotalMistakes(Integer totalMistakes) {
        this.totalMistakes = totalMistakes;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public GolfCourseDTO getGolfCourse() {
        return golfCourse;
    }

    public void setGolfCourse(GolfCourseDTO golfCourse) {
        this.golfCourse = golfCourse;
    }

    public String getGolfCourseName() {
        return golfCourseName;
    }

    public void setGolfCourseName(String golfCourseName) {
        this.golfCourseName = golfCourseName;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public Integer getGolfCourseID() {
        return golfCourseID;
    }

    public void setGolfCourseID(Integer golfCourseID) {
        this.golfCourseID = golfCourseID;
    }

    public Integer getPracticeSessionID() {
        return practiceSessionID;
    }

    public void setPracticeSessionID(Integer practiceSessionID) {
        this.practiceSessionID = practiceSessionID;
    }

    public Long getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Long sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Integer getNumberOfHoles() {
        return numberOfHoles;
    }

    public void setNumberOfHoles(Integer numberOfHoles) {
        this.numberOfHoles = numberOfHoles;
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
    public List<HoleStatDTO> getHoleStatList() {
        if (holeStatList == null) {
            holeStatList = new ArrayList<>();
        }
        return holeStatList;
    }

    public void setHoleStatList(List<HoleStatDTO> holeStatList) {
        this.holeStatList = holeStatList;
    }

    @XmlTransient
    public List<VideoUploadDTO> getVideoUploadList() {
        if (videoUploadList == null) {
            videoUploadList = new ArrayList<>();
        }
        return videoUploadList;
    }

    public void setVideoUploadList(List<VideoUploadDTO> videoUploadList) {
        this.videoUploadList = videoUploadList;
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
        if (!(object instanceof PracticeSessionDTO)) {
            return false;
        }
        PracticeSessionDTO other = (PracticeSessionDTO) object;
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

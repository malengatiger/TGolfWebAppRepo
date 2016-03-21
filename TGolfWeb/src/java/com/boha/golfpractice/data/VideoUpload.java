/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.data;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "videoUpload")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VideoUpload.findAll", query = "SELECT v FROM VideoUpload v"),
    @NamedQuery(name = "VideoUpload.findByVideoUploadID", query = "SELECT v FROM VideoUpload v WHERE v.videoUploadID = :videoUploadID"),
    @NamedQuery(name = "VideoUpload.findByYouTubeID", query = "SELECT v FROM VideoUpload v WHERE v.youTubeID = :youTubeID"),
    @NamedQuery(name = "VideoUpload.findByDateTaken", query = "SELECT v FROM VideoUpload v WHERE v.dateTaken = :dateTaken"),
    @NamedQuery(name = "VideoUpload.findByUrl", query = "SELECT v FROM VideoUpload v WHERE v.url = :url")})
public class VideoUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "videoUploadID")
    private Integer videoUploadID;
    @Size(max = 105)
    @Column(name = "youTubeID")
    private String youTubeID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateTaken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTaken;
    @Size(max = 200)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "playerID", referencedColumnName = "playerID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Player player;
    @JoinColumn(name = "practiceSessionID", referencedColumnName = "practiceSessionID")
    @ManyToOne(fetch = FetchType.EAGER)
    private PracticeSession practiceSession;

    public VideoUpload() {
    }

    public VideoUpload(Integer videoUploadID) {
        this.videoUploadID = videoUploadID;
    }

    public VideoUpload(Integer videoUploadID, Date dateTaken) {
        this.videoUploadID = videoUploadID;
        this.dateTaken = dateTaken;
    }

    public Integer getVideoUploadID() {
        return videoUploadID;
    }

    public void setVideoUploadID(Integer videoUploadID) {
        this.videoUploadID = videoUploadID;
    }

    public String getYouTubeID() {
        return youTubeID;
    }

    public void setYouTubeID(String youTubeID) {
        this.youTubeID = youTubeID;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PracticeSession getPracticeSession() {
        return practiceSession;
    }

    public void setPracticeSession(PracticeSession practiceSession) {
        this.practiceSession = practiceSession;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (videoUploadID != null ? videoUploadID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideoUpload)) {
            return false;
        }
        VideoUpload other = (VideoUpload) object;
        if ((this.videoUploadID == null && other.videoUploadID != null) || (this.videoUploadID != null && !this.videoUploadID.equals(other.videoUploadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.VideoUpload[ videoUploadID=" + videoUploadID + " ]";
    }
    
}

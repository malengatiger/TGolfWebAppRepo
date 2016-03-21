/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.data;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "clubUsed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClubUsed.findbyPlayer", query = "SELECT c FROM ClubUsed c where c.holeStat.practiceSession.player.playerID = :playerID order by c.holeStat.holeStatID"),
    @NamedQuery(name = "ClubUsed.findByClubUsedID", query = "SELECT c FROM ClubUsed c WHERE c.clubUsedID = :clubUsedID")})
public class ClubUsed implements Serializable {

    @JoinColumn(name = "shotShapeID", referencedColumnName = "shotShapeID")
    @OneToOne(fetch = FetchType.EAGER)
    private ShotShape shotShape;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clubUsedID")
    private Integer clubUsedID;
    @JoinColumn(name = "clubID", referencedColumnName = "clubID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Club club;
    @JoinColumn(name = "holeStatID", referencedColumnName = "holeStatID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private HoleStat holeStat;

    public ClubUsed() {
    }

    public ClubUsed(Integer clubUsedID) {
        this.clubUsedID = clubUsedID;
    }

    public Integer getClubUsedID() {
        return clubUsedID;
    }

    public void setClubUsedID(Integer clubUsedID) {
        this.clubUsedID = clubUsedID;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public HoleStat getHoleStat() {
        return holeStat;
    }

    public void setHoleStat(HoleStat holeStat) {
        this.holeStat = holeStat;
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clubUsedID != null ? clubUsedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClubUsed)) {
            return false;
        }
        ClubUsed other = (ClubUsed) object;
        if ((this.clubUsedID == null && other.clubUsedID != null) || (this.clubUsedID != null && !this.clubUsedID.equals(other.clubUsedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.ClubUsed[ clubUsedID=" + clubUsedID + " ]";
    }

    public ShotShape getShotShape() {
        return shotShape;
    }

    public void setShotShape(ShotShape shotShape) {
        this.shotShape = shotShape;
    }

  
    
}

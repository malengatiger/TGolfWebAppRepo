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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "coachPlayer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoachPlayer.findAll", query = "SELECT c FROM CoachPlayer c"),
    
    @NamedQuery(name = "CoachPlayer.findCoachesByPlayer", 
            query = "SELECT c.coach FROM CoachPlayer c WHERE c.player.playerID = :playerID order by c.player.lastName, c.player.firstName"),
    @NamedQuery(name = "CoachPlayer.findPlayersByCoach", 
            query = "SELECT c.player FROM CoachPlayer c WHERE c.coach.coachID = :coachID order by c.coach.lastName, c.coach.firstName")


})
public class CoachPlayer implements Serializable {

    @Column(name = "activeFlag")
    private Boolean activeFlag;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coachPlayerID")
    private Integer coachPlayerID;
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @JoinColumn(name = "coachID", referencedColumnName = "coachID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Coach coach;
    @JoinColumn(name = "playerID", referencedColumnName = "playerID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Player player;

    public CoachPlayer() {
    }

    public CoachPlayer(Integer coachPlayerID) {
        this.coachPlayerID = coachPlayerID;
    }

    public Integer getCoachPlayerID() {
        return coachPlayerID;
    }

    public void setCoachPlayerID(Integer coachPlayerID) {
        this.coachPlayerID = coachPlayerID;
    }

   
    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coachPlayerID != null ? coachPlayerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoachPlayer)) {
            return false;
        }
        CoachPlayer other = (CoachPlayer) object;
        if ((this.coachPlayerID == null && other.coachPlayerID != null) || (this.coachPlayerID != null && !this.coachPlayerID.equals(other.coachPlayerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.CoachPlayer[ coachPlayer=" + coachPlayerID + " ]";
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
}

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aubreymalabie
 */
@Entity
@Table(name = "coach")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coach.signIn", query = "SELECT c FROM Coach c where c.email = :email and c.pin = :pin"),
    @NamedQuery(name = "Coach.findAll", query = "SELECT c FROM Coach c order by c.lastName, c.firstName"),
    @NamedQuery(name = "Coach.findByFirstName", query = "SELECT c FROM Coach c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Coach.findByLastName", query = "SELECT c FROM Coach c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Coach.findByEmail", query = "SELECT c FROM Coach c WHERE c.email = :email"),
    @NamedQuery(name = "Coach.findByCellphone", query = "SELECT c FROM Coach c WHERE c.cellphone = :cellphone"),
    @NamedQuery(name = "Coach.findByPin", query = "SELECT c FROM Coach c WHERE c.pin = :pin"),
    @NamedQuery(name = "Coach.findByDateRegistered", query = "SELECT c FROM Coach c WHERE c.dateRegistered = :dateRegistered")})
public class Coach implements Serializable {

    @Column(name = "gender")
    private Short gender;
    @OneToMany(mappedBy = "coach", fetch = FetchType.EAGER)
    private List<PracticeSession> practiceSessionList;

    @Size(max = 256)
    @Column(name = "photoUrl")
    private String photoUrl;

    @OneToMany(mappedBy = "coach", fetch = FetchType.EAGER)
    private List<GcmDevice> gcmDeviceList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coachID")
    private Integer coachID;
    @Size(min = 1, max = 45)
    @Column(name = "firstName")
    private String firstName;
    @Size(min = 1, max = 45)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Size(min = 1, max = 45)
    @Column(name = "cellphone")
    private String cellphone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pin")
    private String pin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coach", fetch = FetchType.EAGER)
    private List<CoachPlayer> coachPlayerList;

    public Coach() {
    }

    public Coach(Integer coachID) {
        this.coachID = coachID;
    }

    public Coach(Integer coachID, String firstName, String lastName, String email, String cellphone, String pin, Date dateRegistered) {
        this.coachID = coachID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cellphone = cellphone;
        this.pin = pin;
        this.dateRegistered = dateRegistered;
    }

    public Integer getCoachID() {
        return coachID;
    }

    public void setCoachID(Integer coachID) {
        this.coachID = coachID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @XmlTransient
    public List<CoachPlayer> getCoachPlayerList() {
        return coachPlayerList;
    }

    public void setCoachPlayerList(List<CoachPlayer> coachPlayerList) {
        this.coachPlayerList = coachPlayerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coachID != null ? coachID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coach)) {
            return false;
        }
        Coach other = (Coach) object;
        if ((this.coachID == null && other.coachID != null) || (this.coachID != null && !this.coachID.equals(other.coachID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.Coach[ coachID=" + coachID + " ]";
    }

    @XmlTransient
    public List<GcmDevice> getGcmDeviceList() {
        return gcmDeviceList;
    }

    public void setGcmDeviceList(List<GcmDevice> gcmDeviceList) {
        this.gcmDeviceList = gcmDeviceList;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    @XmlTransient
    public List<PracticeSession> getPracticeSessionList() {
        return practiceSessionList;
    }

    public void setPracticeSessionList(List<PracticeSession> practiceSessionList) {
        this.practiceSessionList = practiceSessionList;
    }
    
}

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
import javax.persistence.Lob;
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
@Table(name = "gcmDevice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GcmDevice.findAll", query = "SELECT g FROM GcmDevice g"),
    @NamedQuery(name = "GcmDevice.findByGcmDeviceID", query = "SELECT g FROM GcmDevice g WHERE g.gcmDeviceID = :gcmDeviceID"),
    @NamedQuery(name = "GcmDevice.findByManufacturer", query = "SELECT g FROM GcmDevice g WHERE g.manufacturer = :manufacturer"),
    @NamedQuery(name = "GcmDevice.findByModel", query = "SELECT g FROM GcmDevice g WHERE g.model = :model"),
    @NamedQuery(name = "GcmDevice.findByProduct", query = "SELECT g FROM GcmDevice g WHERE g.product = :product"),
    @NamedQuery(name = "GcmDevice.findByDateRegistered", query = "SELECT g FROM GcmDevice g WHERE g.dateRegistered = :dateRegistered"),
    @NamedQuery(name = "GcmDevice.findBySerialNumber", query = "SELECT g FROM GcmDevice g WHERE g.serialNumber = :serialNumber"),
    @NamedQuery(name = "GcmDevice.findByAndroidVersion", query = "SELECT g FROM GcmDevice g WHERE g.androidVersion = :androidVersion"),
    @NamedQuery(name = "GcmDevice.findByApp", query = "SELECT g FROM GcmDevice g WHERE g.app = :app")})
public class GcmDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gcmDeviceID")
    private Integer gcmDeviceID;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "registrationID")
    private String registrationID;
    @Size(max = 100)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Size(max = 100)
    @Column(name = "model")
    private String model;
    @Size(max = 100)
    @Column(name = "product")
    private String product;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Size(max = 255)
    @Column(name = "serialNumber")
    private String serialNumber;
    @Size(max = 100)
    @Column(name = "androidVersion")
    private String androidVersion;
    @Size(max = 100)
    @Column(name = "app")
    private String app;
    @JoinColumn(name = "playerID", referencedColumnName = "playerID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Player player;
    @JoinColumn(name = "coachID", referencedColumnName = "coachID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Coach coach;

    public GcmDevice() {
    }

    public GcmDevice(Integer gcmDeviceID) {
        this.gcmDeviceID = gcmDeviceID;
    }

    public GcmDevice(Integer gcmDeviceID, String registrationID, Date dateRegistered) {
        this.gcmDeviceID = gcmDeviceID;
        this.registrationID = registrationID;
        this.dateRegistered = dateRegistered;
    }

    public Integer getGcmDeviceID() {
        return gcmDeviceID;
    }

    public void setGcmDeviceID(Integer gcmDeviceID) {
        this.gcmDeviceID = gcmDeviceID;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gcmDeviceID != null ? gcmDeviceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GcmDevice)) {
            return false;
        }
        GcmDevice other = (GcmDevice) object;
        if ((this.gcmDeviceID == null && other.gcmDeviceID != null) || (this.gcmDeviceID != null && !this.gcmDeviceID.equals(other.gcmDeviceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfpractice.data.GcmDevice[ gcmDeviceID=" + gcmDeviceID + " ]";
    }
    
}

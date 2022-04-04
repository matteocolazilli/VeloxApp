package it.univaq.veloxapp.model;

import java.util.Date;

public class Autovelox {

    public static Autovelox parseData(){
        //TODO: analizzare i dati scaricati e ricreare l'oggetto autovelox
        Autovelox auuu = null;
        return auuu;
    }
    private long id;
    private String municipality;
    private String province;
    private String region;
    private String speedlimit;
    private String insertionYear;
    private long insertionDate;
    private long insertionTime;
    private double longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSpeedlimit() {
        return speedlimit;
    }

    public void setSpeedlimit(String speedlimit) {
        this.speedlimit = speedlimit;
    }

    public String getInsertionYear() {
        return insertionYear;
    }

    public void setInsertionYear(String insertionYear) {
        this.insertionYear = insertionYear;
    }

    public long getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(long insertionDate) {
        this.insertionDate = insertionDate;
    }

    public long getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(long insertionTime) {
        this.insertionTime = insertionTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitutde() {
        return latitutde;
    }

    public void setLatitutde(double latitutde) {
        this.latitutde = latitutde;
    }

    private double latitutde;




}
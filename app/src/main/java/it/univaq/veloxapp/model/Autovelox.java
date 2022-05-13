package it.univaq.veloxapp.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "speed_checkers")

public class Autovelox implements Serializable {


    public Autovelox() {};

    /*  {
            "ccomune":"Collegno",
                "cprovincia":"TORINO",
                "cregione":"Piemonte",
                "cnome":"",
                "canno_inserimento":"2010",
                "cdata_e_ora_inserimento":"2010-10-12T19:31:50Z",
                "cidentificatore_in_openstreetmap":"474673274",
                "clongitudine":"7.5781483",
                "clatitudine":"45.0727533"
        } */
    public static Autovelox parseData(Context context, JSONObject autovelox){
        Autovelox result = new Autovelox();
        List<Address> addresses;

        try {

            result.setMunicipality(autovelox.getString("ccomune"));
            result.setProvince(autovelox.getString("cprovincia"));
            result.setRegion(autovelox.getString("cregione"));
            result.setInsertionDate(convertToTimestamp(autovelox.getString("cdata_e_ora_inserimento").split("T")[0]));
            result.setLongitude(autovelox.getDouble("clongitudine"));
            result.setLatitude(autovelox.getDouble("clatitudine"));
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(result.getLatitude(), result.getLongitude(), 1);
            result.setAddress(addresses.get(0).getAddressLine(0));

            return result;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static long convertToTimestamp(String text) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = format.parse(text);
            if (date != null) return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String address;
    private String municipality;
    private String province;
    private String region;
    @ColumnInfo(name = "insertion_date")
    private long insertionDate;
    private double longitude;
    private double latitude;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public long getInsertionDate() {
        return this.insertionDate;
    }

    public void setInsertionDate(long insertionDate) {
        this.insertionDate = insertionDate;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }





}
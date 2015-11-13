package com.example.tracklocation.Model;

import android.util.Log;

import java.util.Date;

/**
 * Created by Administrator on 11/13/2015.
 */
public class Location {
    private String mDate;
    private double mLatitude;
    private double mLongitude;
    private float mSpeed;

    public Location() {
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
    }
}

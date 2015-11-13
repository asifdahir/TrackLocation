package com.example.tracklocation;

import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by Asif on 02/11/2015.
 */
public class GpsListener implements GpsStatus.Listener {
    public static final String TAG = GpsListener.class.getSimpleName();
    private Context mContext;
    private LocationManager mLocationManager;
    private Location mLocation;
    private String mGpsInfo;
    private Date mDate;
    private double mLatitude;
    private double mLongitude;
    private float mSpeed;
    private long mLastLocationMillis;
    private boolean mIsGPSFix;

    public GpsListener(Context context) {

        mContext = context;

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addGpsStatusListener(this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location == null)
                    return;

                mLastLocationMillis = SystemClock.elapsedRealtime();

                mLocation = location;
                mDate = new Date(mLocation.getTime());
                mLatitude = mLocation.getLatitude();
                mLongitude = mLocation.getLongitude();
                mSpeed = mLocation.getSpeed();

                update();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }
        });
    }

    public Date getDate() {
        return mDate;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public String getLocation() {
        if (mLocation == null)
            return "";
        return "last loc time:" + Common.getDate(mLastLocationMillis) + "\n" +
                "provider:" + mLocation.getProvider() + "\n" +
                "accuracy:" + mLocation.getAccuracy() + "\n" +
                "altitude:" + mLocation.getAltitude() + "\n" +
                "bearing:" + mLocation.getBearing() + "\n" +
                "lat:" + mLocation.getLatitude() + "," +
                "long:" + mLocation.getLongitude() + "\n" +
                "speed:" + mLocation.getSpeed() + "\n" +
                "gps time:" + Common.getDate(mLocation.getTime());
    }

    public boolean getIsGpsFix() {
        return mIsGPSFix;
    }

    public String getGpsInfo() {
        return mGpsInfo;
    }

    public void update() {
        Intent intent = new Intent();
        intent.setAction(Common.ACTION_STRING_SERVICE);
        intent.putExtra(Common.GPS_FIX, getIsGpsFix());
        intent.putExtra(Common.GPS_INFO, getGpsInfo());
        intent.putExtra(Common.GPS_LOCATION, getLocation());
        mContext.sendBroadcast(intent);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                //Log.d(MainActivity.TAG, "GPS_EVENT_SATELLITE_STATUS");
                if (mLocation != null)
                    mIsGPSFix = (SystemClock.elapsedRealtime() - mLastLocationMillis) < 3000;

                if (mIsGPSFix) {
                    // A fix has been acquired.
                    // Do something.
                } else {
                    // The fix has been lost.
                    // Do something.
                }
                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
                // Log.d(MainActivity.TAG, "GPS_EVENT_FIRST_FIX");
                // Do something.
                mIsGPSFix = true;
                break;

            case GpsStatus.GPS_EVENT_STARTED:
                //Log.d(MainActivity.TAG, "GPS_EVENT_STARTED");
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                //Log.d(MainActivity.TAG, "GPS_EVENT_STOPPED");
                break;
        }

        mGpsInfo = "";
        GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> sat = satellites.iterator();
            int i = 0;
            while (sat.hasNext()) {

                GpsSatellite satellite = sat.next();
                mGpsInfo += (i++) + ":" +
                        satellite.getPrn() + "," +
                        satellite.usedInFix() + "," +
                        satellite.getSnr() + "," +
                        satellite.getAzimuth() + "," +
                        satellite.getElevation() + "," +
                        "\n";
            }
        }

        update();

    }
}

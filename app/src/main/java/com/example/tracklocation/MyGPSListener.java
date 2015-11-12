package com.example.tracklocation;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by Asif on 02/11/2015.
 */
public class MyGPSListener implements GpsStatus.Listener {

    private Context mContext;
    private LocationManager mLocationManager;
    private Location mLocation;
    private String mGpsInfo;
    private long mLastLocationMillis;
    private boolean mIsGPSFix;

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

    public MyGPSListener(Context context) {

        mContext = context;

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addGpsStatusListener(this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d(MainActivity.TAG, "onLocationChanged");

                if (location == null) return;

                mLastLocationMillis = SystemClock.elapsedRealtime();

                // Do something.
                mLocation = location;

                ((MainActivity) mContext).updateUI(mIsGPSFix, mGpsInfo, getLocation());
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

        ((MainActivity) mContext).updateUI(mIsGPSFix, mGpsInfo, getLocation());

    }
}

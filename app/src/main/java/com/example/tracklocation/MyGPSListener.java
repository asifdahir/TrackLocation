package com.example.tracklocation;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Asif on 02/11/2015.
 */
public class MyGPSListener implements GpsStatus.Listener {
    private Context mContext;
    private LocationManager mLocationManager;
    private Location mLocation;
    private long mLastLocationMillis;
    private boolean mIsGPSFix;

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

                Toast.makeText(mContext, location.toString(), Toast.LENGTH_LONG).show();

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

        //ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        //long period = 1000; // the period between successive executions
        //exec.scheduleAtFixedRate(new SatelliteCountTask(mContext, mLocationManager), 0, period, TimeUnit.MICROSECONDS);
        //long delay = 1000 * 30; //the delay between the termination of one execution and the commencement of the next
        //exec.scheduleWithFixedDelay(new SatelliteCountTask(mContext, mLocationManager), 0, delay, TimeUnit.MICROSECONDS);
    }

    @Override
    public void onGpsStatusChanged(int event) {

        TextView textView = (TextView) ((MainActivity) mContext).findViewById(R.id.textview_gps_status);
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
                textView.setText("GPS_EVENT_SATELLITE_STATUS");
                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
                //Log.d(MainActivity.TAG, "GPS_EVENT_FIRST_FIX");
                // Do something.
                mIsGPSFix = true;
                textView.setText("GPS_EVENT_FIRST_FIX");
                break;

            case GpsStatus.GPS_EVENT_STARTED:
                //Log.d(MainActivity.TAG, "GPS_EVENT_STARTED");
                textView.setText("GPS_EVENT_STARTED");
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                //Log.d(MainActivity.TAG, "GPS_EVENT_STOPPED");
                textView.setText("GPS_EVENT_STOPPED");
                break;
        }

        String strGpsStats = "";
        GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> sat = satellites.iterator();
            int i = 0;
            while (sat.hasNext()) {

                GpsSatellite satellite = sat.next();
                strGpsStats += (i++) + ": " + satellite.getPrn() + "," + satellite.usedInFix() + "," + satellite.getSnr() + "," + satellite.getAzimuth() + "," + satellite.getElevation() + "\n\n";
            }

            textView = (TextView) ((AppCompatActivity) mContext).findViewById(R.id.textview_satellite_count);
            textView.setText(strGpsStats);
        }
    }
}

package com.example.tracklocation;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;

/**
 * Created by Asif on 02/11/2015.
 */
public class SatelliteCountTask implements Runnable {
    private Context mContext;
    private LocationManager mLocationManager;

    public SatelliteCountTask(Context context, LocationManager locationManager) {
        mContext = context;
        mLocationManager = locationManager;
    }

    @Override
    public void run() {
        Log.d(MainActivity.TAG, "run");

        String strGpsStats = "no satellite";
        GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> sat = satellites.iterator();
            int i = 0;
            while (sat.hasNext()) {

                GpsSatellite satellite = sat.next();
                strGpsStats += (i++) + ": " + satellite.getPrn() + "," + satellite.usedInFix() + "," + satellite.getSnr() + "," + satellite.getAzimuth() + "," + satellite.getElevation() + "\n\n";
            }

            TextView textView = (TextView) ((AppCompatActivity) mContext).findViewById(R.id.textview_satellite_count);
            textView.setText(strGpsStats);
        }
    }
}
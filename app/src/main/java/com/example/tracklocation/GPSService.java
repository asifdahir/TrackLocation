package com.example.tracklocation;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.tracklocation.Model.Location;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 11/12/2015.
 */
public class GPSService extends Service {

    public static final String TAG = GPSService.class.getSimpleName();
    private GpsListener mGpsListener = null;
    private DatabaseHandler mDatabaseHandler = null;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mHandler = new Handler();
    private boolean mSaveDataInDB = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        try {
            if (intent != null)
                mSaveDataInDB = intent.getBooleanExtra(Common.SAVE_DATA_IN_DB, false);
            mGpsListener = new GpsListener(this);
            if (mSaveDataInDB) {
                mDatabaseHandler = new DatabaseHandler(getApplicationContext());
                startTimer();
            } else {
                stopTimer();
            }
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage(), ex);
        }

        return Service.START_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {
        if (mSaveDataInDB)
            stopTimer();
        return super.stopService(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        Log.d(TAG, "run");

                        if (mGpsListener == null)
                            return;

                        if (!mGpsListener.getIsGpsFix())
                            return;

                        Location location = new Location();
                        location.setDate(Common.getDate(mGpsListener.getDate().getTime()));
                        location.setLatitude(mGpsListener.getLatitude());
                        location.setLongitude(mGpsListener.getLongitude());
                        location.setSpeed(mGpsListener.getSpeed());

                        if (mDatabaseHandler == null)
                            mDatabaseHandler = new DatabaseHandler(getApplicationContext());
                        mDatabaseHandler.addLocation(location);
                    }
                });
            }
        };

        mTimer.schedule(mTimerTask, 1, 10 * 1000);
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
    }
}

package com.example.tracklocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences = null;
    private MainActivityReceiver mMainActivityReceiver = null;
    private Button mButtonStart = null;
    private Button mButtonStop = null;
    private boolean mSaveDataInDB = false;

    public void updateUI(boolean isGpsFix, String gpsInfo, String location) {
        TextView textViewIsGpsFix;
        TextView textViewGpsInfo;
        TextView textViewLocation;

        textViewIsGpsFix = (TextView) findViewById(R.id.textview_is_gps_fix);
        textViewIsGpsFix.setText("GPS : " + isGpsFix);

        textViewGpsInfo = (TextView) findViewById(R.id.textview_gps_info);
        textViewGpsInfo.setText("GPS Info\n" + gpsInfo);

        textViewLocation = (TextView) findViewById(R.id.textview_location);
        textViewLocation.setText("Location\n" + location);
    }

    private void onButtonStart() {
        mButtonStart.setEnabled(false);
        mSaveDataInDB = true;

        mSharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        mSharedPreferences.edit().putInt(Common.SAVE_DATA_IN_DB, 1).apply();

        Intent i = new Intent(this, GPSService.class);
        i.putExtra(Common.SAVE_DATA_IN_DB, mSaveDataInDB);
        startService(i);
    }

    private void onButtonStop() {
        mSaveDataInDB = false;
        mButtonStart.setEnabled(true);

        mSharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        mSharedPreferences.edit().putInt(Common.SAVE_DATA_IN_DB, 0).apply();

        unregisterReceiver(mMainActivityReceiver);

        Intent i = new Intent(this, GPSService.class);
        stopService(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        int x = mSharedPreferences.getInt(Common.SAVE_DATA_IN_DB, -1);
        if (x == -1)
            mSaveDataInDB = false;
        else
            mSaveDataInDB = (x > 0) ? true : false;

        mButtonStart = (Button) findViewById(R.id.button_start);
        mButtonStop = (Button) findViewById(R.id.button_stop);

        mButtonStart.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);

        if (mSaveDataInDB)
            mButtonStart.setEnabled(false);

        mMainActivityReceiver = new MainActivityReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Common.ACTION_STRING_SERVICE);
        registerReceiver(mMainActivityReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search_locations) {
            //startActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mSharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        int x = mSharedPreferences.getInt(Common.SAVE_DATA_IN_DB, -1);
        if (x == -1)
            mSaveDataInDB = false;
        else
            mSaveDataInDB = (x > 0) ? true : false;

        if (mSaveDataInDB)
            mButtonStart.setEnabled(false);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                onButtonStart();
                break;

            case R.id.button_stop:
                onButtonStop();
                break;
        }
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mMainActivityReceiver);

        super.onStop();
    }

    public class MainActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isGpsFix = intent.getBooleanExtra(Common.GPS_FIX, false);
            String gpsInfo = intent.getStringExtra(Common.GPS_INFO);
            String location = intent.getStringExtra(Common.GPS_LOCATION);
            updateUI(isGpsFix, gpsInfo, location);
        }
    }
}

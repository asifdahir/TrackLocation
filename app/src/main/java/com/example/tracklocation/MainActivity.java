package com.example.tracklocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityReceiver mainActivityReceiver = null;
    private Button buttonStart = null;
    private Button buttonStop = null;
    private boolean saveDataInDB = false;

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
        buttonStart.setEnabled(false);
        saveDataInDB = true;

        Intent i = new Intent(this, GPSService.class);
        i.putExtra(Common.SAVE_DATA_IN_DB, saveDataInDB);
        startService(i);
    }

    private void onButtonStop() {
        saveDataInDB = false;
        buttonStart.setEnabled(true);

        Intent i = new Intent(this, GPSService.class);
        i.putExtra(Common.SAVE_DATA_IN_DB, saveDataInDB);
        startService(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.button_start);
        buttonStop = (Button) findViewById(R.id.button_stop);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
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
    protected void onStart() {
        mainActivityReceiver = new MainActivityReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Common.ACTION_STRING_SERVICE);
        registerReceiver(mainActivityReceiver, intentFilter);

        Intent i = new Intent(this, GPSService.class);
        i.putExtra(Common.SAVE_DATA_IN_DB, saveDataInDB);
        startService(i);

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mainActivityReceiver);

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

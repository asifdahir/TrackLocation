package com.example.tracklocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = MainActivity.class.getSimpleName();

    private MyGPSListener gpsListener = null;
    private Button buttonStart = null;
    private Button buttonStop = null;

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
        if (!gpsListener.getIsGpsFix()) {
            Toast.makeText(this, "GPS not fix", Toast.LENGTH_SHORT).show();
        }

        
    }

    private void onButtonStop() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.button_start);
        buttonStop = (Button) findViewById(R.id.button_stop);

        gpsListener = new MyGPSListener(this);
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
}

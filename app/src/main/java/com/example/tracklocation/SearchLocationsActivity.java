package com.example.tracklocation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.tracklocation.Model.Location;

import java.util.List;

/**
 * Created by Administrator on 11/13/2015.
 */
public class SearchLocationsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SearchLocationsActivity.class.getSimpleName();
    private Button mButtonPlot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_locations);

        DatabaseHandler databaseHandler = null;
        List<Location> locations = null;
        SearchLocationsItem[] searchLocationsItems = null;
        Location location = null;
        RecyclerView recyclerView = null;
        SearchLocationsAdapter adapter = null;
        String text;
        int i;

        databaseHandler = new DatabaseHandler(this);
        locations = databaseHandler.getAllLocations();
        searchLocationsItems = new SearchLocationsItem[locations.size()];
        for (i = 0; i < locations.size(); i++) {
            location = locations.get(i);
            text = location.getDate() + ", " + location.getLatitude() + ", " + location.getLongitude() + ", " + location.getSpeed();
            searchLocationsItems[i] = new SearchLocationsItem(text);
        }
        adapter = new SearchLocationsAdapter(searchLocationsItems);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mButtonPlot = (Button) findViewById(R.id.button_plot);
        mButtonPlot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_plot:
                break;
        }
    }
}

package com.example.tracklocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 11/13/2015.
 */
public class SearchLocationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_locations);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        SearchLocationsItem itemsData[] = {
                new SearchLocationsItem("Help"),
                new SearchLocationsItem("Delete"),
                new SearchLocationsItem("Cloud"),
                new SearchLocationsItem("Favorite"),
                new SearchLocationsItem("Like"),
                new SearchLocationsItem("Rating")
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SearchLocationsAdapter adapter = new SearchLocationsAdapter(itemsData);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}

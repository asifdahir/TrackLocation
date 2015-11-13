package com.example.tracklocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 11/13/2015.
 */
public class SearchLocationsAdapter extends RecyclerView.Adapter<SearchLocationsAdapter.ViewHolder> {
    private SearchLocationsItem[] mSearchLocationsItems;

    public SearchLocationsAdapter(SearchLocationsItem[] searchLocationsItems) {
        mSearchLocationsItems = searchLocationsItems;
    }

    @Override
    public SearchLocationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_locations_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.textView.setText(mSearchLocationsItems[position].text);
    }

    @Override
    public int getItemCount() {
        return mSearchLocationsItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView = (TextView) itemLayoutView.findViewById(R.id.text);
        }
    }
}

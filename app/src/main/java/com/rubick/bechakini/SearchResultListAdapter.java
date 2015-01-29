package com.rubick.bechakini;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubick.bechakini.InfiniteScroll.InfiniteScrollAdapter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mehedee Zaman on 1/29/2015.
 */
public class SearchResultListAdapter extends InfiniteScrollAdapter {


    ArrayList<SearchResult> results;



    public SearchResultListAdapter(Context context, ArrayList<SearchResult> results) {

        super(context);

        this.results = new ArrayList<SearchResult>();


    }

    @Override
    public ArrayList getItems() {
        return results;
    }

    @Override
    public void addItems(Collection items) {
        if (items.size() > 0) {
            this.results.addAll(items);
        } else {
            super.setDoneLoading();
        }
        notifyDataSetChanged();
    }


    @Override
    public Object getRealItem(int position) {
        return results.get(position);
    }

    @Override
    public View getRealView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {


        Log.d("LISTVIEW","creating view " + position);
        Log.d("LISTVIEW","array size  " + results.size());

        View v = convertView;

        if(v==null)
        {
            //Log.d("NULL", "VIIIEWWWW NNUUULLLLLL");
         //   LayoutInflater li = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            v = inflater.inflate(R.layout.search_result_row, null);
        }

        TextView tvName, tvLocation, tvPrice;
        ImageView ivThumb;



        tvName = (TextView) v.findViewById(R.id.row_tv_name);

        SearchResult result = results.get(position);

        if(null == result){
            Log.d("LISTVIEW","result null  " + results.size());
        }

        if(null == tvName){
            Log.d("LISTVIEW","tv null  " + results.size());
        }

        tvName.setText(result.name);

        tvLocation = (TextView) v.findViewById(R.id.row_tv_location);
        tvLocation.setText(results.get(position).areaName + ", " + results.get(position).districtName);

        ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
        ivThumb.setImageResource(R.drawable.laptop);

//        v.setTag(listArray.get(position).getPersonId());

        return v;
    }


    @Override
    public View getLoadingView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.list_loading, null);
    }
}

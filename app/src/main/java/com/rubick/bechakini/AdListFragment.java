package com.rubick.bechakini;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.rubick.bechakini.lib.IInfiniteScrollListener;
import com.rubick.bechakini.lib.InfiniteScrollListView;
import com.rubick.bechakini.lib.InfiniteScrollOnScrollListener;

import java.util.ArrayList;


public class AdListFragment extends Fragment {

    private boolean executing = false;
    InfiniteScrollListView listView;
    ListTask listTask;

    public AdListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        listView = (InfiniteScrollListView) getView().findViewById(R.id.ad_listview);
        listView.setListener(new InfiniteScrollOnScrollListener(new IInfiniteScrollListener() {
            @Override
            public void endIsNear() {
                if (!executing) {
                    Toast.makeText(getActivity(), "End is near", Toast.LENGTH_SHORT).show();
                    executing = true;
                    listTask = new ListTask();
                    listTask.execute(listView.getRealCount());
                }
            }

            @Override
            public void onScrollCalled(int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        }));

        listView.setAdapter(new SearchResultListAdapter(getActivity(), HardConstants.demoresults));

        listView.appendItems(HardConstants.demoresults);

    }


    private class ListTask extends AsyncTask<Integer, Void, ArrayList<SearchResult>> {

        @Override
        protected ArrayList<SearchResult> doInBackground(Integer... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<SearchResult> items = new ArrayList<SearchResult>();
            if (params[0] < 90) {
                //  for (int i = params[0]; i < (params[0] + 8); i++) {
                //    String str = "Index: " + String.valueOf(i);
                items.addAll(HardConstants.demoresults);
                // }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<SearchResult> result) {
            listView.appendItems(result);
            executing = false;
            if (result.size() > 0) {
                Toast.makeText(getActivity(), "Loaded " + String.valueOf(result.size()) + " items", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getActivity(), "No more items to load", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
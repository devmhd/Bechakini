package com.rubick.bechakini;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rubick.bechakini.lib.IInfiniteScrollListener;
import com.rubick.bechakini.lib.InfiniteScrollListView;
import com.rubick.bechakini.lib.InfiniteScrollOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ServiceConfigurationError;


public class RecentAdListFragment extends Fragment {

    private boolean executing = false;
    InfiniteScrollListView listView;
    ListTask listTask;

    String requestUrl;
    int maxLoadCount;



    ProgressBar pbInitial;

    boolean isInitialLoading;



    public RecentAdListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        requestUrl = getArguments().getString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL);
        maxLoadCount = getArguments().getInt(HardConstants.ADLISTFRAGMENT_ARG_MAXLOADCOUNT);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_ad_list, container, false);


    }

    void initListView(ArrayList<SearchResult> results){

        View view = getView();
 //       if(view == null) return;

        listView = (InfiniteScrollListView) view.findViewById(R.id.ad_listview);

        listView.setListener(new InfiniteScrollOnScrollListener(new IInfiniteScrollListener() {
            @Override
            public void endIsNear() {
                if (!executing) {
//                    Toast.makeText(getActivity(), "End is near", Toast.LENGTH_SHORT).show();
                    executing = true;


//                    listTask = new ListTask();
//                    listTask.execute(listView.getRealCount(), 0);
                }
            }

            @Override
            public void onScrollCalled(int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        }));

        listView.setAdapter(new SearchResultListAdapter(getActivity(), results));

        listView.appendItems(results);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), AdDetailActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pbInitial = (ProgressBar) getView().findViewById(R.id.fragment_adlist_progressbar);

        isInitialLoading = true;
//        new ListTask().execute(30);


        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {



                    @Override
                    public void onResponse(JSONObject responseJson) {
                        try {



                            ArrayList<SearchResult> results = new ArrayList<SearchResult>();

                            JSONArray data = responseJson.getJSONArray("data");

                            for(int i = 0; i<data.length(); ++i){



                                results.add(new SearchResult(
                                        data.getJSONObject(i).getString("title"),
                                        data.getJSONObject(i).getString("price"),
                                        data.getJSONObject(i).getString("image_url"),
                                        data.getJSONObject(i).getString("location")
                                ));
                            }


                            executing = false;

                            if(isInitialLoading){
                                isInitialLoading = false;

                                initListView(results);
                                pbInitial.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);

                                listView.appendItems(new ArrayList<SearchResult>());


                            } else {
                                listView.appendItems(results);

                                if (results.size() > 0) {
                                    Toast.makeText(getActivity(), "Loaded " + String.valueOf(results.size()) + " items", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(getActivity(), "No more items to load", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();

                    }

                });

        VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(jsonRequest);


    }




    private class ListTask extends AsyncTask<Integer, Void, ArrayList<SearchResult>> {

        @Override
        protected ArrayList<SearchResult> doInBackground(Integer... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<SearchResult> items = new ArrayList<SearchResult>();
            if (params[0] < maxLoadCount) {
                //  for (int i = params[0]; i < (params[0] + 8); i++) {
                //    String str = "Index: " + String.valueOf(i);
                items.addAll(HardConstants.demoresults);
                // }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<SearchResult> result) {

   //         if(null == listView) return;
            executing = false;

            if(isInitialLoading){
                isInitialLoading = false;

                initListView(result);
                pbInitial.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

            } else {
                listView.appendItems(result);

                if (result.size() > 0) {
                    Toast.makeText(getActivity(), "Loaded " + String.valueOf(result.size()) + " items", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No more items to load", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
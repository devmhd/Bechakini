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
import com.android.volley.toolbox.JsonObjectRequest;
import com.rubick.bechakini.lib.IInfiniteScrollListener;
import com.rubick.bechakini.lib.InfiniteScrollListView;
import com.rubick.bechakini.lib.InfiniteScrollOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FilteredAdListFragment extends Fragment {

    private boolean executing = false;
    InfiniteScrollListView listView;


    String requestUrl;



    int requestCount;



    ProgressBar pbInitial;

    boolean isInitialLoading;

    Button btnSort, btnFilter;

    public FilteredAdListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        requestUrl = getArguments().getString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL);



        Log.d("REQUEST_URL",requestUrl);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_filtered_list, container, false);


    }

    void initListView(ArrayList<SearchResult> results){

        View view = getView();
 //       if(view == null) return;

        listView = (InfiniteScrollListView) view.findViewById(R.id.ad_listview);

        listView.setListener(new InfiniteScrollOnScrollListener(new IInfiniteScrollListener() {
            @Override
            public void endIsNear() {

                if(requestCount > 3) {
                    listView.appendItems(new ArrayList<SearchResult>());
                    return;
                }
                if (!executing) {
//                    Toast.makeText(getActivity(), "End is near", Toast.LENGTH_SHORT).show();
                    executing = true;
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                            new Response.Listener<JSONObject>() {



                                @Override
                                public void onResponse(JSONObject responseJson) {
                                    try {

                                        requestCount++;

                                        ArrayList<SearchResult> results = new ArrayList<SearchResult>();

                                        JSONArray data = responseJson.getJSONArray("data");

                                        for(int i = data.length()-1; i>=0; --i){



                                            results.add(new SearchResult(
                                                    data.getJSONObject(i).getString("title"),
                                                    data.getJSONObject(i).getString("price"),
                                                    data.getJSONObject(i).getString("image_url"),
                                                    HardConstants.areaStringArray[i]
                                            ));
                                        }


                                        executing = false;

                                        if(isInitialLoading){
                                            isInitialLoading = false;

                                            initListView(results);
                                            pbInitial.setVisibility(View.GONE);
                                            listView.setVisibility(View.VISIBLE);




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

        btnFilter = (Button) getView().findViewById(R.id.fragment_adlist_btn_filter);
        btnSort = (Button) getView().findViewById(R.id.fragment_adlist_btn_sort);
        requestCount = 0;
        pbInitial = (ProgressBar) getView().findViewById(R.id.fragment_adlist_progressbar);




        isInitialLoading = true;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {



                    @Override
                    public void onResponse(JSONObject responseJson) {
                        try {



                            ArrayList<SearchResult> results = new ArrayList<SearchResult>();

                            JSONArray data = responseJson.getJSONArray("data");

                            for(int i = data.length()-1; i>=0; --i){



                                results.add(new SearchResult(
                                        data.getJSONObject(i).getString("title"),
                                        data.getJSONObject(i).getString("price"),
                                        data.getJSONObject(i).getString("image_url"),
                                        HardConstants.areaStringArray[i]
                                ));
                            }


                            executing = false;

                            if(isInitialLoading){
                                isInitialLoading = false;

                                initListView(results);
                                pbInitial.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);




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





    @Override
    public void onDestroyView() {

        VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(getActivity());
        super.onStop();
    }


}
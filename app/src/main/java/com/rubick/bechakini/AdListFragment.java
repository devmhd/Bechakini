package com.rubick.bechakini;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.MediaStore;
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

import java.io.File;
import java.util.ArrayList;


public class AdListFragment extends Fragment {

    private boolean executing = false;
    InfiniteScrollListView listView;


    String requestUrl;


    int requestCount;

    AlertDialog.Builder filterDialogBuilder;
    AlertDialog filterDialog;

    ProgressBar pbInitial;

    boolean isInitialLoading;

    Button btnSort, btnFilter;

    public AdListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        requestUrl = getArguments().getString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL);


        Log.d("REQUEST_URL",requestUrl);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_list, container, false);





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

                                        for(int i = 0; i<data.length(); ++i){



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


        initDialog();

        isInitialLoading = true;
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



        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.show();

            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                final String[] options = { "Price (increasing)", "Price (decreasing)","Location (nearest)", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sort by");
                builder.setItems(options, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int item)
                    {
                        if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                        else {

                            Intent intent = new Intent(getActivity(),FilteredAdListActivity.class);
                            intent.putExtra(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL, requestUrl);
                            getActivity().startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });



    }

    void initDialog(){
        filterDialogBuilder = new AlertDialog.Builder(getActivity());
        filterDialogBuilder.setTitle("Filters");

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filter, null);

        filterDialogBuilder.setView(dialogView);

        AutoCompleteTextView autoTv = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_filter_locationAutoComplete);

        autoTv.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, HardConstants.areaStringArray));
        autoTv.setThreshold(1);

        filterDialogBuilder.setPositiveButton("Apply Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(getActivity(),FilteredAdListActivity.class);
                intent.putExtra(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL, requestUrl);
                getActivity().startActivity(intent);
            }
        });

        filterDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                filterDialog.cancel();
            }
        });


        filterDialog = filterDialogBuilder.create();

    }



    @Override
    public void onDestroyView() {

        VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(getActivity());
        super.onStop();
    }


}
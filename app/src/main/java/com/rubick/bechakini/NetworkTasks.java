package com.rubick.bechakini;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

/**
 * Created by Mehedee Zaman on 1/21/2015.
 */
public class NetworkTasks {

    static Context appContext;
    static HttpClient httpclient;

    public static void init(Context context){
        appContext = context;

        httpclient = getThreadSafeClient();
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null);
    }


    public static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
        return client;
    }


    public static String getString(String URL, List<NameValuePair> posts, boolean isPost) throws UnsupportedEncodingException
    {

        HttpPost httpPost = null;
        HttpGet httpGet = null;

        if(isPost)
        {
            httpPost = new HttpPost(URL);
            httpPost.setEntity(new UrlEncodedFormEntity(posts));


        }
        else httpGet = new HttpGet(URL);


        String jOb = null;

        try {
            HttpResponse response = httpclient.execute(isPost?httpPost:httpGet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }


            jOb = builder.toString();
        }
        catch(Exception e) {
            Log.d(URL, e.toString());
        }



        return jOb;

    }


    public static JSONObject getJsonObject(String URL, List<NameValuePair> posts, boolean isPost) throws UnsupportedEncodingException
    {

        Log.d("NETWORK","SUBMIT: " + URL);
        for (int i = 0; i < posts.size(); i++) {
            Log.d("NETWORK", posts.get(i).toString());
        }

        HttpPost httpPost = null;
        HttpGet httpGet = null;

        if(isPost)
        {
            httpPost = new HttpPost(URL);
            httpPost.setEntity(new UrlEncodedFormEntity(posts));


        }
        else httpGet = new HttpGet(URL);


        JSONObject jOb = null;

        try {
            HttpResponse response = httpclient.execute(isPost?httpPost:httpGet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }


            jOb = new JSONObject(builder.toString());
        }
        catch(Exception e) {
            Log.d(URL, e.toString());
        }


        Log.d("NETWORK", "Response: " + jOb.toString());
        return jOb;

    }





}

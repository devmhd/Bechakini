package com.rubick.bechakini;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class LaunchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceStorage.init(getApplicationContext());

        if(PreferenceStorage.isFirstLaunch()){

        }
        else {
            startActivity(new Intent(LaunchActivity.this, RegisterActivity.class));
        }

    }






}

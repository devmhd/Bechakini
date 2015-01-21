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
        NetworkTasks.init(getApplicationContext());



        if(PreferenceStorage.isFirstLaunch()){

            PreferenceStorage.setFirstLaunch();
            startActivity(new Intent(LaunchActivity.this, SignUpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

        else {

            if(PreferenceStorage.isLoggedIn()){
                startActivity(new Intent(LaunchActivity.this, FakeHomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }else{
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        }

    }






}

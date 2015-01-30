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
        HardConstants.init();


        startActivity(new Intent(LaunchActivity.this, HomeActivity.class));


//
//        if(PreferenceStorage.isFirstLaunch()){
//
//            PreferenceStorage.setFirstLaunch();
//
//            Intent intent = new Intent(LaunchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.putExtra("showSignUp", true);
//            startActivity(intent);
//            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
//        }
//
//        else {
//
//            if(PreferenceStorage.isLoggedIn()){
//                startActivity(new Intent(LaunchActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
//            }else{
//                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra("showSignUp", false);
//                startActivity(intent);
//                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
//            }
//        }

    }






}

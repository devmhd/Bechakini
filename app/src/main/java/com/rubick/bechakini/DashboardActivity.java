package com.rubick.bechakini;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DashboardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initGUI();
    }

    void logout(){

        PreferenceStorage.setLoggedIn(false);
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("showSignUp", false);
        startActivity(intent);
    }

    void initGUI(){

        TextView tvUserName = (TextView) findViewById(R.id.tvUsername);
        TextView tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);

        tvUserName.setText(PreferenceStorage.getLoggerName());
        tvUserEmail.setText(PreferenceStorage.getLoggerEmail());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update_profile) {



        }

        if (id == R.id.action_logout) {

            logout();

        }

        return super.onOptionsItemSelected(item);
    }
}

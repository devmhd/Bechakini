package com.rubick.bechakini;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DashboardActivity extends ActionBarActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
     //   mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
      //  mDrawerList.setAdapter(new ArrayAdapter<String>(this,
            //    android.R.layout.simple_list_item_1, mPlanetTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Closed");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Open");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    void logout(){

        PreferenceStorage.setLoggedIn(false);
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("showSignUp", false);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        if (id == R.id.action_update_profile) {

        }

        if (id == R.id.action_logout) {

            logout();

        }

        return super.onOptionsItemSelected(item);
    }


    private class CategoriesPagerAdapter extends FragmentPagerAdapter {

        public CategoriesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {


            Fragment f = null;

            switch (i) {
                case 0:

              //      f = new ProfileMainFragment();
                    break;

                case 1:
            //        f = new ProfileInfoFragment();
                    break;

                case 2:
           //         f = new ProfileLookinFragment();
                    break;


            }

            return f;

        }

        @Override
        public int getCount() {
            return HardConstants.adCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return HardConstants.adCategories.get(position).name;
        }
    }
}

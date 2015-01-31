package com.rubick.bechakini;



import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.io.File;


public class DashboardActivity extends ActionBarActivity{


    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private CategoriesPagerAdapter categoryPagerAdapter;
    PagerSlidingTabStrip slidingTabStrip;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.drawer_close);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_open);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initPager(getIntent().getExtras().getInt("catId"));
        initDrawerActions();




    }

    void initDrawerActions(){

        RelativeLayout btnNewAd = (RelativeLayout) findViewById(R.id.drawer_btn_newad);
        btnNewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, UploadPhotoActivity.class));
            }
        });

        ListView navCategoryList = (ListView) findViewById(R.id.drawer_list_categories);
        navCategoryList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, HardConstants.categoryStringArray));
        navCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            initPager(position);

            }
        });

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

    void initPager(int categoryId){

        ViewPager pager = (ViewPager) findViewById(R.id.tabViewPager);

        final CategoriesPagerAdapter adapter =  new CategoriesPagerAdapter(getSupportFragmentManager(),categoryId);

        pager.setAdapter(adapter);
    //    adapter.notifyDataSetChanged();
        slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabStrip);
        slidingTabStrip.setViewPager(pager);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.action_update_profile) {

        }

        if (id == R.id.action_logout) {

            logout();

        }

        if (id == R.id.action_dashboard_filter) {


        }

        return super.onOptionsItemSelected(item);
    }



    private class CategoriesPagerAdapter extends FragmentStatePagerAdapter {

        private int categoryId;

        public CategoriesPagerAdapter(FragmentManager fm, int catId) {
            super(fm);
            this.categoryId = catId;



        }

        @Override
        public Fragment getItem(int i) {




            Fragment fr = new AdListFragment();
            Bundle bundle = new Bundle();

            Log.d("BBBB", "Cat " + HardConstants.adCategories.get(categoryId).name + " Subcat " + HardConstants.adCategories.get(categoryId).subCategories.get(i).name);

            bundle.putString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL, HardConstants.adCategories.get(categoryId).subCategories.get(i).url);
            bundle.putInt(HardConstants.ADLISTFRAGMENT_ARG_MAXLOADCOUNT,30);
            fr.setArguments(bundle);

            return fr;

        }

        @Override
        public int getCount() {
            return HardConstants.adCategories.get(categoryId).subCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return HardConstants.adCategories.get(categoryId).subCategories.get(position).name;
        }

//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
    }

}

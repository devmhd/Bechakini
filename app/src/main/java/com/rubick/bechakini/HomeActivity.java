package com.rubick.bechakini;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends ActionBarActivity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;

    private ImageView[] editorPicksIvs;
    private TextView[] tvNames;
    private TextView[] tvPrices;

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        initDrawerActions();

        initFlipper();

        initEditorsPicks();

        Fragment fr = new RecentAdListFragment();

        Bundle bundle = new Bundle();

        bundle.putString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL, APISpecs.FETCH_MOST_VIEWED);
        bundle.putInt(HardConstants.ADLISTFRAGMENT_ARG_MAXLOADCOUNT,10);
        fr.setArguments(bundle);
        getSupportFragmentManager()	.beginTransaction()
                .replace(R.id.home_frame_recent_ads,fr)
                .commit();

    }


    void initEditorsPicks(){

        editorPicksIvs = new ImageView[5];
        editorPicksIvs[0] = (ImageView) findViewById(R.id.editor_picks_iv0);
        editorPicksIvs[1] = (ImageView) findViewById(R.id.editor_picks_iv1);
        editorPicksIvs[2] = (ImageView) findViewById(R.id.editor_picks_iv2);
        editorPicksIvs[3] = (ImageView) findViewById(R.id.editor_picks_iv3);
        editorPicksIvs[4] = (ImageView) findViewById(R.id.editor_picks_iv4);

        tvNames = new TextView[5];
        tvNames[0] = (TextView) findViewById(R.id.editor_picks_tvName0);
        tvNames[1] = (TextView) findViewById(R.id.editor_picks_tvName1);
        tvNames[2] = (TextView) findViewById(R.id.editor_picks_tvName2);
        tvNames[3] = (TextView) findViewById(R.id.editor_picks_tvName3);
        tvNames[4] = (TextView) findViewById(R.id.editor_picks_tvName4);

        tvPrices = new TextView[5];
        tvPrices[0] = (TextView) findViewById(R.id.editor_picks_tvPrice0);
        tvPrices[1] = (TextView) findViewById(R.id.editor_picks_tvPrice1);
        tvPrices[2] = (TextView) findViewById(R.id.editor_picks_tvPrice2);
        tvPrices[3] = (TextView) findViewById(R.id.editor_picks_tvPrice3);
        tvPrices[4] = (TextView) findViewById(R.id.editor_picks_tvPrice4);




        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, APISpecs.FETCH_EDITORS_PICKS, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseJson) {
                        try {



                            JSONArray data = responseJson.getJSONArray("data");



                            for(int i = 0; i<data.length(); ++i){



                                tvNames[i].setText(data.getJSONObject(i).getString("title"));
                                tvPrices[i].setText(data.getJSONObject(i).getString("price"));


                                VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(data.getJSONObject(i).getString("image_url"),
                                        ImageLoader.getImageListener(editorPicksIvs[i],
                                                R.drawable.ic_wait, R.drawable.ic_error));

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
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();

                    }

                });

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(jsonRequest);


    }





    void initDrawerActions(){

        ((TextView)findViewById(R.id.drawer_tv_username)).setText(PreferenceStorage.getLoggerName());
        ((TextView)findViewById(R.id.drawer_tv_useremail)).setText(PreferenceStorage.getLoggerEmail());



        RelativeLayout btnNewAd = (RelativeLayout) findViewById(R.id.drawer_btn_newad);
        btnNewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PostAdActivity.class));
            }
        });

        //TODO



        ListView navCategoryList = (ListView) findViewById(R.id.drawer_list_categories);
        navCategoryList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, HardConstants.categoryStringArray));
        navCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
                intent.putExtra("catId",position);
                startActivity(intent);

            }
        });

        ((TextView) findViewById(R.id.drawer_btn_updateProfile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, UpdateProfileActivity.class));

            }
        });


        ((TextView) findViewById(R.id.drawer_btn_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();

            }
        });




    }

    void logout(){

        PreferenceStorage.setLoggedIn(false);
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("showSignUp", false);
        startActivity(intent);
    }


    void initFlipper(){
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        mViewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AdDetailActivity.class));
            }
        });



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}

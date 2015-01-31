package com.rubick.bechakini;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class FilteredAdListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_ad_list);



        Fragment fr = new AdListFragment();
        Bundle bundle = new Bundle();

 //       Log.d("BBBB", "Cat " + HardConstants.adCategories.get(categoryId).name + " Subcat " + HardConstants.adCategories.get(categoryId).subCategories.get(i).name);

        bundle.putString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL, getIntent().getExtras().getString(HardConstants.ADLISTFRAGMENT_ARG_REQUESTURL));
        fr.setArguments(bundle);

        getSupportFragmentManager()	.beginTransaction()
                .replace(R.id.content_frame,fr)
                .commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_filtered_ad_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

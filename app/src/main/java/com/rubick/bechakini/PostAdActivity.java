package com.rubick.bechakini;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class PostAdActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);


        final Spinner spCat, spSubCat;

        spCat = (Spinner) findViewById(R.id.postad_sp_category);
        spSubCat = (Spinner) findViewById(R.id.postad_sp_subcategory);


        spCat.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, HardConstants.categoryStringArray));

        spCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] arr = new String[HardConstants.adCategories.get(position).subCategories.size()];

                for(int i = 0; i< HardConstants.adCategories.get(position).subCategories.size() ; ++i){
                    arr[i] = HardConstants.adCategories.get(position).subCategories.get(i).name;

                }

                spSubCat.setAdapter(new ArrayAdapter<>(PostAdActivity.this, android.R.layout.simple_list_item_1, arr));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_ad, menu);
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

        if (id == R.id.action_ok) {

            startActivity(new Intent(this,UploadPhotoActivity.class));
            return true;
        }

        if (id == R.id.action_cancel) {

            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}

package com.rubick.bechakini;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class UpdateProfileActivity extends ActionBarActivity {

    EditText etName, etEmail, etOldPass, etNewPass, etNewPassConf;
    AutoCompleteTextView autoTvLocation;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        etName = (EditText) findViewById(R.id.update_et_name);
        etEmail = (EditText) findViewById(R.id.update_et_email);
        etOldPass = (EditText) findViewById(R.id.update_et_oldpass);
        etNewPass = (EditText) findViewById(R.id.update_et_newpass);
        etNewPassConf = (EditText) findViewById(R.id.update_et_newpassconf);


        autoTvLocation = (AutoCompleteTextView) findViewById(R.id.update_et_autolocation);

        autoTvLocation.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, HardConstants.areaStringArray));


        loadData();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ok) {
            if(validateInputs()){


                pd = ProgressDialog.show(UpdateProfileActivity.this, "", "Updating...");

                String requestUrl = "http://www.mocky.io/v2/54cbbdb296d6b26f12431f90";
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject responseJson) {
                                try {

                                    if(responseJson.getBoolean("success")){

                                        saveData();
                                        pd.dismiss();

                                        Crouton.makeText(UpdateProfileActivity.this, "Your info has been successfully updated", Style.CONFIRM);


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
                                Toast.makeText(UpdateProfileActivity.this.getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();

                            }

                        });

                VolleySingleton.getInstance(UpdateProfileActivity.this.getApplicationContext()).getRequestQueue().add(jsonRequest);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    void saveData(){
        PreferenceStorage.setLoggerName(etName.getText().toString());
        PreferenceStorage.setLoggerEmail(etEmail.getText().toString());
        PreferenceStorage.setLoggerPassword(etNewPass.getText().toString());
        PreferenceStorage.setLoggerLocation(autoTvLocation.getText().toString());
    }

    void loadData(){
        etName.setText(PreferenceStorage.getLoggerName());
        etEmail.setText(PreferenceStorage.getLoggerEmail());
        autoTvLocation.setText(PreferenceStorage.getLoggerLocation());
    }


    boolean validateInputs(){


        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String oldpass = etOldPass.getText().toString();
        String newPass = etNewPass.getText().toString();
        String newPassConf = etNewPassConf.getText().toString();
        String location = autoTvLocation.getText().toString();

        if(name.isEmpty()){
            Crouton.makeText(this, "Need a name", Style.ALERT).show();
            return false;
        }


        if(email.isEmpty()){
            Crouton.makeText(this, "Need an email address", Style.ALERT).show();
            return false;
        }

        if(location.isEmpty()){
            Crouton.makeText(this, "Need your location", Style.ALERT).show();
            return false;
        }

        if(newPass.isEmpty()){
            Crouton.makeText(this, "Need a password", Style.ALERT).show();
            return false;
        }

        if(!isValidEmail(email)){
            Crouton.makeText(this, "Email not valid", Style.ALERT).show();
            return false;
        }

        if(!newPass.equals(newPassConf)){
            Crouton.makeText(this, "Passwords did not match", Style.ALERT).show();
            return false;
        }

        if(!(PreferenceStorage.getLoggerPassword().equals(oldpass))){
            Crouton.makeText(this, "Old password is incorrect", Style.ALERT).show();
            return false;
        }

        return true;


    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}

package com.rubick.bechakini;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SignUpActivity extends ActionBarActivity {

    EditText etName, etPass, etConfPass, etEmail;
    Button btnSignUp, btnCancel;

    SignUpNetworkTask signUpTask;



    void initGUI(){
        etName = (EditText) findViewById(R.id.etName);
        etPass = (EditText) findViewById(R.id.etPass);
        etConfPass = (EditText) findViewById(R.id.etConfPass);
        etEmail = (EditText) findViewById(R.id.etEmail);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnCancel = (Button) findViewById(R.id.btnCancel);



    }

    boolean validateInputs(){

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initGUI();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                signUpTask = new SignUpNetworkTask();
                signUpTask.execute(name, email, pass);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_register, menu);
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


    private class SignUpNetworkTask extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject searchResponse = null;

            try {

                ArrayList<NameValuePair> postVars = new ArrayList<NameValuePair>();

                postVars.add(new BasicNameValuePair(APISpecs.POST_SIGNUP_NAME, (String)params[0]));
                postVars.add(new BasicNameValuePair(APISpecs.POST_SIGNUP_EMAIL, (String)params[1]));
                postVars.add(new BasicNameValuePair(APISpecs.POST_SIGNUP_PASSWORD, (String)params[2]));


                searchResponse = NetworkTasks.getJsonObject(APISpecs.URL_BASE + APISpecs.SUBMIT_SIGNUP, postVars, true);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return searchResponse;


        }

        protected void onPostExecute(JSONObject searchResponse) {

            Toast.makeText(getApplicationContext(), searchResponse.toString(), Toast.LENGTH_SHORT).show();

        };

    }
}

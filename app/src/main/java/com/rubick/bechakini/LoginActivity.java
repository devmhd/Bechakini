package com.rubick.bechakini;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class LoginActivity extends ActionBarActivity {


    Button btnNewAccount, btnSignin;
    EditText etEmail, etPass;
    ProgressDialog progressDialog;
    LoginNetworkTask loginNetworkTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initGUI();
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    boolean validateInputs(){

           String email = etEmail.getText().toString();
           String pass = etPass.getText().toString();



           if(email.isEmpty()){
               Crouton.makeText(this, "Email needed", Style.ALERT).show();
               return false;
           }

           if(pass.isEmpty()){
               Crouton.makeText(this, "Need a password", Style.ALERT).show();
               return false;
           }

           if(!isValidEmail(email)){
               Crouton.makeText(this, "Email not valid", Style.ALERT).show();
               return false;
           }

           return true;


    }

    void initGUI(){

        btnNewAccount = (Button) findViewById(R.id.btnNewAccount);
        btnSignin = (Button) findViewById(R.id.btnSignIn);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateInputs()){
                    loginNetworkTask = new LoginNetworkTask();
                    loginNetworkTask.execute(
                            etEmail.getText().toString(),
                            etPass.getText().toString()
                    );
                }

            }
        });


        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
   //     getMenuInflater().inflate(R.menu.menu_login, menu);
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


    private class LoginNetworkTask extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject searchResponse = null;

            try {

                ArrayList<NameValuePair> postVars = new ArrayList<NameValuePair>();


                postVars.add(new BasicNameValuePair(APISpecs.POST_LOGIN_EMAIL, (String)params[0]));
                postVars.add(new BasicNameValuePair(APISpecs.POST_LOGIN_PASSWORD, (String)params[1]));


                searchResponse = NetworkTasks.getJsonObject(APISpecs.URL_BASE + APISpecs.SUBMIT_LOGIN, postVars, true);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return searchResponse;


        }

        protected void onPostExecute(JSONObject response) {

            //Toast.makeText(getApplicationContext(), searchResponse.toString(), Toast.LENGTH_SHORT).show();


            progressDialog.dismiss();

            try {
                boolean success = response.getBoolean("success");


                if(success){
                    PreferenceStorage.setLoggerEmail(etEmail.getText().toString());
                    PreferenceStorage.setLoggerPassword(etPass.getText().toString());

                    PreferenceStorage.setLoggedIn(true);

                    startActivity(new Intent(LoginActivity.this, FakeHomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }else{

                    Crouton.makeText(LoginActivity.this, "Incorrect email-password combination", Style.ALERT).show();
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }



        };

    }
}

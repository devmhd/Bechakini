package com.rubick.bechakini;




import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    EditText etName, etPass, etConfPass, etEmail;
    Button btnSignUp, btnAlreadyHaveAcc;

    SignUpNetworkTask signUpTask;

    ProgressDialog progressDialog;

    public SignupFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initGUI();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();


                if(validateInputs()){
                    signUpTask = new SignUpNetworkTask();
                    signUpTask.execute(name, email, pass);

                }

            }
        });

        btnAlreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(R.id.content_frame, new LoginFragment())
                .commit();
            }
        });


    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    boolean validateInputs(){


        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String pass2 = etConfPass.getText().toString();


        if(name.isEmpty()){
            Crouton.makeText(getActivity(), "Need a name", Style.ALERT).show();
            return false;
        }


        if(email.isEmpty()){
            Crouton.makeText(getActivity(), "Need an email address", Style.ALERT).show();
            return false;
        }

        if(pass.isEmpty()){
            Crouton.makeText(getActivity(), "Need a password", Style.ALERT).show();
            return false;
        }

        if(!isValidEmail(email)){
            Crouton.makeText(getActivity(), "Email not valid", Style.ALERT).show();
            return false;
        }

        if(!pass.equals(pass2)){
            Crouton.makeText(getActivity(), "Passwords did not match", Style.ALERT).show();
            return false;
        }

        return true;


    }



    void initGUI(){
        etName = (EditText) getView().findViewById(R.id.etName);
        etPass = (EditText) getView().findViewById(R.id.etPass);
        etConfPass = (EditText) getView().findViewById(R.id.etConfPass);
        etEmail = (EditText) getView().findViewById(R.id.etEmail);

        btnSignUp = (Button) getView().findViewById(R.id.btnSignIn);
        btnAlreadyHaveAcc = (Button) getView().findViewById(R.id.btnAlreadyHaveAcc);



    }



    private class SignUpNetworkTask extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(), "", "Wait a second");
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject searchResponse = null;

            try {

                ArrayList<NameValuePair> postVars = new ArrayList<NameValuePair>();

                postVars.add(new BasicNameValuePair(APISpecs.POST_SIGNUP_NAME,params[0]));
                postVars.add(new BasicNameValuePair(APISpecs.POST_SIGNUP_EMAIL, params[1]));
                postVars.add(new BasicNameValuePair(APISpecs.POST_SIGNUP_PASSWORD, params[2]));


                searchResponse = NetworkTasks.getJsonObject(APISpecs.URL_BASE + APISpecs.SUBMIT_SIGNUP, postVars, true);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return searchResponse;


        }

        protected void onPostExecute(JSONObject searchResponse) {



            progressDialog.dismiss();

            PreferenceStorage.setLoggerEmail(etEmail.getText().toString());
            PreferenceStorage.setLoggerName(etName.getText().toString());
            PreferenceStorage.setLoggerPassword(etPass.getText().toString());

            PreferenceStorage.setLoggedIn(true);

            startActivity(new Intent(getActivity(), DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));


        };

    }
}

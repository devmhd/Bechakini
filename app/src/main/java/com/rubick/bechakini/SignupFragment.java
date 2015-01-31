package com.rubick.bechakini;




import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    EditText etName, etPass, etConfPass, etEmail, etPhone;
    Button btnSignUp, btnAlreadyHaveAcc;



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

                    progressDialog = ProgressDialog.show(getActivity(), "", "Wait a second");

                    String requestUrl = "http://www.mocky.io/v2/54cbbdb296d6b26f12431f90";
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject responseJson) {
                                    try {

                                        if(responseJson.getBoolean("success")){
                                            progressDialog.dismiss();

                                            PreferenceStorage.setLoggerEmail(etEmail.getText().toString());
                                            PreferenceStorage.setLoggerName(etName.getText().toString());
                                            PreferenceStorage.setLoggerPassword(etPass.getText().toString());

                                            PreferenceStorage.setLoggedIn(true);

                                            startActivity(new Intent(getActivity(), DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));



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
                                    Toast.makeText(getActivity().getApplicationContext(), "Network Error" , Toast.LENGTH_LONG).show();

                                }

                            });

                    VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(jsonRequest);



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
        String phone = etPhone.getText().toString();

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

        if(phone.length() > HardConstants.MAX_PHONE_LENGTH ||
                phone.length() < HardConstants.MIN_PHONE_LENGTH){
            Crouton.makeText(getActivity(), "Invalid Phone number", Style.ALERT).show();
            return false;
        }

        return true;


    }



    void initGUI(){
        etName = (EditText) getView().findViewById(R.id.etName);
        etPass = (EditText) getView().findViewById(R.id.etPass);
        etConfPass = (EditText) getView().findViewById(R.id.update_et_oldpass);
        etEmail = (EditText) getView().findViewById(R.id.etEmail);
        etPhone = (EditText) getView().findViewById(R.id.signup_et_phone);

        btnSignUp = (Button) getView().findViewById(R.id.btnSignIn);
        btnAlreadyHaveAcc = (Button) getView().findViewById(R.id.btnAlreadyHaveAcc);



    }


}

package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.dialog.LoginDialog;
import com.google.android.material.button.MaterialButton;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {

    private String email,password;
    private EditText etEmail, etPassword;
    private final String url= "http://dev.rapptrlabs.com/Tests/scripts/login.php";

    private RequestQueue mQueue;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        Button btnSend = findViewById(R.id.btn_send);

        mQueue = Volley.newRequestQueue(this);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volleyRequest();
            }
        });

        //Save user input for rotation
        if(savedInstanceState != null){
            String savedEmail = savedInstanceState.getString(email);
            etEmail.setText(savedEmail);

            String savedPassword = savedInstanceState.getString(password);
            etPassword.setText(savedPassword);
        }


        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }


    //Login Request
    private void volleyRequest(){
        //Instantiate LoginDialog object
        LoginDialog loginDialog=new LoginDialog();
        //Start request
        long mRequestStartTime = System.currentTimeMillis();
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        long requestTime = System.currentTimeMillis() - mRequestStartTime;
                        try {
                            //parse response as JSONObject to easily handle strings
                            JSONObject obj = new JSONObject(response);
                            //Assign response values to corresponding Dialog texts
                            loginDialog.codeD= obj.getString("code");
                            loginDialog.messageD=obj.getString("message")+"\n\nAPI response time: "+requestTime+" milliseconds";
                            //Show dialog
                            loginDialog.show(getSupportFragmentManager(),"Dialog");

                        } catch (Throwable tx) {

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        long requestTime = System.currentTimeMillis() - mRequestStartTime;
                        //Assign response values to corresponding Dialog texts
                        loginDialog.codeD= "ERROR";
                        loginDialog.messageD="Invalid Parameters \n\nAPI response time: "+requestTime+" milliseconds";
                        loginDialog.show(getSupportFragmentManager(),"Dialog");
                    }
                })
        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("email", etEmail.getText().toString());
                params.put("password", etPassword.getText().toString());
                return params;
            }
        };

        mQueue.add(strRequest);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(email, etEmail.getText().toString());
        savedInstanceState.putString(password, etPassword.getText().toString());


        super.onSaveInstanceState(savedInstanceState);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

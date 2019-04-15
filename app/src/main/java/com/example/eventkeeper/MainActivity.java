package com.example.eventkeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    Gson g = new Gson();

    public static final String EXTRA_MESSAGE = "com.example.eventkeeper.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
    }

    public void CreateAccount (View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }


    public void login (View view) {
        EditText loginEmail = findViewById(R.id.login_email);
        final String email = loginEmail.getText().toString();
        EditText loginPassword = findViewById(R.id.login_password);
        final String password = loginPassword.getText().toString();

        final String url = "https://eventkeeperofficial.herokuapp.com/api/signin";


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            Log.d("Response", json.getString("userid"));
                            SharedPreferences sharedPref = getSharedPreferences("eventKeeper", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            editor.putString("userid", json.getString("userid"));
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        valid();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        TextView wrong = findViewById(R.id.wrong);
                        wrong.setText("Wrong email or password");

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);


    }

    public void valid() {
        Intent intent = new Intent(this, nav.class);
        startActivity(intent);
    }

}

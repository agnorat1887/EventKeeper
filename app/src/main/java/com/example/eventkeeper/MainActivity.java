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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

        final String url = "https://eventkeeperofficial.herokuapp.com/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        User loginuser = new User("login", email, password, null, null);

        Call<User> call = jsonPlaceHolderApi.loginUser(loginuser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    TextView wrong = findViewById(R.id.wrong);
                    wrong.setText("Wrong email or password");
                    return;
                }
                User userResponse = response.body();
                System.out.println(userResponse.getUserid());
                SharedPreferences sharedPref = getSharedPreferences("eventKeeper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("userid", userResponse.getUserid());
                editor.commit();

                valid();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                System.out.println("This is in failure");

            }
        });

    }

    public void valid() {
        Intent intent = new Intent(this, nav.class);
        startActivity(intent);
    }

}

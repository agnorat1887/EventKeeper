package com.example.eventkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void Create (View view) throws JSONException {
        if(Checkpass()) {

            String url = "https://eventkeeperofficial.herokuapp.com/api/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            String username = ((EditText)findViewById(R.id.Username)).getText().toString();
            String email = ((EditText)findViewById(R.id.Email)).getText().toString();
            String password = ((EditText)findViewById(R.id.Password)).getText().toString();
            String firstname = ((EditText)findViewById(R.id.FirstName)).getText().toString();
            String lastname = ((EditText)findViewById(R.id.LastName)).getText().toString();
            String street = ((EditText)findViewById(R.id.Street)).getText().toString();
            String city = ((EditText)findViewById(R.id.City)).getText().toString();
            String state = ((EditText)findViewById(R.id.State)).getText().toString();
            int zip_code = Integer.parseInt(((EditText)findViewById(R.id.Zipcode)).getText().toString());

            Fullname fullname= new Fullname(firstname, lastname);
            Address address = new Address(street, city, state, zip_code);

            User user = new User(username, email, password, fullname, address);
            Call<User> call = jsonPlaceHolderApi.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(!response.isSuccessful()){
                        TextView match = findViewById(R.id.match);
                        match.setText("An error occured, try again");
                        return;
                    }
                    User userResponse = response.body();

                    valid();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    System.out.println("Something failed");

                }
            });
    }

    }

    public void valid() {
        System.out.println("Account was created");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean Checkpass () {
        EditText password = findViewById(R.id.Password);
        String pass1 = password.getText().toString();
        EditText password2 = findViewById(R.id.REPassword);
        String pass2 = password2.getText().toString();

        if(pass1.equals(pass2)) {
            return true;
        }
        TextView wrong = findViewById(R.id.match);
        wrong.setText("Passwords must match");
        return false;
    }
}

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void Create (View view) throws JSONException {
        if(Checkpass()) {
            TextView wrong = findViewById(R.id.match);
            wrong.setText("Account is created");

            final String url = "https://eventkeeperofficial.herokuapp.com/api/signup";

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // response
                            Log.d("Response", response.toString());
                            valid();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();

                    EditText username = findViewById(R.id.Username);
                    EditText email = findViewById(R.id.Email);
                    EditText password = findViewById(R.id.Password);
                    params.put("username", username.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());
                    JSONObject name = new JSONObject();
                    EditText firstname = findViewById(R.id.FirstName);
                    EditText lastname = findViewById(R.id.LastName);
                    String First = firstname.getText().toString();
                    String Last = lastname.getText().toString();
                    try {
                        name.put("first", First);
                        name.put("last", Last);
                    }
                     catch (JSONException e) {
                        e.printStackTrace();
                    }
                    params.put("name", name.toString());

                    JSONObject address = new JSONObject();
                    EditText street = findViewById(R.id.Street);
                    EditText city = findViewById(R.id.City);
                    EditText state = findViewById(R.id.State);
                    EditText zipcode = findViewById(R.id.Zipcode);
                    try {
                        address.put("street", street.getText().toString());
                        address.put("city", city.getText().toString());
                        address.put("state", state.getText().toString());
                        address.put("zip_code", zipcode.getText().toString());
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    params.put("address", address.toString());

                    System.out.println(address.toString());
                    return params;
                }
            };
            queue.add(postRequest);
        }

    }

    public void valid() {
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
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

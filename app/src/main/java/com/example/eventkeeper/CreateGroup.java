package com.example.eventkeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateGroup extends Fragment {
    public static CreateGroup newInstance() {
        CreateGroup fragment = new CreateGroup();
        return fragment;
    }

    private EditText mgroupName, mgroupState, mgroupCity, mgroupStreet, mgroupDescription;
    private EditText mgroupZip;

    private String groupName, groupStreet, groupState, groupCity, groupDescription, groupZip;

    private SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_creategroup, null);
        mgroupName = v.findViewById(R.id.enterGroupName);
        mgroupStreet = v.findViewById(R.id.enterGroupStreet);
        mgroupState = v.findViewById(R.id.enterGroupState);
        mgroupCity = v.findViewById(R.id.enterGroupCity);
        mgroupZip = v.findViewById(R.id.enterGroupZip);
        mgroupDescription = v.findViewById(R.id.enterGroupDescription);
        sharedPref = v.getContext().getSharedPreferences("eventKeeper", Context.MODE_PRIVATE);

        Button button = v.findViewById(R.id.CreateGroup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName = mgroupName.getText().toString();
                groupStreet = mgroupStreet.getText().toString();
                groupState = mgroupState.getText().toString();
                groupCity = mgroupCity.getText().toString();
                groupZip = mgroupZip.getText().toString();
                groupDescription = mgroupDescription.getText().toString();

                if(groupName.isEmpty() || groupStreet.isEmpty() || groupState.isEmpty() || groupCity.isEmpty() || groupZip.isEmpty() ||groupDescription.isEmpty()) {
                    Toast.makeText(v.getContext(), "Please Fill all the Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    createGroup();
                }
            }
        });



        return v;
    }


    private void createGroup(){
        String url = "https://eventkeeperofficial.herokuapp.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi  = retrofit.create(JsonPlaceHolderApi.class);
        //replace below with the group you want to create loololololol xd
        System.out.println(groupZip);
        cGroup group = new cGroup(groupName,groupDescription,sharedPref.getString("userid", "default"),new Address(groupStreet,groupCity, groupState, (Integer.parseInt(groupZip))));
        Call<cGroup> call = jsonPlaceHolderApi.createGroup(group);
        call.enqueue(new Callback<cGroup>() {
            @Override
            public void onResponse(Call<cGroup> call, Response<cGroup> response) {
                if (!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<cGroup> call, Throwable t) {

            }
        });



    }

}
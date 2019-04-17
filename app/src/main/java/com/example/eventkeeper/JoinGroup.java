package com.example.eventkeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JoinGroup extends Fragment {

    private String groupID;
    private String Userid;

    private TextView tvgroupName, tvgroupDescription, tvgroupStreet, tvgroupCity, tvgroupState, tvgroupZip, tvJoined;

    private Button button;

    private SharedPreferences sharedPref;

    public JoinGroup() {
        // Required empty public constructor
    }

    public static JoinGroup newInstance() {
        JoinGroup fragment = new JoinGroup();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            groupID = bundle.getString("id");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_join_group, container, false);

        tvgroupName = v.findViewById(R.id.GroupName);
        tvgroupDescription = v.findViewById(R.id.GroupDescription);
        tvgroupStreet = v.findViewById(R.id.groupStreet);
        tvgroupCity = v.findViewById(R.id.groupCity);
        tvgroupState = v.findViewById(R.id.groupState);
        tvgroupZip = v.findViewById(R.id.groupZip);
        tvJoined = v.findViewById(R.id.JoinDisplay);
        button = v.findViewById(R.id.JoinGroupButton);
        sharedPref = v.getContext().getSharedPreferences("eventKeeper", Context.MODE_PRIVATE);

        getOneGroup();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();

            }
        });

        return v;
    }


    public void join() {

        String url = "https://eventkeeperofficial.herokuapp.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Userid = sharedPref.getString("userid", "default");
        userID uid = new userID(Userid);
        Call<Group> call = jsonPlaceHolderApi.joinGroup(groupID, uid);

        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Response code: " + response.code());
                    return;
                }

                tvJoined.setText("You have joined this group succesfully");

            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

            }
        });

    }


    private  void getOneGroup(){
        String url = "https://eventkeeperofficial.herokuapp.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi  = retrofit.create(JsonPlaceHolderApi.class);
        //replace value below with groupid u wanna get lololololo xd hehehehehhe
        Call<Group> call = jsonPlaceHolderApi.getOneGroup(groupID);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(!response.isSuccessful()){
                    System.out.println("Response code: " + response.code());
                    return;
                }
                Group group = response.body();
                tvgroupName.setText(group.getName());
                tvgroupDescription.setText(group.getDescription());
                tvgroupStreet.setText(group.getAddress().getStreet());
                tvgroupCity.setText(group.getAddress().getCity());
                tvgroupState.setText(group.getAddress().getState());
                tvgroupZip.setText(String.valueOf(group.getAddress().getZipCode()));
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

            }
        });
    }

}

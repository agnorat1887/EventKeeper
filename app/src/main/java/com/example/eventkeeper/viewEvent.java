package com.example.eventkeeper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class viewEvent extends Fragment {


    public viewEvent() {
        // Required empty public constructor
    }

    private String eventID;

    private TextView tveventName, tveventDescription, tveventStreet, tveventCity, tveventState, tveventZip, tveventDate;


    public static viewEvent newInstance(String param1, String param2) {
        viewEvent fragment = new viewEvent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            eventID = bundle.getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_event, container, false);

        tveventName = v.findViewById(R.id.eventName);
        tveventDescription = v.findViewById(R.id.eventDescription);
        tveventStreet = v.findViewById(R.id.eventStreet);
        tveventCity = v.findViewById(R.id.eventCity);
        tveventState = v.findViewById(R.id.eventState);
        tveventZip = v.findViewById(R.id.eventZip);
        tveventDate = v.findViewById(R.id.eventDate);

        showEvent();

        return v;
    }

    public void showEvent() {
        String url = "https://eventkeeperofficial.herokuapp.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi  = retrofit.create(JsonPlaceHolderApi.class);
        //replace value below with eventid u wanna get lololololo xd hehehehehhe
        Call<Event> call = jsonPlaceHolderApi.viewEvent(eventID);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                Event event = response.body();

                tveventName.setText(event.getName());
                tveventDescription.setText(event.getDescription());
                tveventStreet.setText(event.getAddress().getStreet());
                tveventCity.setText(event.getAddress().getCity());
                tveventState.setText(event.getAddress().getState());
                tveventZip.setText(String.valueOf(event.getAddress().getZipCode()));
                tveventDate.setText(event.getDate());
            }
            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                System.out.println("You are a failure");
            }
        });
    }

}

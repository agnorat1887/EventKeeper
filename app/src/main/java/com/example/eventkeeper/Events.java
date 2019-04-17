package com.example.eventkeeper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Events extends Fragment implements RecyclerViewAdapter.OnItemClickListener {


    public Events() {
        // Required empty public constructor
    }
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<GroupItem> mArrayList;

    private String groupID;

    public static Events newInstance() {
        Events fragment = new Events();
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

        View v = inflater.inflate(R.layout.fragment_events, container, false);

        mRecyclerView = v.findViewById(R.id.eventRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mArrayList = new ArrayList<>();

        mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mArrayList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        viewEvents();

        return v;
    }

    public void viewEvents() {
        String url = "https://eventkeeperofficial.herokuapp.com/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Group>> call = jsonPlaceHolderApi.showEvents(groupID);

        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, retrofit2.Response<List<Group>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }

                List<Group> groups = response.body();

                for(Group group : groups) {
                    System.out.println("group name: " + group.getName());
                    mArrayList.add(new GroupItem(group.getName(), group.getDescription(), group.get_id()));
                }
                mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mArrayList);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewAdapter.setOnItemClickListener(Events.this);
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                System.out.println("On failure t message: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        GroupItem clickedItem = mArrayList.get(position);
        System.out.println("You clicked something");
        Bundle bundle = new Bundle();
        bundle.putString("id", mArrayList.get(position).getId());
        Fragment fragment = new viewEvent();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("home").commit();
    }

}

package com.example.eventkeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eventkeeper.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends Fragment implements RecyclerViewAdapter.OnItemClickListener {
    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<GroupItem> mArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Button b = (Button) v.findViewById(R.id.IDTest);
        //b.setOnClickListener(this);

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mArrayList = new ArrayList<>();

        mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mArrayList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        Test(v);
        return v;
    }

    public void Test (View view) {
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("eventKeeper", Context.MODE_PRIVATE);
        System.out.println("userid from sharedpref: " + sharedPref.getString("userid", "default"));

        String url = "https://eventkeeperofficial.herokuapp.com/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<List<Group>> call = jsonPlaceHolderApi.showGroups(sharedPref.getString("userid", "default"));

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
                        mRecyclerViewAdapter.setOnItemClickListener(Home.this);
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
        Fragment fragment = new Events();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("home").commit();
    }
}
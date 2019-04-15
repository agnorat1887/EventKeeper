package com.example.eventkeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Home extends Fragment implements RecyclerViewAdapter.OnItemClickListener {
    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<GroupItem> mArrayList;
    private RequestQueue queue;

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

        String url = "https://eventkeeperofficial.herokuapp.com/api/groups/";
        url+=sharedPref.getString("userid", "default");

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i =0; i<response.length(); i++) {
                                JSONObject hit = response.getJSONObject(i);

                                String groupName = hit.getString("name");
                                String groupLocation = hit.getString("description");

                                mArrayList.add(new GroupItem(groupName, groupLocation));
                            }

                            mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mArrayList);
                            mRecyclerView.setAdapter(mRecyclerViewAdapter);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            //mRecyclerViewAdapter.setOnItemClickListener(Home.this, mArrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.response", error.toString());
                    }
                }

                );
        queue.add(getRequest);


    }


    @Override
    public void onItemClick(int position) {
        GroupItem clickedItem = mArrayList.get(position);

    }
}
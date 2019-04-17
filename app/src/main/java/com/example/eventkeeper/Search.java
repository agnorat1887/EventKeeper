package com.example.eventkeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Search extends Fragment implements RecyclerViewAdapter.OnItemClickListener {
    public static Search newInstance() {
        Search fragment = new Search();
        return fragment;
    }
    //variables
    private EditText zipCode;
    private ImageButton searchButton;
    private TextView results;
    private int zipCodeEntered;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<GroupItem> mArrayList;
    private List<Group> groups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, null);
        mRecyclerView = v.findViewById(R.id.searchRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mArrayList = new ArrayList<>();

        mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mArrayList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        zipCode = (EditText) v.findViewById(R.id.etSearch);
        searchButton = (ImageButton) v.findViewById(R.id.imageButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zipCode.getText() == null)
                    return;
                zipCodeEntered = Integer.parseInt(zipCode.getText().toString());
                getAllGroups();

            }
        });





        return v;
    }
    public void getAllGroups (){
        System.out.println("Getting all groups");

        String url = "https://eventkeeperofficial.herokuapp.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi  = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Group>> call = jsonPlaceHolderApi.getAllGroups();
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(!response.isSuccessful()){
                    results.setText("code" +response.code());
                    return;

                }
                groups = response.body();
                for(Group group: groups){
                    if(group.getAddress().getZipCode() == zipCodeEntered) {
                        mArrayList.add(new GroupItem(group.getName(), group.getDescription()));
                    }

                    mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mArrayList);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerViewAdapter.setOnItemClickListener(Search.this);
                }

            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });



    }

    @Override
    public void onItemClick(int position) {
        GroupItem clickedItem = mArrayList.get(position);
        System.out.println("You clicked something");
        Bundle bundle = new Bundle();
        bundle.putString("id", groups.get(position).get_id());
        Fragment fragment = new JoinGroup();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("home").commit();
    }

}
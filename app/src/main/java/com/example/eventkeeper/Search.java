package com.example.eventkeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Search extends Fragment {
    public static Search newInstance() {
        Search fragment = new Search();
        return fragment;
    }
    //variables
    private EditText zipCode;
    private Button btnSearch;
    private TextView results;
    private String zipCodeEntered;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, null);

        zipCode = (EditText) v.findViewById(R.id.etSearch);
        results = (TextView) v.findViewById(R.id.tvresult);
        zipCodeEntered = zipCode.getText().toString();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllGroups();


            }
        });


        return v;
    }
    private void getAllGroups (){

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
                List<Group> groups = response.body();
                for(Group group: groups){
                    String content  = "";
                    content += "Name " + group.getName() + "\n";
                    content += "Description " + group.getName() + "\n";
                    content += "Address :"
                            + group.getAddress().getStreet() +" "
                            + group.getAddress().getCity() + " "
                            + group.getAddress().getState() + " "
                            + group.getAddress().getZipCode() + "\n";


                }

            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });



    }

}
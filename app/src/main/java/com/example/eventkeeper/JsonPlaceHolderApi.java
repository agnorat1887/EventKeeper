package com.example.eventkeeper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {




    @POST("signup")
    Call<User> createUser(@Body User user);

    @POST("signin")
    Call<User> loginUser(@Body User user);

    @GET("groups/{userid}")
    Call<List<Group>> showGroups(@Path("userid") String userid);

    @GET("crowd")
    Call<List<Group>> getAllGroups();
}

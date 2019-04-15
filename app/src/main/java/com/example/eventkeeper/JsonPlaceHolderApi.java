package com.example.eventkeeper;

import android.content.Context;
import android.content.SharedPreferences;

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

    @GET("group/{userid}")
    Call<Group> showGroups(@Body Group group, @Path("userid") String userid);
}

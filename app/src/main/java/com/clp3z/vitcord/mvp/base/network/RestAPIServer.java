package com.clp3z.vitcord.mvp.base.network;

import com.clp3z.vitcord.mvp.model.response.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Clelia López on 6/16/18
 */

public interface RestAPIServer {

    @GET("users")
    Call<List<User>> getUsers(@Query("page") int page, @Query("page_size") int pageSize);

    @GET("me")
    Call<User> getMyProfile();
}

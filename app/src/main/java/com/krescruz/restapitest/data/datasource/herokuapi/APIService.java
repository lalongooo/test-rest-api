package com.krescruz.restapitest.data.datasource.herokuapi;

import com.krescruz.restapitest.data.model.User;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

public interface APIService {

    @GET("/users/")
    Observable<List<User>> getUsers();

    @GET("/users/{id}")
    Observable<User> getUser(@Path("id") int userId);

    @POST("/users/")
    Observable<User> addUser(@Body User user);

    @PUT("/users/{id}")
    Observable<User> updateUser(@Path("id") int userId, @Body User user);

    @DELETE("/users/{id}")
    Observable<Response> deleteUser(@Path("id") int userId);
}
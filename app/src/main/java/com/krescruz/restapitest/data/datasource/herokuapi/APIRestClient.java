package com.krescruz.restapitest.data.datasource.herokuapi;

import android.util.Base64;

import com.krescruz.restapitest.BuildConfig;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class APIRestClient {

    private static final String API_BASE_URL = BuildConfig.API_ENDPOINT;
    private static final String API_REST_USER = BuildConfig.API_USER;
    private static final String API_REST_PASSWORD = BuildConfig.API_PASSWORD;
    private RestAdapter restAdapter;

    public APIRestClient() {

        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setEndpoint(API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String credentials = API_REST_USER + ":" + API_REST_PASSWORD;
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                    }
                });

        restAdapter = builder.build();
    }

    public APIService get() {
        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setEndpoint(API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String credentials = API_REST_USER + ":" + API_REST_PASSWORD;
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                    }
                });

        restAdapter = builder.build();

        return restAdapter.create(APIService.class);
    }
}
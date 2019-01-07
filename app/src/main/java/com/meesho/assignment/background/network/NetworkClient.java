package com.meesho.assignment.background.network;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.meesho.assignment.background.network.api.APIInterface;
import com.meesho.assignment.background.network.interceptors.HeaderInterceptor;
import com.meesho.assignment.background.network.interceptors.QueryInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static APIInterface APIInterface;
    private static final String BASE_URL = "https://api.github.com/";

    public static APIInterface getInstance() {
        if (APIInterface == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new QueryInterceptor());
            httpClient.addInterceptor(new HeaderInterceptor());


            httpClient.addInterceptor(logging);
            Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            APIInterface = retrofit.create(com.meesho.assignment.background.network.api.APIInterface.class);
        }
        return APIInterface;
    }
}
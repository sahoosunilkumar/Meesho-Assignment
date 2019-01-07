package com.meesho.assignment.background.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private static final String VALUE_ACCEPT_FORMAT = "application/vnd.github.v3+json";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("Accept", VALUE_ACCEPT_FORMAT).build();
        return chain.proceed(request);
    }
}

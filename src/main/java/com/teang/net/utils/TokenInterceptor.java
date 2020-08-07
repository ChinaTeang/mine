package com.teang.net.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class TokenInterceptor implements Interceptor {
    private String TAG = "HeaderInterceptor";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authorised = originalRequest.newBuilder()
                .header("token", "token")
                .build();
        return chain.proceed(authorised);
    }
}

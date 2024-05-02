package com.sosoburger.toaster.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    public static Retrofit getInstance() {
        return new Retrofit.
                Builder().
                baseUrl("https://api.imagga.com/v2/")
                .addConverterFactory(JacksonConverterFactory
                        .create()).
                build();

    }
}

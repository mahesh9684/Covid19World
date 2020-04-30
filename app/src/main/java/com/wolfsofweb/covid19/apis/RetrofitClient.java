package com.wolfsofweb.covid19.apis;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    public static final String BASE_URL = "https://api.wolfsofweb.com/v1/";
    //    public static final String BASE_URL = "https://coronavirus-19-api.herokuapp.com/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;


    public static ApiInterface getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        ApiInterface apiService =
                retrofit.create(ApiInterface.class);
        return apiService;
    }

    public static ApiInterface getCountryData(String country) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit1 = new Retrofit.Builder()
                .baseUrl(BASE_URL + "countries/" + country + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiInterface apiService =
                retrofit1.create(ApiInterface.class);
        return apiService;
    }
}

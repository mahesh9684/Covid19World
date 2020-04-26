package com.info.covid19.apis;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    String get = "india";
    /*@GET("/user_login")
    Call<JSONObject> loginUser(@Query("name") String name,
                                  @Query("contact_phone") String phone,
                                  @Query("country_code") String countryCode,
                                  @Query("password") String password,
                                  @Query("email") String email,
                                  @Query("device_token") String device_token);*/

//    @POST("Login")
//    Call<LoginResponseModel> loginUser(@Body LoginRequestModel requestModel);

    @GET("all.php")
    Call<JsonObject> getCovidData();

    @GET("usa.php")
    Call<JsonObject> getStateData();
}

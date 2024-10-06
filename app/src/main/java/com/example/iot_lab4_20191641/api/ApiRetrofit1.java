package com.example.iot_lab4_20191641.api;

import com.example.iot_lab4_20191641.dto.Ligas;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRetrofit1 {

    @GET("api/v1/json/3/all_leagues.php")
    Call<Ligas> getAllLeagues();

}

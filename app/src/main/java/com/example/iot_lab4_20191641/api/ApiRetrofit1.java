package com.example.iot_lab4_20191641.api;

import com.example.iot_lab4_20191641.dto.Ligas;
import com.example.iot_lab4_20191641.dto.LigasParticular;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRetrofit1 {

    @GET("api/v1/json/3/all_leagues.php")
    Call<Ligas> getAllLeagues();

    @GET("api/v1/json/3/search_all_leagues.php")
    Call<Ligas> getLeaguesByCountry(@Query("c") String country); //acá debería ser Call<LigasParticular>

}

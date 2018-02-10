package ru.divizdev.coinrate.rates;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICoinRateApi {

    @GET("v1/ticker/")
    Call<List<ru.divizdev.coinrate.Entities.CoinRateApi>> getData(@Query("limit") int limit);

    @GET("v1/ticker/")
    Call<List<ru.divizdev.coinrate.Entities.CoinRateApi>> getData(@Query("start") int start, @Query("limit") int limit);

    @GET("v1/ticker/")
    Call<List<ru.divizdev.coinrate.Entities.CoinRateApi>> getData(@Query("start") int start, @Query("limit") int limit, @Query("convert") String convert);


}

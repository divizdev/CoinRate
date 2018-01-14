package ru.divizdev.coinrate.BL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.divizdev.coinrate.Entities.CoinRate;

import java.util.List;

public interface CoinRateApi {

    @GET("v1/ticker/")
    Call<List<CoinRate>> getData(@Query("limit") int limit);

    @GET("v1/ticker/")
    Call<List<CoinRate>> getData(@Query("start") int start, @Query("limit") int limit);

    @GET("v1/ticker/")
    Call<List<CoinRate>> getData(@Query("start") int start, @Query("limit") int limit, @Query("convert") String convert);

}

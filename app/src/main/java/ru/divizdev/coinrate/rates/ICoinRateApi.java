package ru.divizdev.coinrate.rates;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.Entities.api.ApiData;

public interface ICoinRateApi {


    /**
     * @param start   integer >= 1
     *                Optionally offset the start (1-based index) of the paginated list of items to return.
     * @param limit   integer [ 1 .. 5000 ]
     *                Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
     * @param convert string
     *                "USD"
     *                Optionally calculate market quotes in up to 32 currencies at once by passing
     *                a comma-separated list of cryptocurrency or fiat currency symbols.
     *                Each additional convert option beyond the first requires an additional
     *                call credit. A list of supported fiat options can  be found here.
     *                Each conversion is returned in its own "quote" object.
     * @return
     */
    @Headers("X-CMC_PRO_API_KEY: " + BuildConfig.API2_KEY)
    @GET("v1/cryptocurrency/listings/latest")
    Call<ApiData> getRate(@Query("start") int start, @Query("limit") int limit, @Query("convert") String convert);

    @Headers("X-CMC_PRO_API_KEY: " + BuildConfig.API2_KEY)
    @GET("v1/cryptocurrency/listings/latest")
    Call<ApiData> getRate(@Query("start") int start, @Query("limit") int limit);

    @Headers("X-CMC_PRO_API_KEY: " + BuildConfig.API2_KEY)
    @GET("v1/cryptocurrency/listings/latest")
    Call<ApiData> getRate();

}

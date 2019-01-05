package ru.divizdev.coinrate.data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.entities.api.ApiData;

public class CoinRateApiRepository implements  CoinRateRepository {
    @Override
    public void loadListCoinRate(String currency, LoadCoinRateCallback callback) {
        Retrofit retrofitV2 = new Retrofit.Builder()
                .baseUrl(BuildConfig.API2_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        ICoinRateApi apiV2 = retrofitV2.create(ICoinRateApi.class);

        apiV2.getRate(1, 100, currency).enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (response.body() != null) {
                    callback.onNotesLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                callback.onErrorLoaded("error");
            }
        });
    }
}

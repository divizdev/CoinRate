package ru.divizdev.coinrate.data;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.data.entities.ApiData;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import timber.log.Timber;

public class CoinRateApiRepository implements CoinRateRepository {
    private ICoinRateApi _apiV2;

    public CoinRateApiRepository() {
        OkHttpClient client = getClient();

        Retrofit retrofitV2 = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.API2_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        _apiV2 = retrofitV2.create(ICoinRateApi.class);
    }

    @Override
    public void loadListCoinRate(String currency, boolean isForced, LoadCoinRateCallback callback) {
        _apiV2.getRate(1, 100, currency).enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(@NonNull Call<ApiData> call, @NonNull Response<ApiData> response) {
                if (response.body() != null) {
                    callback.onNotesLoaded(CoinRateUI.convertList(response.body(), currency));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData> call, @NonNull Throwable t) {
                callback.onErrorLoaded("error");
            }
        });
    }

    @NonNull
    private OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        if(BuildConfig.DEBUG) {
            logging.setLevel(Level.BODY);
        }else{
            logging.setLevel(Level.BASIC);
        }
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }
}

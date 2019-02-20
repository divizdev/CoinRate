package ru.divizdev.coinrate.di;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.data.ManagerSettings;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;
import ru.divizdev.coinrate.data.RestClient;
import ru.divizdev.coinrate.data.RxRepository;
import ru.divizdev.coinrate.data.RxRepositoryApi;
import ru.divizdev.coinrate.data.RxRepositoryCache;
import ru.divizdev.coinrate.presentation.Router;
import timber.log.Timber;

public class Factory {
    private static Factory INSTANCE;

    private ManagerSettings _managerSettings;
    private Router _router;
    private RxRepositoryCache _rxRepositoryCache;

    private Factory(Context context) {
        _managerSettings = new PreferenceManagerSettings(context);
        _router = new Router();
        _rxRepositoryCache = new RxRepositoryCache();
    }

    public static Factory getFactory() {
        return INSTANCE;
    }

    public static void create(Context context) {
        INSTANCE = new Factory(context);
    }

    public Router getRouter() {
        return _router;
    }

    public ManagerSettings getManagerSettings() {
        return _managerSettings;
    }

    public RxRepository getRxRepository() {
        return new RxRepositoryApi(getClient(), _rxRepositoryCache);
    }

    public RestClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        if (BuildConfig.DEBUG) {
            logging.setLevel(Level.BODY);
        } else {
            logging.setLevel(Level.BASIC);
        }

        OkHttpClient okHttpClient = new Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofitV2 = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.API2_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofitV2.create(RestClient.class);
    }
}

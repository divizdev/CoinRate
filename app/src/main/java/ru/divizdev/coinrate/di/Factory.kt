package ru.divizdev.coinrate.di

import android.content.Context
import ru.divizdev.coinrate.data.ManagerSettings
import ru.divizdev.coinrate.presentation.Router
import ru.divizdev.coinrate.data.RxRepositoryCache
import ru.divizdev.coinrate.data.PreferenceManagerSettings
import ru.divizdev.coinrate.data.RxRepository
import ru.divizdev.coinrate.data.RxRepositoryApi
import ru.divizdev.coinrate.data.RestClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.divizdev.coinrate.BuildConfig
import ru.divizdev.coinrate.utils.Config

class Factory private constructor(context: Context) {
    val managerSettings: ManagerSettings
    val router: Router
    private val _rxRepositoryCache: RxRepositoryCache
    val config: Config

    init {
        config = Config()
        managerSettings = PreferenceManagerSettings(context)
        router = Router()
        _rxRepositoryCache = RxRepositoryCache()
    }

    val rxRepository: RxRepository
        get() = RxRepositoryApi(client, _rxRepositoryCache)

    //Базовая часть адреса
    //Конвертер, необходимый для преобразования JSON'а в объекты
    val client: RestClient
        get() {
            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                     Timber.tag("OkHttp").d(message)
                }
            })
            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            }
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            val retrofitV2 = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(config.baseUrl) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofitV2.create(RestClient::class.java)
        }

    companion object {
        var factory: Factory? = null
            private set

        @JvmStatic
        fun create(context: Context) {
            factory = Factory(context)
        }
    }
}
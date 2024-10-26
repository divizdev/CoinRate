package ru.divizdev.coinrate.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.divizdev.coinrate.BuildConfig
import ru.divizdev.coinrate.data.*
import ru.divizdev.coinrate.presentation.Router
import ru.divizdev.coinrate.presentation.coinRateList.viewModel.ListViewModel
import ru.divizdev.coinrate.presentation.detail.viewModel.DetailViewModel
import ru.divizdev.coinrate.utils.Config
import timber.log.Timber

val appModule = module {
    single<ManagerSettings>(createdAtStart = true) {
        PreferenceManagerSettings(get())
    }
    single(createdAtStart = true) {
        RxRepositoryCache()
    }
    single(createdAtStart = true) {
        Config()
    }

    single(createdAtStart = true) {
        Router()
    }

    viewModelOf(::ListViewModel)
    viewModelOf(::DetailViewModel)

    factory<RestClient> {
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
            .baseUrl(Factory.config.baseUrl) //Базовая часть адреса
            .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofitV2.create(RestClient::class.java)
    }

    single<RxRepository> { RxRepositoryApi(get(), get()) }


}

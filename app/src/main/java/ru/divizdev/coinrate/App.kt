package ru.divizdev.coinrate

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.divizdev.coinrate.di.appModule
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by diviz on 29.01.2018.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}
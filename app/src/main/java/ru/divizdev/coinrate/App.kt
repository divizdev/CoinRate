package ru.divizdev.coinrate

import android.app.Application
import ru.divizdev.coinrate.di.Factory.Companion.create
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by diviz on 29.01.2018.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        create(applicationContext)
        Timber.plant(DebugTree())
    }
}
package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.di.Factory;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Factory.create(getApplicationContext());
    }


}

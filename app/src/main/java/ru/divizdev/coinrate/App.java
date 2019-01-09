package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.di.Factory;
import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailInteraction;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {


    private static CoinRateDetailInteraction _coinRateDetailInteraction = new CoinRateDetailInteraction();



    public static CoinRateDetailInteraction getCoiCoinRateDetailInteraction() {
        return _coinRateDetailInteraction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Factory.create(getApplicationContext());
    }


}

package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.BL.CoinRateListInteractor;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {

    private static CoinRateListInteractor _coinRateListPresenter;

    @Override
    public void onCreate() {
        super.onCreate();

        _coinRateListPresenter = new CoinRateListInteractor( getBaseContext() );
    }

    public static CoinRateListInteractor getCoinRateListPresenter() {
        return _coinRateListPresenter;
    }
}

package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.BL.CoinRateListPresenter;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {

    private static CoinRateListPresenter _coinRateListPresenter = new CoinRateListPresenter();

    
    
    public static CoinRateListPresenter getCoinRateListPresenter() {
        return _coinRateListPresenter;
    }
}

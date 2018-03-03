package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.rates.CoinRateListInteractor;
import ru.divizdev.coinrate.rates.IManagerSettings;
import ru.divizdev.coinrate.rates.PreferenceManagerSettings;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {

    private static CoinRateListInteractor _coinRateListPresenter;
    private static IManagerSettings _managerSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        _managerSettings = new PreferenceManagerSettings(getApplicationContext());
        _coinRateListPresenter = new CoinRateListInteractor( _managerSettings );
    }

    public static CoinRateListInteractor getCoinRateListPresenter() {
        return _coinRateListPresenter;
    }
}

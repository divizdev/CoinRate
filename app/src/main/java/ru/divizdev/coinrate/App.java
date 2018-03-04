package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.rates.CoinRateListInteraction;
import ru.divizdev.coinrate.rates.IManagerSettings;
import ru.divizdev.coinrate.rates.PreferenceManagerSettings;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {

    private static CoinRateListInteraction _coinRateListPresenter;
    private static IManagerSettings _managerSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        _managerSettings = new PreferenceManagerSettings(getApplicationContext());
        _coinRateListPresenter = new CoinRateListInteraction( _managerSettings );
    }

    public static CoinRateListInteraction getCoinRateListPresenter() {
        return _coinRateListPresenter;
    }
}

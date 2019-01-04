package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.data.IManagerSettings;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;
import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailInteraction;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {


    private static CoinRateDetailInteraction _coinRateDetailInteraction = new CoinRateDetailInteraction();
    private static IManagerSettings _managerSettings;


    public static CoinRateDetailInteraction getCoiCoinRateDetailInteraction() {
        return _coinRateDetailInteraction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _managerSettings = new PreferenceManagerSettings(getApplicationContext());
    }

    public static IManagerSettings getManagerSettings() {
        return _managerSettings;
    }
}

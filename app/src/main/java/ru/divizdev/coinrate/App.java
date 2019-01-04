package ru.divizdev.coinrate;

import android.app.Application;

import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailInteraction;
import ru.divizdev.coinrate.presentation.listCoins.presenter.CoinRateListInteraction;
import ru.divizdev.coinrate.data.IManagerSettings;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;

/**
 * Created by diviz on 29.01.2018.
 */

public class App extends Application {

    private static CoinRateListInteraction _coinRateListPresenter;
    private static CoinRateDetailInteraction _coiCoinRateDetailInteraction = new CoinRateDetailInteraction();
    private static IManagerSettings _managerSettings;

    public static CoinRateListInteraction getCoinRateListPresenter() {
        return _coinRateListPresenter;
    }

    public static CoinRateDetailInteraction getCoiCoinRateDetailInteraction() {
        return _coiCoinRateDetailInteraction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _managerSettings = new PreferenceManagerSettings(getApplicationContext());
        _coinRateListPresenter = new CoinRateListInteraction(_managerSettings);
    }


}

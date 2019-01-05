package ru.divizdev.coinrate.di;

import android.content.Context;

import ru.divizdev.coinrate.data.CoinRateApiRepository;
import ru.divizdev.coinrate.data.CoinRateRepository;
import ru.divizdev.coinrate.data.ManagerSettings;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;
import ru.divizdev.coinrate.presentation.Router;

public class Factory {
    private static Factory INSTANCE;

    private ManagerSettings _managerSettings;
    private Router _router;
    private CoinRateRepository _coinRateRepository;

    public Router getRouter() {
        return _router;
    }

    private Factory(Context context) {
        _managerSettings = new PreferenceManagerSettings(context);
        _router = new Router();
        _coinRateRepository = new CoinRateApiRepository();
    }

    public CoinRateRepository getCoinRateRepository() {
        return _coinRateRepository;
    }

    public ManagerSettings getManagerSettings() {
        return _managerSettings;
    }

    public static Factory getFactory() {
        return INSTANCE;
    }

    public static void create(Context context) {
        INSTANCE = new Factory(context);
    }
}

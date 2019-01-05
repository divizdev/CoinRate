package ru.divizdev.coinrate.di;

import android.content.Context;

import ru.divizdev.coinrate.data.IManagerSettings;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;
import ru.divizdev.coinrate.presentation.Router;

public class Factory {
    private static Factory INSTANCE;

    private IManagerSettings _managerSettings;
    private Router _router;

    public Router getRouter() {
        return _router;
    }

    private Factory(Context context) {
        _managerSettings = new PreferenceManagerSettings(context);
        _router = new Router();
    }

    public IManagerSettings getManagerSettings() {
        return _managerSettings;
    }

    public static Factory getFactory() {
        return INSTANCE;
    }

    public static void create(Context context) {
        INSTANCE = new Factory(context);
    }
}

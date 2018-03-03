package ru.divizdev.coinrate.rates;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

/**
 * Created by diviz on 03.03.2018.
 */

public class PreferenceManagerSettings implements IManagerSettings {

    public static final String KEY_NAME_PREF = "pref_currency";

    private final Context _context;

    public PreferenceManagerSettings(Context context){
        _context = context;

    }
    @Override
    public String getCurCurrency() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        return preferences.getString(PreferenceManagerSettings.KEY_NAME_PREF, "USD");
    }

    @Override
    public void setCurCurrency(String currency) {
        android.preference.PreferenceManager.getDefaultSharedPreferences(_context).edit().putString(PreferenceManagerSettings.KEY_NAME_PREF, currency).apply();

    }

}
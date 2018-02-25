package ru.divizdev.coinrate.rates;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.Entities.CoinRate;
import ru.divizdev.coinrate.Entities.CoinRateApi;
import ru.divizdev.coinrate.ui.DialogSettings;

/**
 * Created by diviz on 29.01.2018.
 */

public class CoinRateListInteractor {

    private ICoinRateListView _ICoinRateListView;
    private String _curCurrency = "";

    private Context _context;

    private Map<String, List<CoinRate>> _coinRateModel = new HashMap<>();

    public CoinRateListInteractor(Context context) {
        _context = context;
        _curCurrency = getCurrencySettings();
    }

    private String getCurrencySettings() {
        //TODO: Extract constants
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        return preferences.getString("pref_currency", "USD");
    }

    public void attache(ICoinRateListView ICoinRateListView) {
        _ICoinRateListView = ICoinRateListView;
        showList(_curCurrency);
    }

    public void detach(){
        _ICoinRateListView = null;
    }

    private void showList(String curCurrency) {
        List<CoinRate> list = _coinRateModel.get(curCurrency);

        if (list != null) {
            _ICoinRateListView.showCoinRateList(list);
            _curCurrency = curCurrency;
            android.preference.PreferenceManager.getDefaultSharedPreferences(_context).edit().putString(DialogSettings.KEY_NAME_PREF, _curCurrency).apply();
        } else {
            loadCoinRate(curCurrency);
        }
    }

    public void refresh() {

        loadCoinRate(_curCurrency);

    }

    private void loadCoinRate(final String currency) {

        _ICoinRateListView.showLoadingProgress(true);
        //TODO: Extract constants
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        ICoinRateApi api = retrofit.create(ICoinRateApi.class);

        api.getData(0, 100, currency).enqueue(new Callback<List<ru.divizdev.coinrate.Entities.CoinRateApi>>() {
            @Override
            public void onResponse(@NonNull Call<List<ru.divizdev.coinrate.Entities.CoinRateApi>> call, @NonNull Response<List<ru.divizdev.coinrate.Entities.CoinRateApi>> response) {
                if (response.body() != null) {
                    List<CoinRate> coinRates = CoinRate.convertList(response.body(), currency);
                    _coinRateModel.put(currency, coinRates);//TODO: multi thread not lock

                    _ICoinRateListView.showLoadingProgress(false);
                    _ICoinRateListView.showCoinRateList(coinRates);

                    _curCurrency = currency;
                    android.preference.PreferenceManager.getDefaultSharedPreferences(_context).edit().putString(DialogSettings.KEY_NAME_PREF, _curCurrency).apply();
                }
            }

            @Override
            public void onFailure(Call<List<CoinRateApi>> call, Throwable t) {
                _ICoinRateListView.showLoadingProgress(false);
                _ICoinRateListView.showErrorLoading();
                Log.e("test", "Fail");
            }
        });

    }

    public void setCurrency(String currency) {
        if (_curCurrency.compareTo(currency) != 0) {
            showList(currency);
        }

    }


    public interface ICoinRateListView {
        void showLoadingProgress(Boolean isView);

        void showCoinRateList(List<CoinRate> list);

        void showErrorLoading();
    }
}

package ru.divizdev.coinrate.rates;

import android.support.annotation.NonNull;
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

/**
 * Created by diviz on 29.01.2018.
 */

public class CoinRateListInteraction {

    private ICoinRateListView _ICoinRateListView = NullRateListView.getInstance();
    private String _curCurrency = "";


    private Map<String, List<CoinRate>> _coinRateModel = new HashMap<>();
    private final IManagerSettings _managerSettings;

    public CoinRateListInteraction(IManagerSettings managerSettings) {
        _managerSettings = managerSettings;
        _curCurrency = getCurrencySettings();
    }

    private String getCurrencySettings() {
        return _managerSettings.getCurCurrency();
    }

    public void attache(ICoinRateListView ICoinRateListView) {
        _ICoinRateListView = ICoinRateListView;
        _curCurrency = getCurrencySettings();
        showList(_curCurrency);
    }

    public void detach() {
        _ICoinRateListView = NullRateListView.getInstance();
    }

    private void showList(String curCurrency) {
        List<CoinRate> list = _coinRateModel.get(curCurrency);

        if (list != null) {
            _curCurrency = curCurrency;
            _managerSettings.setCurCurrency(curCurrency);
            _ICoinRateListView.showCoinRateList(list);
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
                    _managerSettings.setCurCurrency(currency);
                    _curCurrency = currency;

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

    private static class NullRateListView implements ICoinRateListView {

        private static NullRateListView _nullRateListView = new NullRateListView();

        private NullRateListView() {

        }

        static NullRateListView getInstance() {
            return _nullRateListView;
        }

        @Override
        public void showLoadingProgress(Boolean isView) {

        }

        @Override
        public void showCoinRateList(List<CoinRate> list) {

        }

        @Override
        public void showErrorLoading() {

        }
    }
}

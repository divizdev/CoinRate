package ru.divizdev.coinrate.rates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.Entities.CoinRateUI;
import ru.divizdev.coinrate.Entities.v2.ApiData;

/**
 * Created by diviz on 29.01.2018.
 */

public class CoinRateListInteraction {

    private ICoinRateListView _ICoinRateListView = NullRateListView.getInstance();
    private String _curCurrency = "";


    private Map<String, List<CoinRateUI>> _coinRateModel = new HashMap<>();
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
        List<CoinRateUI> list = _coinRateModel.get(curCurrency);

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


        Retrofit retrofitV2 = new Retrofit.Builder()
                .baseUrl(BuildConfig.API2_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        ICoinRateApi apiV2 = retrofitV2.create(ICoinRateApi.class);

        apiV2.getRate(1, 100, currency).enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (response.body() != null) {
                    List<CoinRateUI> coinRateUI = CoinRateUI.convertList(response.body(), currency);
                    _coinRateModel.put(currency, coinRateUI);//TODO: multi thread not lock
                    _ICoinRateListView.showLoadingProgress(false);
                    _ICoinRateListView.showCoinRateList(coinRateUI);
                    _managerSettings.setCurCurrency(currency);
                    _curCurrency = currency;
                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                _ICoinRateListView.showLoadingProgress(false);
                _ICoinRateListView.showErrorLoading();

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

        void showCoinRateList(List<CoinRateUI> list);

        void showErrorLoading();

        void showDialogAbout();

        void showDialogSettings();
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
        public void showCoinRateList(List<CoinRateUI> list) {

        }

        @Override
        public void showErrorLoading() {

        }

        @Override
        public void showDialogAbout() {

        }

        @Override
        public void showDialogSettings() {

        }
    }
}

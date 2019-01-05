package ru.divizdev.coinrate.presentation.listCoins.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.data.ICoinRateApi;
import ru.divizdev.coinrate.data.IManagerSettings;
import ru.divizdev.coinrate.entities.CoinRateUI;
import ru.divizdev.coinrate.entities.api.ApiData;
import ru.divizdev.coinrate.utils.EspressoIdlingResource;

/**
 * Created by diviz on 29.01.2018.
 */
@InjectViewState
public class CoinRateListPresenter extends MvpPresenter<CoinRateListView> {

    private final IManagerSettings _managerSettings;
    private String _curCurrency;
    private Map<String, List<CoinRateUI>> _coinRateModel = new HashMap<>();

    public CoinRateListPresenter(IManagerSettings managerSettings) {
        _managerSettings = managerSettings;
        _curCurrency = getCurrencySettings();
    }

    private String getCurrencySettings() {
        return _managerSettings.getCurCurrency();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        _curCurrency = getCurrencySettings();
        showList(_curCurrency);
    }

    private void showList(String curCurrency) {
        List<CoinRateUI> list = _coinRateModel.get(curCurrency);

        if (list != null) {
            _curCurrency = curCurrency;
            _managerSettings.setCurCurrency(curCurrency);
            getViewState().showCoinRateList(list);
        } else {
            loadCoinRate(curCurrency);
        }
    }

    public void refresh() {

        loadCoinRate(_curCurrency);

    }

    private void loadCoinRate(final String currency) {

        EspressoIdlingResource.increment();
        getViewState().showLoadingProgress(true);


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
                    getViewState().showLoadingProgress(false);
                    getViewState().showCoinRateList(coinRateUI);
                    _managerSettings.setCurCurrency(currency);
                    _curCurrency = currency;
                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                getViewState().showLoadingProgress(false);
                getViewState().showErrorLoading();
                EspressoIdlingResource.decrement();
            }
        });

    }

    public void setCurrency(String currency) {
        if (_curCurrency.compareTo(currency) != 0) {
            showList(currency);
        }

    }

    public void clickToItem(CoinRateUI coinRateUI) {
        getViewState().navToDetail(coinRateUI);
    }
}

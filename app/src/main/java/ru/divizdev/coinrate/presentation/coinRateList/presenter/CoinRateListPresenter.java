package ru.divizdev.coinrate.presentation.coinRateList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.divizdev.coinrate.data.CoinRateRepository;
import ru.divizdev.coinrate.data.CoinRateRepository.LoadCoinRateCallback;
import ru.divizdev.coinrate.data.ManagerSettings;
import ru.divizdev.coinrate.entities.CoinRateUI;
import ru.divizdev.coinrate.entities.api.ApiData;
import ru.divizdev.coinrate.utils.EspressoIdlingResource;

/**
 * Created by diviz on 29.01.2018.
 */
@InjectViewState
public class CoinRateListPresenter extends MvpPresenter<CoinRateListView> {

    private final ManagerSettings _managerSettings;
    private String _curCurrency;
    private Map<String, List<CoinRateUI>> _coinRateModel = new HashMap<>();
    private CoinRateRepository _repository;

    public CoinRateListPresenter(CoinRateRepository repository, ManagerSettings managerSettings) {
        _managerSettings = managerSettings;
        _curCurrency = getCurrencySettings();
        _repository = repository;
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

        _repository.loadListCoinRate(currency, new LoadCoinRateCallback() {
            @Override
            public void onNotesLoaded(ApiData apiData) {
                List<CoinRateUI> coinRateUI = CoinRateUI.convertList(apiData, currency);
                _coinRateModel.put(currency, coinRateUI);//TODO: multi thread not lock
                getViewState().showLoadingProgress(false);
                getViewState().showCoinRateList(coinRateUI);
                _managerSettings.setCurCurrency(currency);
                _curCurrency = currency;
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onErrorLoaded(String message) {
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

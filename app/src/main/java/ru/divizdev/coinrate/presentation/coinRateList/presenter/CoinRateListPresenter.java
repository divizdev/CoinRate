package ru.divizdev.coinrate.presentation.coinRateList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.divizdev.coinrate.data.CoinRateRepository;
import ru.divizdev.coinrate.data.CoinRateRepository.LoadCoinRateCallback;
import ru.divizdev.coinrate.data.ManagerSettings;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import ru.divizdev.coinrate.utils.EspressoIdlingResource;

/**
 * Created by diviz on 29.01.2018.
 */
@InjectViewState
public class CoinRateListPresenter extends MvpPresenter<CoinRateListView> {

    private final ManagerSettings _managerSettings;
    private String _curCurrency;
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
        loadCoinRate(curCurrency, false);
    }

    public void refresh() {
        loadCoinRate(_curCurrency, true);
    }

    private void loadCoinRate(final String currency, boolean isForce) {

        EspressoIdlingResource.increment();
        getViewState().showLoadingProgress(true);

        _repository.loadListCoinRate(currency, isForce, new LoadCoinRateCallback() {
            @Override
            public void onNotesLoaded(List<CoinRateUI> coinRateUI) {
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

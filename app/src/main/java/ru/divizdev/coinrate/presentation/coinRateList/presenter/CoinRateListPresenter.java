package ru.divizdev.coinrate.presentation.coinRateList.presenter;


import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.divizdev.coinrate.data.ManagerSettings;
import ru.divizdev.coinrate.data.RxRepository;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import ru.divizdev.coinrate.utils.EspressoIdlingResource;
import timber.log.Timber;

/**
 * Created by diviz on 29.01.2018.
 */
@InjectViewState
public class CoinRateListPresenter extends MvpPresenter<CoinRateListView> {

    private final ManagerSettings _managerSettings;
    private RxRepository _rxRepository;
    private String _curCurrency;
    private CompositeDisposable _disposable = new CompositeDisposable();

    public CoinRateListPresenter(ManagerSettings managerSettings, RxRepository rxRepository) {
        _managerSettings = managerSettings;
        _rxRepository = rxRepository;
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
        loadCoinRate(curCurrency, false);
    }

    public void refresh() {
        loadCoinRate(_curCurrency, true);
    }

    private void loadCoinRate(final String currency, boolean isForce) {
        Timber.d("start load coin rate");

        EspressoIdlingResource.increment();
        getViewState().showLoadingProgress(true);

        _disposable.add(_rxRepository.getData(currency, isForce).subscribe(list -> {
            Timber.d("finish load coin rate");
            getViewState().showLoadingProgress(false);
            getViewState().showCoinRateList(list);
            _managerSettings.setCurCurrency(currency);
            _curCurrency = currency;
        }, throwable -> {
            Timber.e("Error load coin rate");
            Timber.e(throwable);
            getViewState().showLoadingProgress(false);
            getViewState().showErrorLoading();
            EspressoIdlingResource.decrement();
        }, EspressoIdlingResource::decrement));
    }

    public void setCurrency(String currency) {
        if (_curCurrency.compareTo(currency) != 0) {
            showList(currency);
        }

    }

    public void clickToItem(CoinRateUI coinRateUI) {
        getViewState().navToDetail(coinRateUI);
    }

    @Override
    public void onDestroy() {
        _disposable.dispose();
        super.onDestroy();
    }
}

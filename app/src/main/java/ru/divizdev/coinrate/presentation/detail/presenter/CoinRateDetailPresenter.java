package ru.divizdev.coinrate.presentation.detail.presenter;


import java.math.BigDecimal;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import ru.divizdev.coinrate.utils.LocaleUtils;

/**
 * Created by diviz on 22.03.2018.
 */
@InjectViewState
public class CoinRateDetailPresenter extends MvpPresenter<CoinRateDetailView> {

    private CoinRateUI _coinRateUI;
    private boolean _direct = true; //Symbol => Currency

    public CoinRateDetailPresenter(CoinRateUI coinRateUI) {
        _coinRateUI = coinRateUI;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        _direct = true;

        setCurrency(_direct);
        getViewState().setData(_coinRateUI);
    }

    private void setCurrency(boolean direct) {
        if (direct) {
            getViewState().setCurrencyFrom(_coinRateUI.getSymbol());
            getViewState().setCurrencyTo(_coinRateUI.getCurrency());
        } else {
            getViewState().setCurrencyFrom(_coinRateUI.getCurrency());
            getViewState().setCurrencyTo(_coinRateUI.getSymbol());
        }
    }

    public void changeCurrency() {
        _direct = !_direct;
        setCurrency(_direct);
        getViewState().clearAll();
    }

    public void convertCurrency(String value) {
        try {
            float valueF = Float.parseFloat(value);
            float result;
            if (_direct) {

                result = valueF * _coinRateUI.getPrice().floatValue();

            } else {

                result = valueF / _coinRateUI.getPrice().floatValue();

            }

            getViewState().setValueTo(LocaleUtils.formatBigDecimal(new BigDecimal(result)));

        } catch (Exception e) {
            getViewState().showError("Error convert");
        }
    }


}

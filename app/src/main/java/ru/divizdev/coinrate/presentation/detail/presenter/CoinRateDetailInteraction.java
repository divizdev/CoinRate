package ru.divizdev.coinrate.presentation.detail.presenter;

import java.math.BigDecimal;

import ru.divizdev.coinrate.entities.CoinRateUI;
import ru.divizdev.coinrate.utils.LocaleUtils;

/**
 * Created by diviz on 22.03.2018.
 */

public class CoinRateDetailInteraction implements ICoinRateDetailInteraction {

    private ICoinRateDetailView _view;
    private CoinRateUI _coinRateUI;
    private boolean _direct = true; //Symbol => Currency


    public interface ICoinRateDetailView {

        void setCurrencyFrom(String currencyFrom);

        void setCurrencyTo(String currencyTo);

        void setValue(String value);

        void clearAll();

        void showError(String error);

    }



    @Override
    public void attache(ICoinRateDetailView view, CoinRateUI coinRate) {
        _view = view;
        _coinRateUI = coinRate;
        _direct = true;

        setCurrency(_direct);

    }

    private void setCurrency(boolean direct) {
        if (direct) {
            _view.setCurrencyFrom(_coinRateUI.getSymbol());
            _view.setCurrencyTo(_coinRateUI.getCurrency());
        } else {
            _view.setCurrencyFrom(_coinRateUI.getCurrency());
            _view.setCurrencyTo(_coinRateUI.getSymbol());
        }
    }

    @Override
    public void detache() {
        _view = null;
    }

    @Override
    public void changeCurrency() {

        _direct = !_direct;
        setCurrency(_direct);
        _view.clearAll();

    }

    @Override
    public void convertCurrency(String value) {
        try {
            float valueF = Float.parseFloat(value);
            float result;
            if (_direct) {

                result = valueF * _coinRateUI.getPrice().floatValue();

            } else {

                result = valueF / _coinRateUI.getPrice().floatValue();

            }

            _view.setValue(LocaleUtils.formatBigDecimal(new BigDecimal(result)));

        } catch (Exception e) {
            _view.showError("Error convert");
        }
    }


}

package ru.divizdev.coinrate.rates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import ru.divizdev.coinrate.entities.CoinRateUI;
import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailInteraction;

/**
 * Created by diviz on 25.03.2018.
 */
public class CoinRateDetailInteractionTest {

    private CoinRateDetailInteraction _interaction;
    private StubView _view;
    private CoinRateUI _coinRateUI;

    private class StubView implements CoinRateDetailInteraction.ICoinRateDetailView{

        String _currencyFrom;
        String _currencyTo;
        String _value;
        String _error;


        @Override
        public void setCurrencyFrom(String currencyFrom) {
            _currencyFrom = currencyFrom;
        }

        @Override
        public void setCurrencyTo(String currencyTo) {
            _currencyTo = currencyTo;
        }

        @Override
        public void setValue(String value) {
            _value = value;
        }

        @Override
        public void clearAll() {

        }

        @Override
        public void showError(String error) {
            _error = error;
        }
    }

    @Before
    public void setUp() throws Exception {
        _interaction = new CoinRateDetailInteraction();
        _view = new StubView();
        _coinRateUI = new CoinRateUI("USD","BTC", "Bitcoin", "BTC", 0,
                                     new BigDecimal(100),BigDecimal.ZERO,  1,
                                     2, 7, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                                     0,  BigDecimal.ZERO);


        _interaction.attache(_view, _coinRateUI);
    }

    @Test
    public void convertCurrencyDirectInteger() throws Exception {

        _interaction.convertCurrency("1");

        Assert.assertEquals("100,00", _view._value);

    }

    @Test
    public void convertCurrencyReverseInteger() throws Exception {
        _interaction.changeCurrency();
        _interaction.convertCurrency("100");
        Assert.assertEquals("1,00", _view._value);

        _interaction.convertCurrency("1000");
        Assert.assertEquals("10,00", _view._value);



    }

}
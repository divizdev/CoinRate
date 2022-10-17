package ru.divizdev.coinrate.presentation.detail.presenter;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

public class CoinRateDetailPresenterTest {


    CoinRateUI _coinRateUI = new CoinRateUI(
            "RUB", "BTC", "BTC", "BTC",
            10, BigDecimal.valueOf(100), BigDecimal.ZERO, 0, 0, 0,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            10, BigDecimal.ZERO);


    @Mock
    CoinRateDetailView _detailView;

    @Mock
    CoinRateDetailView$$State _view$$State;

    CoinRateDetailPresenter _presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        _presenter = new CoinRateDetailPresenter(_coinRateUI);
        _presenter.attachView(_detailView);
        _presenter.setViewState(_view$$State);
//        when(_coinRateUI.getPrice()).thenReturn(BigDecimal.valueOf(100));
//        when(_coinRateUI.getSymbol()).thenReturn("BTC");
//        when(_coinRateUI.getCurrency()).thenReturn("RUB");
    }

    @Test
    public void convertSimpleCurrency() {
        _presenter.convertCurrency("1");
        verify(_view$$State).setValueTo(argThat(argument -> argument.contains("100")));
    }

    @Test
    public void convertUndersideCurrency() {
        _presenter.changeCurrency();
        verify(_view$$State).setCurrencyFrom("RUB");
        verify(_view$$State).setCurrencyTo("BTC");
        _presenter.convertCurrency("100");

        verify(_view$$State).setValueTo(argThat(argument -> argument.contains("1")));

    }
}
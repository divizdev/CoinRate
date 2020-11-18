package ru.divizdev.coinrate.presentation.detail.presenter;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CoinRateDetailView extends MvpView {

    void setCurrencyFrom(String currencyFrom);

    void setCurrencyTo(String currencyTo);

    void setValueTo(String value);

    void clearAll();

    void showError(String error);

    void setData(CoinRateUI coinRateUI);

}

package ru.divizdev.coinrate.presentation.detail.presenter;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.divizdev.coinrate.entities.CoinRateUI;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CoinRateDetailView extends MvpView {

    void setCurrencyFrom(String currencyFrom);

    void setCurrencyTo(String currencyTo);

    void setValueTo(String value);

    void clearAll();

    void showError(String error);

    void setData(CoinRateUI coinRateUI);

}

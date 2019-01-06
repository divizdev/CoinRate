package ru.divizdev.coinrate.presentation.detail.presenter;

import ru.divizdev.coinrate.entities.CoinRateUI;

/**
 * Created by diviz on 24.03.2018.
 */

public interface ICoinRateDetailInteraction {
    void attache(CoinRateDetailInteraction.ICoinRateDetailView view, CoinRateUI coinRate);

    void detache();

    void changeCurrency();

    void convertCurrency(String value);
}

package ru.divizdev.coinrate.rates;

import ru.divizdev.coinrate.Entities.CoinRateUI;

/**
 * Created by diviz on 24.03.2018.
 */

public interface ICoinRateDetailInteraction {
    void attache(CoinRateDetailInteraction.ICoinRateDetailView view, CoinRateUI coinRate);

    void detache();

    void changeCurrency();

    void convertCurrency(String value);
}

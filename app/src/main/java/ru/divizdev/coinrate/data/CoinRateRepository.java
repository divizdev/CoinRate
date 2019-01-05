package ru.divizdev.coinrate.data;

import ru.divizdev.coinrate.entities.api.ApiData;

public interface CoinRateRepository {

    interface LoadCoinRateCallback {
        void onNotesLoaded(ApiData apiData);
        void onErrorLoaded(String message);
    }

    void loadListCoinRate(String currency, LoadCoinRateCallback callback);
}

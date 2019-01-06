package ru.divizdev.coinrate.data;

import java.util.List;

import ru.divizdev.coinrate.entities.CoinRateUI;

public interface CoinRateRepository {

    interface LoadCoinRateCallback {
        void onNotesLoaded(List<CoinRateUI> list);
        void onErrorLoaded(String message);
    }

    void loadListCoinRate(String currency, boolean isForced, LoadCoinRateCallback callback);
}

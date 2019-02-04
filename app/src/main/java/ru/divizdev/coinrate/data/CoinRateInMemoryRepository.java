package ru.divizdev.coinrate.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

public class CoinRateInMemoryRepository implements CoinRateRepository {

    private CoinRateRepository _repository;
    private Map<String, List<CoinRateUI>> _cache = new HashMap<>();

    public CoinRateInMemoryRepository(CoinRateRepository repositoryApi) {
        _repository = repositoryApi;
    }

    @Override
    public void loadListCoinRate(String currency, boolean isForced, LoadCoinRateCallback callback) {
        if (isForced) {
            _cache.remove(currency);
        }
        if (_cache.containsKey(currency)) {
            callback.onNotesLoaded(_cache.get(currency));
        }else {
            _repository.loadListCoinRate(currency, isForced, new LoadCoinRateCallback() {
                @Override
                public void onNotesLoaded(List<CoinRateUI> list) {
                    _cache.put(currency, list);
                    callback.onNotesLoaded(list);
                }

                @Override
                public void onErrorLoaded(String message) {
                    callback.onErrorLoaded(message);
                }
            });
        }
    }
}

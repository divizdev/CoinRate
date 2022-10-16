package ru.divizdev.coinrate.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

public class RxRepositoryCache implements RxRepository {

    private Map<String, List<CoinRateUI>> _cache = new HashMap<>();

    public void setCache(String currency, List<CoinRateUI> list) {
        _cache.put(currency, list);
    }

    @Override
    public Observable<List<CoinRateUI>> getData(String currency, boolean isForced) {
        return Observable.create(emitter -> {
            if(_cache.containsKey(currency)) {
                emitter.onNext(_cache.get(currency));
                emitter.onComplete();
            }else{
                emitter.onComplete();
            }
        });
    }
}

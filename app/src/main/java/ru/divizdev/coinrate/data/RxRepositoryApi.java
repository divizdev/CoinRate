package ru.divizdev.coinrate.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

public class RxRepositoryApi implements RxRepository {

    private RestClient _client;
    private RxRepositoryCache _rxRepositoryCache;

    public RxRepositoryApi(RestClient client, RxRepositoryCache rxRepositoryCache) {
        _client = client;
        _rxRepositoryCache = rxRepositoryCache;
    }

    @Override
    public Observable<List<CoinRateUI>> getData(String currency, boolean isForced) {
        if (isForced) {
            return getListApi(currency)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return Observable.concat(_rxRepositoryCache.getData(currency, false), getListApi(currency))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    private Observable<List<CoinRateUI>> getListApi(String currency) {
        return _client.getRxRate(1, 100, currency).map(apiData -> CoinRateUI.convertList(apiData, currency))
                .doOnNext(list -> _rxRepositoryCache.setCache(currency, list))
                ;
    }
}

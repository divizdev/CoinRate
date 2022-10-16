package ru.divizdev.coinrate.data

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.coinrate.data.entities.ApiData
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

class RxRepositoryApi(private val _client: RestClient, private val _rxRepositoryCache: RxRepositoryCache) : RxRepository {
    override fun getData(currency: String, isForced: Boolean): Observable<List<CoinRateUI>> {
        return if (isForced) {
            getListApi(currency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        } else {
            Observable.concat(
                _rxRepositoryCache.getData(currency, false),
                getListApi(currency)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getListApi(currency: String): Observable<List<CoinRateUI>> {
        return _client.getRxRate(1, 100, currency)
            .map { apiData: ApiData ->
                CoinRateUI.convertList(
                    apiData, currency
                )
            }
            .doOnNext { list: List<CoinRateUI> -> _rxRepositoryCache.setCache(currency, list) }
    }
}
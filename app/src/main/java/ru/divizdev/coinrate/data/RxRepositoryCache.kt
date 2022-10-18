package ru.divizdev.coinrate.data

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

class RxRepositoryCache : RxRepository {
    private val _cache: MutableMap<String, List<CoinRateUI>> = HashMap()
    fun setCache(currency: String, list: List<CoinRateUI>) {
        _cache[currency] = list
    }

    override fun getData(currency: String, isForced: Boolean): Observable<List<CoinRateUI>> {
        return Observable.create { emitter: ObservableEmitter<List<CoinRateUI>> ->
            if (_cache.containsKey(currency)) {
                emitter.onNext(_cache[currency]!!)
                emitter.onComplete()
            } else {
                emitter.onComplete()
            }
        }
    }
}
package ru.divizdev.coinrate.data

import io.reactivex.Observable
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

interface RxRepository {
    fun getData(currency: String, isForced: Boolean): Observable<List<CoinRateUI>>
}
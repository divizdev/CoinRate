package ru.divizdev.coinrate.presentation.detail.presenter

import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.MvpView
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

@StateStrategyType(AddToEndSingleStrategy::class)
interface CoinRateDetailView : MvpView {
    fun setCurrencyFrom(currencyFrom: String?)
    fun setCurrencyTo(currencyTo: String?)
    fun setValueTo(value: String?)
    fun clearAll()
    fun showError(error: String?)
    fun setData(coinRateUI: CoinRateUI?)
}
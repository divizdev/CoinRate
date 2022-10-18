package ru.divizdev.coinrate.presentation.coinRateList.presenter

import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.MvpView
import ru.divizdev.coinrate.presentation.entities.CoinRateUI
import moxy.viewstate.strategy.OneExecutionStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface CoinRateListView : MvpView {
    fun showLoadingProgress(isView: Boolean?)
    fun showCoinRateList(list: List<CoinRateUI?>?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorLoading()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDialogAbout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navToDetail(coinRateUI: CoinRateUI?)
}
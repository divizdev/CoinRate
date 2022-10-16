package ru.divizdev.coinrate.presentation.coinRateList.presenter

import moxy.InjectViewState
import ru.divizdev.coinrate.data.ManagerSettings
import ru.divizdev.coinrate.data.RxRepository
import moxy.MvpPresenter
import ru.divizdev.coinrate.presentation.coinRateList.presenter.CoinRateListView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import ru.divizdev.coinrate.utils.EspressoIdlingResource
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

/**
 * Created by diviz on 29.01.2018.
 */
@InjectViewState
class CoinRateListPresenter(private val _managerSettings: ManagerSettings, private val _rxRepository: RxRepository) :
    MvpPresenter<CoinRateListView?>() {
    private var _curCurrency: String
    private val _disposable = CompositeDisposable()

    init {
        _curCurrency = currencySettings
    }

    private val currencySettings: String
        private get() = _managerSettings.curCurrency

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        _curCurrency = currencySettings
        showList(_curCurrency)
    }

    private fun showList(curCurrency: String) {
        loadCoinRate(curCurrency, false)
    }

    fun refresh() {
        loadCoinRate(_curCurrency, true)
    }

    private fun loadCoinRate(currency: String, isForce: Boolean) {
        Timber.d("start load coin rate")
        EspressoIdlingResource.increment()
        viewState!!.showLoadingProgress(true)
        _disposable.add(_rxRepository.getData(currency, isForce).subscribe({ list: List<CoinRateUI?>? ->
            Timber.d("finish load coin rate")
            viewState!!.showLoadingProgress(false)
            viewState!!.showCoinRateList(list)
            _managerSettings.curCurrency = currency
            _curCurrency = currency
        }, { throwable: Throwable? ->
            Timber.e("Error load coin rate")
            Timber.e(throwable)
            viewState!!.showLoadingProgress(false)
            viewState!!.showErrorLoading()
            EspressoIdlingResource.decrement()
        }) { EspressoIdlingResource.decrement() })
    }

    fun setCurrency(currency: String) {
        if (_curCurrency.compareTo(currency) != 0) {
            showList(currency)
        }
    }

    fun clickToItem(coinRateUI: CoinRateUI?) {
        viewState!!.navToDetail(coinRateUI)
    }

    override fun onDestroy() {
        _disposable.dispose()
        super.onDestroy()
    }
}
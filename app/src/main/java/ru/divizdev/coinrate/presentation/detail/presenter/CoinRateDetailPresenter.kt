package ru.divizdev.coinrate.presentation.detail.presenter

import moxy.InjectViewState
import ru.divizdev.coinrate.presentation.entities.CoinRateUI
import moxy.MvpPresenter
import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailView
import ru.divizdev.coinrate.utils.LocaleUtils
import java.lang.Exception
import java.math.BigDecimal

/**
 * Created by diviz on 22.03.2018.
 */
@InjectViewState
class CoinRateDetailPresenter(private val _coinRateUI: CoinRateUI) : MvpPresenter<CoinRateDetailView?>() {
    private var _direct = true //Symbol => Currency
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        _direct = true
        setCurrency(_direct)
        viewState!!.setData(_coinRateUI)
    }

    private fun setCurrency(direct: Boolean) {
        if (direct) {
            viewState!!.setCurrencyFrom(_coinRateUI.symbol)
            viewState!!.setCurrencyTo(_coinRateUI.currency)
        } else {
            viewState!!.setCurrencyFrom(_coinRateUI.currency)
            viewState!!.setCurrencyTo(_coinRateUI.symbol)
        }
    }

    fun changeCurrency() {
        _direct = !_direct
        setCurrency(_direct)
        viewState!!.clearAll()
    }

    fun convertCurrency(value: String) {
        try {
            val valueF = value.toFloat()
            val result: Float
            result = if (_direct) {
                valueF * (_coinRateUI.price?.toFloat() ?: 1f)
            } else {
                valueF / (_coinRateUI.price?.toFloat() ?: 1f)
            }
            viewState!!.setValueTo(LocaleUtils.formatBigDecimal(BigDecimal(result.toDouble())))
        } catch (e: Exception) {
            viewState!!.showError("Error convert")
        }
    }
}
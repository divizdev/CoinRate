package ru.divizdev.coinrate.presentation.detail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.divizdev.coinrate.presentation.detail.ui.DetailFragment.Companion.ARG_COIN_RATE
import ru.divizdev.coinrate.presentation.entities.CoinRateUI
import ru.divizdev.coinrate.utils.LocaleUtils
import java.math.BigDecimal

class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _coinRateUI: CoinRateUI = savedStateHandle.get<CoinRateUI>(ARG_COIN_RATE) ?: throw Exception("Panic")

    private var _direct = true //Symbol => Currency

    private val _viewState = MutableLiveData(DetailViewState(_coinRateUI.symbol ?: "", _coinRateUI.currency ?: "", ""))
    val viewState: LiveData<DetailViewState>
        get() = _viewState


    private fun setCurrency(direct: Boolean) {
        if (direct) {
            _viewState.value = DetailViewState(_coinRateUI.symbol ?: "", _coinRateUI.currency ?: "", _viewState.value?.value ?: "")
        } else {
            _viewState.value = DetailViewState(_coinRateUI.currency ?: "", _coinRateUI.symbol ?: "", _viewState.value?.value ?: "")
        }
    }

    fun changeCurrency() {
        _direct = !_direct
        _viewState.value = _viewState.value?.copy(value = "")
        setCurrency(_direct)
    }

    fun convertCurrency(value: String) {
        try {
            val valueF = value.toFloat()
            val result: Float = if (_direct) {
                valueF * (_coinRateUI.price?.toFloat() ?: 1f)
            } else {
                valueF / (_coinRateUI.price?.toFloat() ?: 1f)
            }
            _viewState.value = _viewState.value?.copy(value = LocaleUtils.formatBigDecimal(BigDecimal(result.toDouble())), isError = false)
        } catch (e: Exception) {
            _viewState.value = _viewState.value?.copy(value = "", isError = true)
        }
    }
}

data class DetailViewState(val from: String, val to: String, val value: String, val isError: Boolean = false)

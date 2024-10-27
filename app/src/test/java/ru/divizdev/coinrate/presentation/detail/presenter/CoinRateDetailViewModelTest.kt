package ru.divizdev.coinrate.presentation.detail.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.divizdev.coinrate.presentation.detail.ui.DetailFragment.Companion.ARG_COIN_RATE
import ru.divizdev.coinrate.presentation.detail.viewModel.DetailViewModel
import ru.divizdev.coinrate.presentation.entities.CoinRateUI
import java.math.BigDecimal

class CoinRateDetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    var _coinRateUI: CoinRateUI = CoinRateUI(
        "RUB", "BTC", "BTC", "BTC",
        10, BigDecimal.valueOf(100), BigDecimal.ZERO, 0.0, 0.0, 0.0,
        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
        10, BigDecimal.ZERO
    )

    private lateinit var _detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        val savedStateHandle = SavedStateHandle().apply {
            set(ARG_COIN_RATE, _coinRateUI)
        }
        _detailViewModel = DetailViewModel(savedStateHandle)
    }

    @Test
    fun convertSimpleCurrency() {
        _detailViewModel.convertCurrency("1")
        val result = _detailViewModel.viewState.value?.value ?: ""
        assertEquals("100,00", result)
    }

    @Test
    fun convertUndersideCurrency() {
        _detailViewModel.changeCurrency()
        val resultTo = _detailViewModel.viewState.value?.to
        assertEquals("BTC", resultTo)

        _detailViewModel.convertCurrency("100")
        val resultValue = _detailViewModel.viewState.value?.value
        assertEquals("1,00", resultValue)
    }
}

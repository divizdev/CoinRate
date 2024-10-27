package ru.divizdev.coinrate.presentation.coinRateList.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.divizdev.coinrate.data.ManagerSettings
import ru.divizdev.coinrate.data.RxRepository
import ru.divizdev.coinrate.presentation.coinRateList.viewModel.ListViewModel
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

class CoinRateListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    var coinRateRepository: RxRepository? = null

    @Mock
    lateinit var managerSettings: ManagerSettings

    private lateinit var viewModel: ListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(managerSettings.curCurrency).thenReturn("USD")

        `when`(coinRateRepository?.getData("USD", true))
            .thenReturn(Observable.create { emitter: ObservableEmitter<List<CoinRateUI>> ->
                emitter.onNext(
                    ArrayList()
                )
            })
        `when`(coinRateRepository?.getData("RUB", false))
            .thenReturn(Observable.create { emitter: ObservableEmitter<List<CoinRateUI>> ->
                emitter.onNext(
                    ArrayList()
                )
            })
        viewModel = ListViewModel(managerSettings, coinRateRepository!!)
    }

    @Test
    fun setCurrency() {
        viewModel.setCurrency("RUB")
        Mockito.verify(managerSettings).curCurrency = "RUB"
        //        verify(_coinRateListView).showLoadingProgress(true);
    }
}

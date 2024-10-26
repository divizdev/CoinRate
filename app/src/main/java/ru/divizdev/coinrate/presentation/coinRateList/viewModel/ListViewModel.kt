package ru.divizdev.coinrate.presentation.coinRateList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.divizdev.coinrate.data.ManagerSettings
import ru.divizdev.coinrate.data.RxRepository
import ru.divizdev.coinrate.presentation.Router
import ru.divizdev.coinrate.presentation.entities.CoinRateUI
import ru.divizdev.coinrate.presentation.entities.Event
import ru.divizdev.coinrate.utils.EspressoIdlingResource
import timber.log.Timber

class ListViewModel(
    private val _managerSettings: ManagerSettings,
    private val _rxRepository: RxRepository,
) : ViewModel() {

    private var _curCurrency: String
    private val _disposable = CompositeDisposable()

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _event = MutableLiveData<Event<Router.Screens>>()
    val event: LiveData<Event<Router.Screens>>
        get() = _event

    init {
        _curCurrency = currencySettings
        refresh()
    }

    private val currencySettings: String
        private get() = _managerSettings.curCurrency

    private fun showList(curCurrency: String = _curCurrency) {
        loadCoinRate(curCurrency, false)
    }

    fun refresh() {
        loadCoinRate(_curCurrency, true)
    }

    private fun loadCoinRate(currency: String, isForce: Boolean) {
        Timber.d("start load coin rate")
        EspressoIdlingResource.increment()
        _viewState.value = ViewState.LoadingProgress
        _disposable.add(_rxRepository.getData(currency, isForce).subscribe({ list: List<CoinRateUI?>? ->
            Timber.d("finish load coin rate")
            if (list == null) {
                _viewState.value = ViewState.ShowCoinRateList(emptyList())
            } else {
                _viewState.value = ViewState.ShowCoinRateList(list.filterNotNull())
            }
            _managerSettings.curCurrency = currency
            _curCurrency = currency
        }, { throwable: Throwable? ->
            Timber.e("Error load coin rate")
            Timber.e(throwable)
            _viewState.value = ViewState.ErrorLoading
            EspressoIdlingResource.decrement()
        }) { EspressoIdlingResource.decrement() })
    }

    fun setCurrency(currency: String) {
        if (_curCurrency.compareTo(currency) != 0) {
            showList(currency)
        }
    }

    fun clickToItem(coinRateUI: CoinRateUI) {
        _event.value = Event(Router.Screens.Detail(coinRateUI))
    }

    override fun onCleared() {
        super.onCleared()
        _disposable.dispose()
    }
}


sealed interface ViewState {

    data class ShowCoinRateList(val list: List<CoinRateUI>) : ViewState
    data object LoadingProgress : ViewState
    data object ErrorLoading : ViewState

}

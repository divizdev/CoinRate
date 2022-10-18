package ru.divizdev.coinrate.presentation.detail.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailPresenter
import ru.divizdev.coinrate.presentation.detail.presenter.CoinRateDetailView
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

class DetailFragment : MvpAppCompatFragment(), CoinRateDetailView {
    @JvmField
    @InjectPresenter
    var _interaction: CoinRateDetailPresenter? = null
    private var _detailNameCoin: TextView? = null
    private var _detailAvailableSupply: TextView? = null
    private var _detailMarketCap //detail_max_supply
            : TextView? = null
    private var _detail24hVolume //detail_total_supply
            : TextView? = null
    private var _detailSymbolCoin: TextView? = null
    private var _logo: ImageView? = null
    private var _nameCurrencyFrom: TextView? = null
    private var _nameCurrencyTo: TextView? = null
    private var _valueFrom: EditText? = null
    private var _valueTo: TextView? = null

    //TODO: null
    @get:ProvidePresenter
    val interaction: CoinRateDetailPresenter
        get() = CoinRateDetailPresenter(requireArguments().getParcelable(ARG_COIN_RATE)!!) //TODO: null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_detail, container, false)
        bind(result)
        return result
    }

    override fun onResume() {
        super.onResume()
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Detail")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "DetailFragment")
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private fun bind(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar)
        if (activity != null) {
            (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
            val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true)
                supportActionBar.setDisplayShowHomeEnabled(true)
            }
        }
        _detailAvailableSupply = view.findViewById(R.id.detail_available_supply)
        _detailNameCoin = view.findViewById(R.id.detail_name_coin)
        _detailMarketCap = view.findViewById(R.id.detail_market_cap)
        _detail24hVolume = view.findViewById(R.id.detail_24h_volume)
        _logo = view.findViewById(R.id.detail_image_view)
        _detailSymbolCoin = view.findViewById(R.id.detail_symbol_coin)
        _nameCurrencyFrom = view.findViewById(R.id.name_currency_from)
        _nameCurrencyTo = view.findViewById(R.id.name_currency_to)
        val buttonChangeCurrency = view.findViewById<Button>(R.id.button_change_currency)
        val buttonConvert = view.findViewById<Button>(R.id.button_convert)
        _valueFrom = view.findViewById(R.id.value_from)
        _valueTo = view.findViewById(R.id.value_to)
        _valueFrom?.setOnKeyListener { _: View?, _: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_UP) {
                _interaction!!.convertCurrency(_valueFrom?.getText().toString())
            }
            false
        }
        buttonConvert.setOnClickListener { v: View? -> _interaction!!.convertCurrency(_valueFrom?.text.toString()) }
        buttonChangeCurrency.setOnClickListener { v: View? -> _interaction!!.changeCurrency() }
    }

    override fun setData(coinRateUI: CoinRateUI?) {
        _detailNameCoin!!.text = coinRateUI!!.name
        _detail24hVolume!!.text = String.format("%s %s", coinRateUI.uiVolume24, coinRateUI.uiCurrency)
        _detailMarketCap!!.text = String.format("%s %s", coinRateUI.uiMarketCap, coinRateUI.uiCurrency)
        _detailAvailableSupply!!.text = String.format("%s %s", coinRateUI.uiAvailableSupply, coinRateUI.symbol)
        _detailSymbolCoin!!.text = coinRateUI.symbol
        Picasso.get()
            .load(coinRateUI.urlImage)
            .error(R.drawable.unknown_currency)
            .into(_logo)
    }

    override fun setCurrencyFrom(currencyFrom: String?) {
        _nameCurrencyFrom!!.text = currencyFrom
    }

    override fun setCurrencyTo(currencyTo: String?) {
        _nameCurrencyTo!!.text = currencyTo
    }

    override fun setValueTo(value: String?) {
        _valueTo!!.text = value
    }

    override fun clearAll() {
        _valueFrom!!.setText("")
        _valueTo!!.text = ""
    }

    override fun showError(error: String?) {}

    companion object {
        private const val ARG_COIN_RATE = "coin_rate"

        @JvmStatic
        fun newInstance(coinRateUI: CoinRateUI?): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_COIN_RATE, coinRateUI)
            fragment.arguments = args
            return fragment
        }
    }
}
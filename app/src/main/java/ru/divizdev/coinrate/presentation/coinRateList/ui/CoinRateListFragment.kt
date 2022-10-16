package ru.divizdev.coinrate.presentation.coinRateList.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.di.Factory.Companion.factory
import ru.divizdev.coinrate.presentation.about.AboutDialog
import ru.divizdev.coinrate.presentation.coinRateList.presenter.CoinRateListPresenter
import ru.divizdev.coinrate.presentation.coinRateList.presenter.CoinRateListView
import ru.divizdev.coinrate.presentation.coinRateList.ui.SettingsDialog.INoticeDialogListener
import ru.divizdev.coinrate.presentation.entities.CoinRateUI
import ru.divizdev.coinrate.utils.LocaleUtils
import java.util.*

/**
 * Created by diviz on 26.01.2018.
 */
class CoinRateListFragment : MvpAppCompatFragment(), CoinRateListView, OnRefreshListener, INoticeDialogListener {
    @JvmField
    @InjectPresenter
    var _presenter: CoinRateListPresenter? = null
    private var _recyclerView: RecyclerView? = null
    private val _list: MutableList<CoinRateUI?> = ArrayList()
    private var _swipeRefreshLayout: SwipeRefreshLayout? = null
    private var _progressBar: ProgressBar? = null

    @ProvidePresenter
    fun provideCoinRateListPresenter(): CoinRateListPresenter {
        return CoinRateListPresenter(factory!!.managerSettings, factory!!.rxRepository)
    }

    //region LifeCycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coin_rate, container, false)
        _recyclerView = view.findViewById(R.id.coin_rate_list)
        val linearLayoutManager = LinearLayoutManager(view.context)
        _recyclerView?.layoutManager = linearLayoutManager
        val coinRateAdapter =
            CoinRateAdapter(
                object : IFragmentInteractionListener {
                    override fun onClickItemCoinRate(coinRateUI: CoinRateUI?) {
                        _presenter!!.clickToItem(coinRateUI)
                    }
                }, _list)
        _recyclerView?.adapter = coinRateAdapter
        _recyclerView?.addItemDecoration(DividerItemDecoration(_recyclerView?.context, linearLayoutManager.orientation))
        _swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        _swipeRefreshLayout?.setOnRefreshListener(this)
        _progressBar = view.findViewById(R.id.progress_bar)
        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar)
        if (activity != null) {
            (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
            val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(false)
                supportActionBar.setDisplayShowHomeEnabled(false)
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CoinRateListFragment")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CoinRateListFragment")
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    //endregion LifeCycle
    //region Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> showDialogSettings()
            R.id.about -> showDialogAbout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun showDialogSettings() {
        val fragmentManager = requireActivity().supportFragmentManager
        val settingsDialog = SettingsDialog()
        settingsDialog.setTargetFragment(this, 0)
        settingsDialog.show(fragmentManager, "")
    }

    override fun showDialogAbout() {
        val fragmentManager = requireActivity().supportFragmentManager
        val aboutDialog = AboutDialog()
        aboutDialog.show(fragmentManager, "")
    }

    override fun navToDetail(coinRateUI: CoinRateUI?) {
        if (activity != null) {
            factory!!.router.navToDetail((activity as AppCompatActivity?)!!, coinRateUI)
        }
    }

    //endregion Menu
    //region CoinRateListView
    override fun showLoadingProgress(isView: Boolean?) {
        if (isView!!) {
            if (!_swipeRefreshLayout!!.isRefreshing) {
                _progressBar!!.visibility = View.VISIBLE
            }
        } else {
            _progressBar!!.visibility = View.GONE
            _swipeRefreshLayout!!.isRefreshing = false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showCoinRateList(list: List<CoinRateUI?>?) {
        _list.clear()
        _list.addAll(list!!)
        _recyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun showErrorLoading() {
        Toast.makeText(context, "ErrorLoad", Toast.LENGTH_SHORT).show()
    }

    //endregion
    override fun onRefresh() {
        _presenter?.refresh()
    }

    override fun onDialogSettingsSelectedItem(currency: String) {
        _presenter?.setCurrency(currency)
    }

    override fun onDialogSettingsNegativeClick(dialog: DialogFragment) {}

    interface IFragmentInteractionListener {
        fun onClickItemCoinRate(coinRateUI: CoinRateUI?)
    }

    class CoinRateAdapter internal constructor(private val _listener: IFragmentInteractionListener, private val _list: List<CoinRateUI?>) :
        RecyclerView.Adapter<CoinRateAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val cardView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_coin_constraint,
                parent, false
            )
            return ViewHolder(_listener, cardView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setData(_list[position])
        }

        override fun getItemCount(): Int {
            return _list.size
        }

        class ViewHolder internal constructor(private val _listener: IFragmentInteractionListener?, private val _itemView: View) :
            RecyclerView.ViewHolder(
                _itemView
            ), View.OnClickListener {
            private var _coinRateUI: CoinRateUI? = null
            private var _logo: AppCompatImageView? = null
            private var _symbolCoin: TextView? = null
            private var _nameCoin: TextView? = null
            private var _rateCoin: TextView? = null
            private var _percentChange24h: TextView? = null
            private var _percentChange7d: TextView? = null
            private var _currencyRateCoin: TextView? = null
            private var _percentChange1h: TextView? = null

            init {
                bind()
            }

            fun setData(coinRateUI: CoinRateUI?) {
                _coinRateUI = coinRateUI
                _symbolCoin!!.text = coinRateUI!!.symbol
                _nameCoin!!.text = coinRateUI.name
                _rateCoin!!.text = coinRateUI.uiPrice
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    _percentChange7d!!.setTextColor(_itemView.resources.getColor(coinRateUI.colorPercentChange7d, _itemView.context.theme))
                    _percentChange1h!!.setTextColor(_itemView.resources.getColor(coinRateUI.colorPercentChange1h, _itemView.context.theme))
                    _percentChange24h!!.setTextColor(
                        _itemView.resources.getColor(
                            coinRateUI.colorPercentChange24h,
                            _itemView.context.theme
                        )
                    )
                } else {
                    _percentChange7d!!.setTextColor(_itemView.resources.getColor(coinRateUI.colorPercentChange7d))
                    _percentChange1h!!.setTextColor(_itemView.resources.getColor(coinRateUI.colorPercentChange1h))
                    _percentChange24h!!.setTextColor(_itemView.resources.getColor(coinRateUI.colorPercentChange24h))
                }
                _percentChange7d!!.text = String.format("%s %s", coinRateUI.uiPercentChange7d, LocaleUtils.SYMBOL_PERCENT)
                _percentChange24h!!.text = String.format("%s %s", coinRateUI.uiPercentChange24h, LocaleUtils.SYMBOL_PERCENT)
                _percentChange1h!!.text =
                    String.format("%s %s", coinRateUI.uiPercentChange1h, LocaleUtils.SYMBOL_PERCENT)
                _currencyRateCoin!!.text = coinRateUI.uiCurrency
                Picasso.get()
                    .load(coinRateUI.urlImage)
                    .error(R.drawable.unknown_currency)
                    .into(_logo)
            }

            private fun bind() {
                _symbolCoin = _itemView.findViewById(R.id.symbol_coin)
                _nameCoin = _itemView.findViewById(R.id.name_coin)
                _rateCoin = _itemView.findViewById(R.id.rate_coin)
                _percentChange7d = _itemView.findViewById(R.id.percent_change_7d)
                _percentChange24h = _itemView.findViewById(R.id.percent_change_24h)
                _percentChange1h = _itemView.findViewById(R.id.percent_change_1h)
                _logo = _itemView.findViewById(R.id.img_coin)
                _currencyRateCoin = _itemView.findViewById(R.id.currency_rate_coin)
                _itemView.setOnClickListener(this)
            }

            override fun onClick(view: View) {
                _listener?.onClickItemCoinRate(_coinRateUI)
            }
        }
    }
}
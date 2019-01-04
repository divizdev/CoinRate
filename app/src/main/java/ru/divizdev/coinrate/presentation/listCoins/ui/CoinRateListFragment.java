package ru.divizdev.coinrate.presentation.listCoins.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.divizdev.coinrate.App;
import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;
import ru.divizdev.coinrate.entities.CoinRateUI;
import ru.divizdev.coinrate.presentation.about.AboutDialog;
import ru.divizdev.coinrate.presentation.listCoins.presenter.CoinRateListPresenter;
import ru.divizdev.coinrate.presentation.listCoins.presenter.CoinRateListView;
import ru.divizdev.coinrate.utils.LocaleUtils;

/**
 * Created by diviz on 26.01.2018.
 */

public class CoinRateListFragment extends MvpAppCompatFragment implements CoinRateListView, SwipeRefreshLayout.OnRefreshListener {

    private IFragmentInteractionListener _listener;
    private RecyclerView _recyclerView;
    private List<CoinRateUI> _list = new ArrayList<>();
    private SwipeRefreshLayout _swipeRefreshLayout;
    private ProgressBar _progressBar;
    private DrawerLayout _drawerLayout;
    private NavigationView _navigationView;

    @InjectPresenter
    CoinRateListPresenter _coinRateListPresenter;

    @ProvidePresenter
    CoinRateListPresenter provideCoinRateListPresenter() {
        return new CoinRateListPresenter(App.getManagerSettings());
    }
    //region LifeCycle

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_rate, container, false);

        _recyclerView = view.findViewById(R.id.coin_rate_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        _recyclerView.setLayoutManager(linearLayoutManager);
        CoinRateAdapter coinRateAdapter = new CoinRateAdapter(_listener, _list);
        _recyclerView.setAdapter(coinRateAdapter);
        _recyclerView.addItemDecoration(new DividerItemDecoration(_recyclerView.getContext(), linearLayoutManager.getOrientation()));

        _swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        _swipeRefreshLayout.setOnRefreshListener(this);

        _progressBar = view.findViewById(R.id.progress_bar);

        _drawerLayout = view.findViewById(R.id.drawer_layout);
        _navigationView = view.findViewById(R.id.nav_view);

        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            }
        }

        _navigationView.setNavigationItemSelectedListener(menuItem -> {
            _drawerLayout.closeDrawers();
            return onOptionsItemSelected(menuItem);
        });

        setCheckedInitialItemMenu();

        return view;
    }

    private void setCheckedInitialItemMenu() {
        String currentValue = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(PreferenceManagerSettings.KEY_NAME_PREF, PreferenceManagerSettings.DEFAULT_CURRENCY);
        int itemSelectMenu = R.id.menu_item_usd;
        switch (currentValue){
            case "RUB":
                itemSelectMenu = R.id.menu_item_rub;
                break;
            case "USD":
                itemSelectMenu = R.id.menu_item_usd;
                break;
            case "EUR":
                itemSelectMenu = R.id.menu_item_eur;
                break;
        }
        _navigationView.setCheckedItem(itemSelectMenu);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentInteractionListener) {
            _listener = (IFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }


    //endregion LifeCycle


    //region Menu

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_item_rub:
                _coinRateListPresenter.setCurrency("RUB");
                break;
            case R.id.menu_item_usd:
                _coinRateListPresenter.setCurrency("USD");
                break;
            case R.id.menu_item_eur:
                _coinRateListPresenter.setCurrency("EUR");
                break;
            case R.id.about:
                showDialogAbout();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showDialogAbout() {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(fragmentManager, "");
    }

    //endregion Menu

    //region CoinRateListView

    @Override
    public void showLoadingProgress(Boolean isView) {

        if (isView) {
            if (!_swipeRefreshLayout.isRefreshing()) {
                _progressBar.setVisibility(View.VISIBLE);
            }

        } else {
            _progressBar.setVisibility(View.GONE);
            _swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void showCoinRateList(List<CoinRateUI> list) {
        _list.clear();
        _list.addAll(list);
        Objects.requireNonNull(_recyclerView.getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(getContext(), "ErrorLoad", Toast.LENGTH_SHORT).show();
    }

    //endregion

    @Override
    public void onRefresh() {
        _coinRateListPresenter.refresh();
    }


    public interface IFragmentInteractionListener {

        void onClickItemCoinRate(CoinRateUI coinRateUI);
    }

    public static class CoinRateAdapter extends RecyclerView.Adapter<CoinRateAdapter.ViewHolder> {

        private IFragmentInteractionListener _listener;
        private List<CoinRateUI> _list;

        CoinRateAdapter(IFragmentInteractionListener listener, List<CoinRateUI> list) {
            _listener = listener;
            _list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_constraint,
                    parent, false);
            return new ViewHolder(_listener, cardView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.setData(_list.get(position));
        }

        @Override
        public int getItemCount() {
            return _list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private IFragmentInteractionListener _listener;
            private CoinRateUI _coinRateUI;

            private View _itemView;

            private AppCompatImageView _logo;

            private TextView _symbolCoin;
            private TextView _nameCoin;
            private TextView _rateCoin;
            private TextView _percentChange24h;
            private TextView _percentChange7d;
            private TextView _currencyRateCoin;
            private TextView _percentChange1h;

            ViewHolder(IFragmentInteractionListener listener, View itemView) {
                super(itemView);
                _listener = listener;
                this._itemView = itemView;
                bind();

            }

            public void setData(CoinRateUI coinRateUI) {
                _coinRateUI = coinRateUI;
                _symbolCoin.setText(coinRateUI.getSymbol());
                _nameCoin.setText(coinRateUI.getName());
                _rateCoin.setText(coinRateUI.getUIPrice());


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    _percentChange7d.setTextColor(_itemView.getResources().getColor(coinRateUI.getColorPercentChange7d(), _itemView.getContext().getTheme()));
                    _percentChange1h.setTextColor(_itemView.getResources().getColor(coinRateUI.getColorPercentChange1h(), _itemView.getContext().getTheme()));
                    _percentChange24h.setTextColor(_itemView.getResources().getColor(coinRateUI.getColorPercentChange24h(), _itemView.getContext().getTheme()));

                } else {

                    _percentChange7d.setTextColor(_itemView.getResources().getColor(coinRateUI.getColorPercentChange7d()));
                    _percentChange1h.setTextColor(_itemView.getResources().getColor(coinRateUI.getColorPercentChange1h()));
                    _percentChange24h.setTextColor(_itemView.getResources().getColor(coinRateUI.getColorPercentChange24h()));

                }


                _percentChange7d.setText(String.format("%s %s", coinRateUI.getUIPercentChange7d(), LocaleUtils.SYMBOL_PERCENT));
                _percentChange24h.setText(String.format("%s %s", coinRateUI.getUIPercentChange24h(), LocaleUtils.SYMBOL_PERCENT));
                _percentChange1h.setText(String.format("%s %s", coinRateUI.getUIPercentChange1h(), LocaleUtils.SYMBOL_PERCENT));

                _currencyRateCoin.setText(coinRateUI.getUICurrency());


                Picasso loader = Picasso.with(this.itemView.getContext());
                loader.load(coinRateUI.getURLImage())
                        .into(_logo);


            }


            private void bind() {
                _symbolCoin = _itemView.findViewById(R.id.symbol_coin);
                _nameCoin = _itemView.findViewById(R.id.name_coin);
                _rateCoin = _itemView.findViewById(R.id.rate_coin);
                _percentChange7d = _itemView.findViewById(R.id.percent_change_7d);
                _percentChange24h = _itemView.findViewById(R.id.percent_change_24h);
                _percentChange1h = _itemView.findViewById(R.id.percent_change_1h);
                _logo = _itemView.findViewById(R.id.img_coin);
                _currencyRateCoin = _itemView.findViewById(R.id.currency_rate_coin);
                _itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (_listener != null) {
                    _listener.onClickItemCoinRate(_coinRateUI);
                }
            }
        }
    }

}

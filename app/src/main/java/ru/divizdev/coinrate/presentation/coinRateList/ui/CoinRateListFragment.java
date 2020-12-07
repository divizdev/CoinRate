package ru.divizdev.coinrate.presentation.coinRateList.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.di.Factory;
import ru.divizdev.coinrate.presentation.about.AboutDialog;
import ru.divizdev.coinrate.presentation.coinRateList.presenter.CoinRateListPresenter;
import ru.divizdev.coinrate.presentation.coinRateList.presenter.CoinRateListView;
import ru.divizdev.coinrate.presentation.coinRateList.ui.SettingsDialog.INoticeDialogListener;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import ru.divizdev.coinrate.utils.LocaleUtils;

/**
 * Created by diviz on 26.01.2018.
 */

public class CoinRateListFragment extends MvpAppCompatFragment implements CoinRateListView, SwipeRefreshLayout.OnRefreshListener, INoticeDialogListener {

    @InjectPresenter
    CoinRateListPresenter _presenter;
    private RecyclerView _recyclerView;
    private List<CoinRateUI> _list = new ArrayList<>();
    private SwipeRefreshLayout _swipeRefreshLayout;
    private ProgressBar _progressBar;


    @ProvidePresenter
    CoinRateListPresenter provideCoinRateListPresenter() {
        return new CoinRateListPresenter(Factory.getFactory().getManagerSettings(), Factory.getFactory().getRxRepository());
    }
    //region LifeCycle

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_rate, container, false);

        _recyclerView = view.findViewById(R.id.coin_rate_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        _recyclerView.setLayoutManager(linearLayoutManager);
        CoinRateAdapter coinRateAdapter = new CoinRateAdapter(coinRateUI -> _presenter.clickToItem(coinRateUI), _list);
        _recyclerView.setAdapter(coinRateAdapter);
        _recyclerView.addItemDecoration(new DividerItemDecoration(_recyclerView.getContext(), linearLayoutManager.getOrientation()));

        _swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        _swipeRefreshLayout.setOnRefreshListener(this);

        _progressBar = view.findViewById(R.id.progress_bar);


        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
                supportActionBar.setDisplayShowHomeEnabled(false);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CoinRateListFragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CoinRateListFragment");
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            case R.id.action_settings:
                showDialogSettings();
                break;
            case R.id.about:
                showDialogAbout();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void showDialogSettings() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.setTargetFragment(this, 0);
        settingsDialog.show(fragmentManager, "");
    }

    @Override
    public void showDialogAbout() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(fragmentManager, "");
    }

    @Override
    public void navToDetail(CoinRateUI coinRateUI) {
        if (getActivity() != null) {
            Factory.getFactory().getRouter().navToDetail((AppCompatActivity) getActivity(), coinRateUI);
        }
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
        _presenter.refresh();
    }

    @Override
    public void onDialogSettingsSelectedItem(String currency) {
        _presenter.setCurrency(currency);
    }

    @Override
    public void onDialogSettingsNegativeClick(DialogFragment dialog) {

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


                Picasso.get()
                        .load(coinRateUI.getURLImage())
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

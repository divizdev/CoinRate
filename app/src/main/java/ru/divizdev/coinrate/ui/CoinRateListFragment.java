package ru.divizdev.coinrate.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.divizdev.coinrate.App;
import ru.divizdev.coinrate.Entities.CoinRateUI;
import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.rates.CoinRateListInteraction;

/**
 * Created by diviz on 26.01.2018.
 */

public class CoinRateListFragment extends Fragment implements CoinRateListInteraction.ICoinRateListView, SwipeRefreshLayout.OnRefreshListener, SettingsDialog.INoticeDialogListener{

    private IFragmentInteractionListener _listener;
    private RecyclerView _recyclerView;
    private List<CoinRateUI> _list = new ArrayList<>();
    private SwipeRefreshLayout _swipeRefreshLayout;
    private ProgressBar _progressBar;


    //region LifeCycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_coin_rate, container, false);

        _recyclerView = view.findViewById(R.id.coin_rate_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        _recyclerView.setLayoutManager(linearLayoutManager);

        CoinRateAdapter coinRateAdapter = new CoinRateAdapter(_listener, _list);
        _recyclerView.setAdapter(coinRateAdapter);

        _swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        _swipeRefreshLayout.setOnRefreshListener(this);

        _progressBar = view.findViewById(R.id.progress_bar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        App.getCoinRateListPresenter().attache(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        App.getCoinRateListPresenter().detach();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
         inflater.inflate(R.menu.main_menu, menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettingsDialog();
                break;
            case R.id.about:
                showDialogAbout();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showDialogAbout() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(fragmentManager, "");
    }

    private void showSettingsDialog() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.show(fragmentManager, "");
    }

    @Override
    public void onDialogSettingsSelectedItem(String currency) {
        App.getCoinRateListPresenter().setCurrency(currency);
    }

    @Override
    public void onDialogSettingsNegativeClick(DialogFragment dialog) {

    }

    //endregion Menu

    //region ICoinRateListView

    @Override
    public void showLoadingProgress(Boolean isView) {

        if (isView) {
            if(!_swipeRefreshLayout.isRefreshing()){
                _progressBar.setVisibility(View.VISIBLE);
            }

        }else {
            _progressBar.setVisibility(View.GONE);
            _swipeRefreshLayout.setRefreshing(false);
        }
    }



    @Override
    public void showCoinRateList(List<CoinRateUI> list) {
        _list.clear();
        _list.addAll(list);
        _recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(getContext(), "ErrorLoad", Toast.LENGTH_SHORT).show();
    }

    //endregion

    @Override
    public void onRefresh() {
        App.getCoinRateListPresenter().refresh();
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
            CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin,
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

            private CardView _cardView;

            private AppCompatImageView _logo;

            private TextView _symbolCoin;
            private TextView _nameCoin;
            private TextView _rateCoin;
            private TextView _percentChange24h;
            private TextView _percentChange7d;
            private TextView _currencyRateCoin;

            ViewHolder(IFragmentInteractionListener listener, CardView itemView) {
                super(itemView);
                _listener = listener;
                this._cardView = itemView;
                bind();
                Log.d("ViewHolder", "Create ViewHolder");
            }

            public void setData(CoinRateUI coinRateUI) {
                _coinRateUI = coinRateUI;
                _symbolCoin.setText(coinRateUI.getSymbol());
                _nameCoin.setText(coinRateUI.getName());
                _rateCoin.setText(coinRateUI.getUIPrice());

                _percentChange7d.setTextColor(coinRateUI.getColorPercentChange7d());
                _percentChange7d.setText(coinRateUI.getUIPercentChange7d());

                _percentChange24h.setTextColor(coinRateUI.getColorPercentChange24h());
                _percentChange24h.setText(coinRateUI.getUIPercentChange24h());

                _currencyRateCoin.setText(coinRateUI.getUICurrency());



                Picasso loader = Picasso.with(this.itemView.getContext());
                        loader.load(coinRateUI.getURLImage())
                        .into(_logo);
                Log.d("ViewHolder", "SetData");

            }


            private void bind() {
                _symbolCoin = _cardView.findViewById(R.id.symbol_coin);
                _nameCoin = _cardView.findViewById(R.id.name_coin);
                _rateCoin = _cardView.findViewById(R.id.rate_coin);
                _percentChange7d = _cardView.findViewById(R.id.percent_change_7d);
                _percentChange24h = _cardView.findViewById(R.id.percent_change_24h);
                _logo = _cardView.findViewById(R.id.img_coin);
                _currencyRateCoin = _cardView.findViewById(R.id.currency_rate_coin);
                _cardView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (_listener != null) {
                    _listener.onClickItemCoinRate(_coinRateUI);
                }
            }
        }
    }



    public interface IFragmentInteractionListener {

        void onClickItemCoinRate(CoinRateUI coinRateUI);
    }

}

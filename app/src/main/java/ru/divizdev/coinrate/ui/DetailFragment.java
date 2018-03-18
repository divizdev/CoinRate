package ru.divizdev.coinrate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.divizdev.coinrate.Entities.CoinRateUI;
import ru.divizdev.coinrate.R;


public class DetailFragment extends Fragment  {

    private static final String ARG_COIN_RATE = "coin_rate";
    private static final String SYMBOL_PERCENT = "%";

    private CoinRateUI _coinRateUI;
    private TextView _detailNameCoin;
    private TextView _detailAvailableSupply;
    private TextView _detailMarketCap; //detail_max_supply
    private TextView _detail24hVolume; //detail_total_supply
    private TextView _percentChanged24h;
    private TextView _percentChanged1h;
    private TextView _percentChanged7d;
    private TextView _detailSymbolCoin;
    private ImageView _logo;



    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(CoinRateUI coinRateUI) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COIN_RATE, coinRateUI);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _coinRateUI = getArguments().getParcelable(ARG_COIN_RATE);
        }

        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_detail, container, false);
        bind(result);
        return result;
    }

    private void bind(View view) {
        _detailAvailableSupply = view.findViewById(R.id.detail_available_supply);
        _detailNameCoin = view.findViewById(R.id.detail_name_coin);
        _detailMarketCap = view.findViewById(R.id.detail_market_cap);
        _detail24hVolume = view.findViewById(R.id.detail_24h_volume);
        _percentChanged1h = view.findViewById(R.id.percent_change_1h);
        _percentChanged24h = view.findViewById(R.id.percent_change_24h);
        _percentChanged7d = view.findViewById(R.id.percent_change_7d);
        _logo = view.findViewById(R.id.detail_image_view);
        _detailSymbolCoin = view.findViewById(R.id.detail_symbol_coin);


        setData(view, _coinRateUI);
    }

    private void setData(View view, CoinRateUI coinRateUI) {
        _detailNameCoin.setText(coinRateUI.getName());
        _detail24hVolume.setText(String.format("%s %s",  coinRateUI.getUIVolume24(), coinRateUI.getUICurrency()));
        _detailMarketCap.setText(String.format("%s %s", coinRateUI.getUIMarketCap(), coinRateUI.getUICurrency()));
        _detailAvailableSupply.setText(String.format("%s %s", coinRateUI.getUIAvailableSupply(), coinRateUI.getSymbol()));
        _detailSymbolCoin.setText(coinRateUI.getSymbol());

        _percentChanged1h.setText(String.format("%s %s", coinRateUI.getPercentChange1h(), SYMBOL_PERCENT));
        _percentChanged24h.setText(String.format("%s %s", coinRateUI.getPercentChange24h(), SYMBOL_PERCENT));
        _percentChanged7d.setText(String.format("%s %s", coinRateUI.getPercentChange7d(), SYMBOL_PERCENT));

        _percentChanged1h.setTextColor(coinRateUI.getColorPercentChange1h());
        _percentChanged24h.setTextColor(coinRateUI.getColorPercentChange24h());
        _percentChanged7d.setTextColor(coinRateUI.getColorPercentChange7d());

        Picasso.with(view.getContext())
                .load(coinRateUI.getURLImage())
                .into(_logo);
    }


}

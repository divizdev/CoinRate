package ru.divizdev.coinrate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private CoinRateUI _coinRateUI;
    private TextView _detailNameCoin;
    private TextView _detailAvailableSupply;
    private TextView _detailMarketCap; //detail_max_supply
    private TextView _detail24hVolume; //detail_total_supply
    private ImageView _logo;
    private TextView _detailSymbolCoin;



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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        _logo = view.findViewById(R.id.detail_image_view);
        _detailSymbolCoin = view.findViewById(R.id.detail_symbol_coin);

        _detailNameCoin.setText(_coinRateUI.getName());
        _detailSymbolCoin.setText(_coinRateUI.getSymbol());
        _detail24hVolume.setText(String.format("%s %s",  _coinRateUI.getUIVolume24(), _coinRateUI.getUICurrency()));
        _detailMarketCap.setText(String.format("%s %s", _coinRateUI.getUIMarketCap(), _coinRateUI.getUICurrency()));
        _detailAvailableSupply.setText(String.format("%s %s", _coinRateUI.getUIAvailableSupply(), _coinRateUI.getSymbol()));

        Picasso.with(view.getContext())
                .load(_coinRateUI.getURLImage())
                .into(_logo);
    }


}

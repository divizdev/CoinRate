package ru.divizdev.coinrate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.divizdev.coinrate.App;
import ru.divizdev.coinrate.Entities.CoinRate;
import ru.divizdev.coinrate.R;


public class DetailFragment extends Fragment implements ICurrencyChangeListener {

    private static final String ARG_COIN_RATE = "coin_rate";

    private CoinRate _coinRate;
    private TextView _detailNameCoin;
    private TextView _detailAvailableSupply;
    private TextView _detailMarketCap; //detail_max_supply
    private TextView _detail24hVolume; //detail_total_supply
    private ImageView _logo;



    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(CoinRate coinRate) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COIN_RATE, coinRate);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _coinRate = getArguments().getParcelable(ARG_COIN_RATE);
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
        _logo = view.findViewById(R.id.detail_image_view);

        _detailNameCoin.setText(_coinRate.getName());
        _detail24hVolume.setText(String.format("%s %s",  _coinRate.getUIVolume24(), _coinRate.getUICurrency()));
        _detailMarketCap.setText(String.format("%s %s",_coinRate.getUIMarketCap(), _coinRate.getUICurrency()));
        _detailAvailableSupply.setText(String.format("%s %s", _coinRate.getUIAvailableSupply(), _coinRate.getSymbol()));

        Picasso.with(view.getContext())
                .load("http://divizdev.ru/CoinRate/color/" + _coinRate.getSymbol().toLowerCase() + "@2x.png")
                .into(_logo);
    }


    @Override
    public void onChangeCurrency(String currency) {
        //TODO: Implement his Interaction
        App.getCoinRateListPresenter().setCurrency(currency);
    }
}

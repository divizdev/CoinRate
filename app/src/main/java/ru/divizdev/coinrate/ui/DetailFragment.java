package ru.divizdev.coinrate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.divizdev.coinrate.App;
import ru.divizdev.coinrate.Entities.CoinRateUI;
import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.rates.CoinRateDetailInteraction;
import ru.divizdev.coinrate.rates.ICoinRateDetailInteraction;


public class DetailFragment extends Fragment implements CoinRateDetailInteraction.ICoinRateDetailView {

    private static final String ARG_COIN_RATE = "coin_rate";

    private static ICoinRateDetailInteraction _interaction = App.getCoiCoinRateDetailInteraction();

    private CoinRateUI _coinRateUI;
    private TextView _detailNameCoin;
    private TextView _detailAvailableSupply;
    private TextView _detailMarketCap; //detail_max_supply
    private TextView _detail24hVolume; //detail_total_supply

    private TextView _detailSymbolCoin;
    private ImageView _logo;

    private TextView _nameCurrencyFrom;
    private TextView _nameCurrencyTo;
    private EditText _valueFrom;
    private TextView _valueTo;

    private Button _buttonConvert;


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
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_detail, container, false);
        bind(result);
        return result;
    }

    private void bind(View view) {

        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

            if(supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setDisplayShowHomeEnabled(true);
            }
        }

        _detailAvailableSupply = view.findViewById(R.id.detail_available_supply);
        _detailNameCoin = view.findViewById(R.id.detail_name_coin);
        _detailMarketCap = view.findViewById(R.id.detail_market_cap);
        _detail24hVolume = view.findViewById(R.id.detail_24h_volume);

        _logo = view.findViewById(R.id.detail_image_view);
        _detailSymbolCoin = view.findViewById(R.id.detail_symbol_coin);
        _nameCurrencyFrom = view.findViewById(R.id.name_currency_from);
        _nameCurrencyTo = view.findViewById(R.id.name_currency_to);
        Button buttonChangeCurrency = view.findViewById(R.id.button_change_currency);
        _buttonConvert = view.findViewById(R.id.button_convert);
        _valueFrom = view.findViewById(R.id.value_from);
        _valueTo = view.findViewById(R.id.value_to);

        _valueFrom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){

                    _interaction.convertCurrency(_valueFrom.getText().toString());
                }
                return false;
            }
        });

        _buttonConvert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                _interaction.convertCurrency(_valueFrom.getText().toString());
            }
        });

        buttonChangeCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _interaction.changeCurrency();
            }
        });



        setData(view, _coinRateUI);
    }

    @Override
    public void onResume() {
        super.onResume();
        _interaction.attache(this, _coinRateUI);

    }

    @Override
    public void onPause() {
        super.onPause();
        _interaction.detache();
    }

    private void setData(View view, CoinRateUI coinRateUI) {
        _detailNameCoin.setText(coinRateUI.getName());
        _detail24hVolume.setText(String.format("%s %s",  coinRateUI.getUIVolume24(), coinRateUI.getUICurrency()));
        _detailMarketCap.setText(String.format("%s %s", coinRateUI.getUIMarketCap(), coinRateUI.getUICurrency()));
        _detailAvailableSupply.setText(String.format("%s %s", coinRateUI.getUIAvailableSupply(), coinRateUI.getSymbol()));
        _detailSymbolCoin.setText(coinRateUI.getSymbol());



        Picasso.with(view.getContext())
                .load(coinRateUI.getURLImage())
                .into(_logo);
    }


    @Override
    public void setCurrencyFrom(String currencyFrom) {
        _nameCurrencyFrom.setText(currencyFrom);
    }

    @Override
    public void setCurrencyTo(String currencyTo) {
        _nameCurrencyTo.setText(currencyTo);
    }

    @Override
    public void setValue(String value) {
        _valueTo.setText(value);
    }

    @Override
    public void clearAll() {
        _valueFrom.setText("");
        _valueTo.setText("");

    }

    @Override
    public void showError(String error) {

    }
}

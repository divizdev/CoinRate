package ru.divizdev.coinrate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.divizdev.coinrate.Entities.CoinRate;


public class DetailFragment extends Fragment {

    private static final String ARG_COIN_RATE = "coin_rate";

    private CoinRate _coinRate;
    private TextView _detailNameCoin;
    private TextView _detailAvailableSupply;
    private TextView _detailMaxSupply; //detail_max_supply
    private TextView _detailTotalSupply; //detail_total_supply
    private ImageView _logo;



    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param coinRate CoinRate.
     * @return A new instance of fragment DetailFragment.
     */
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
        _detailMaxSupply = view.findViewById(R.id.detail_max_supply);
        _detailTotalSupply = view.findViewById(R.id.detail_total_supply);
        _logo = view.findViewById(R.id.detail_image_view);

        _detailNameCoin.setText(_coinRate.getName());
        _detailTotalSupply.setText(String.valueOf(_coinRate.getTotalSupply()));
        _detailMaxSupply.setText(String.valueOf(_coinRate.getMaxSupply()));
        _detailAvailableSupply.setText(String.valueOf(_coinRate.getAvailableSupply()));

        Picasso.with(view.getContext())
                .load("https://files.coinmarketcap.com/static/img/coins/64x64/" + _coinRate.getId() + ".png")
                .into(_logo);
    }


}

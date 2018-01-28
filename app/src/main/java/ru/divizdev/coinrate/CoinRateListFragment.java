package ru.divizdev.coinrate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BL.CoinRateApi;
import ru.divizdev.coinrate.Entities.CoinRate;

/**
 * Created by diviz on 26.01.2018.
 */

public class CoinRateListFragment extends Fragment {

    private OnFragmentInteractionListener _listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_coin_rate, container, false);
        GetData(view);
        return view;
    }

    private void GetData(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        CoinRateApi api = retrofit.create(CoinRateApi.class);

        final List<CoinRate> list = new ArrayList<>();

        final RecyclerView _recyclerView = view.findViewById(R.id.coin_rate_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        _recyclerView.setLayoutManager(linearLayoutManager);

        CoinRateAdapter coinRateAdapter = new CoinRateAdapter(_listener, list);
        _recyclerView.setAdapter(coinRateAdapter);


        api.getData(200).enqueue(new Callback<List<CoinRate>>() {
            @Override
            public void onResponse(Call<List<CoinRate>> call, Response<List<CoinRate>> response) {
                if (response.body() != null) {
                    list.addAll(response.body());
                    _recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CoinRate>> call, Throwable t) {
                Log.e("test", "Fail");
            }
        });
    }

    public static class CoinRateAdapter extends RecyclerView.Adapter<CoinRateAdapter.ViewHolder> {

        private OnFragmentInteractionListener _listener;
        private List<CoinRate> _list;

        public CoinRateAdapter(OnFragmentInteractionListener listener, List<CoinRate> list) {
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

            private OnFragmentInteractionListener _listener;
            private CoinRate _coinRate;

            private CardView _cardView;

            private AppCompatImageView _logo;

            private TextView _symbolCoin;
            private TextView _nameCoin;
            private TextView _rateCoin;
            private TextView _percentChange24h;
            private TextView _percentChange7d;

            public ViewHolder(OnFragmentInteractionListener listener, CardView itemView) {
                super(itemView);
                _listener = listener;
                this._cardView = itemView;
                bind();
                Log.d("ViewHolder", "Create ViewHolder");
            }

            public void setData(CoinRate coinRate) {
                _coinRate = coinRate;
                _symbolCoin.setText(coinRate.getSymbol());
                _nameCoin.setText(coinRate.getName());
                _rateCoin.setText(String.valueOf(coinRate.getPrice()));

                _percentChange7d.setTextColor(getColorPercent(coinRate.getPercentChange7d()));
                _percentChange7d.setText(String.valueOf(coinRate.getPercentChange7d()));

                _percentChange24h.setTextColor(getColorPercent(coinRate.getPercentChange24h()));
                _percentChange24h.setText(String.valueOf(coinRate.getPercentChange24h()));

                Picasso.with(this.itemView.getContext())
                        .load("https://files.coinmarketcap.com/static/img/coins/32x32/" + coinRate.getId() + ".png")
                        .into(_logo);
                Log.d("ViewHolder", "SetData");

            }

            private int getColorPercent(double percent) {
                if (percent > 0.0) {
                    return Color.GREEN;
                } else {
                    return Color.RED;
                }
            }

            private void bind() {
                _symbolCoin = _cardView.findViewById(R.id.symbol_coin);
                _nameCoin = _cardView.findViewById(R.id.name_coin);
                _rateCoin = _cardView.findViewById(R.id.rate_coin);
                _percentChange7d = _cardView.findViewById(R.id.percent_change_7d);
                _percentChange24h = _cardView.findViewById(R.id.percent_change_24h);
                _logo = _cardView.findViewById(R.id.img_coin);
                _cardView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (_listener != null) {
                    _listener.onFragmentInteraction(_coinRate);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            _listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(CoinRate coinRate);
    }

}

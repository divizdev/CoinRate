package ru.divizdev.coinrate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.BL.CoinRateApi;
import ru.divizdev.coinrate.Entities.CoinRate;

public class CoinRateActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_rate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        CoinRateApi api = retrofit.create(CoinRateApi.class);

        final List<CoinRate> list = new ArrayList<>();

        _recyclerView = (RecyclerView) findViewById(R.id.coin_rate_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(linearLayoutManager);

        CoinRateAdapter coinRateAdapter = new CoinRateAdapter(list);
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

        private List<CoinRate> _list;

        public CoinRateAdapter(List<CoinRate> list) {
            _list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin,
                    parent, false);
            return new ViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.setData(_list.get(position));
            holder.setData(_list.get(position));
        }

        @Override
        public int getItemCount() {
            return _list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            CardView _cardView;

            TextView _symbolCoin;
            TextView _nameCoin;
            TextView _rateCoin;
            TextView _percentChange24h;
            TextView _percentChange7d;

            public ViewHolder(CardView itemView) {
                super(itemView);
                this._cardView = itemView;
                bind();
            }

            public void setData(CoinRate coinRate) {
                _symbolCoin.setText(coinRate.getSymbol());
                _nameCoin.setText(coinRate.getName());
                _rateCoin.setText(String.valueOf(coinRate.getPrice()));

                _percentChange7d.setTextColor(getColorPercent(coinRate.getPercentChange7d()));
                _percentChange7d.setText(String.valueOf(coinRate.getPercentChange7d()));

                _percentChange24h.setTextColor(getColorPercent(coinRate.getPercentChange24h()));
                _percentChange24h.setText(String.valueOf(coinRate.getPercentChange24h()));

            }

            private int getColorPercent(double percent) {
                if (percent > 0.0) {
                    return Color.GREEN;
                } else {
                    return Color.RED;
                }
            }

            private void bind() {
                _symbolCoin = (TextView) _cardView.findViewById(R.id.symbol_coin);
                _nameCoin = (TextView) _cardView.findViewById(R.id.name_coin);
                _rateCoin = (TextView) _cardView.findViewById(R.id.rate_coin);
                _percentChange7d = (TextView) _cardView.findViewById(R.id.percent_change_7d);
                _percentChange24h = (TextView) _cardView.findViewById(R.id.percent_change_24h);
            }
        }
    }
}

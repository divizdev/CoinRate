package ru.divizdev.coinrate.BL;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.Entities.CoinRate;

/**
 * Created by diviz on 29.01.2018.
 */

public class CoinRateListPresenter {

   private CoinRateListView _coinRateListView;
   private List<CoinRate> _list = new ArrayList<>();

   public  CoinRateListPresenter(){

   }

   public void subscribe(CoinRateListView coinRateListView){
       _coinRateListView = coinRateListView;
       if(_list.size() > 0 ){
           _coinRateListView.showCoinRateList(_list);
       } else {
           _coinRateListView.showLoadingProgress(true);
           loadCoinRate();
       }
   }

   private void loadCoinRate(){


       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("https://api.coinmarketcap.com/") //Базовая часть адреса
               .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
               .build();

       CoinRateApi api = retrofit.create(CoinRateApi.class);

       api.getData(200).enqueue(new Callback<List<CoinRate>>() {
           @Override
           public void onResponse(Call<List<CoinRate>> call, Response<List<CoinRate>> response) {
               if (response.body() != null) {
                   _list.addAll(response.body());
                   _coinRateListView.showCoinRateList(_list);
               }
           }

           @Override
           public void onFailure(Call<List<CoinRate>> call, Throwable t) {
               _coinRateListView.showErrorLoading();
               Log.e("test", "Fail");
           }
       });

   }




    public interface CoinRateListView{
        void showLoadingProgress(Boolean isView);
        void showCoinRateList(List<CoinRate> list);
        void showErrorLoading();
    }
}

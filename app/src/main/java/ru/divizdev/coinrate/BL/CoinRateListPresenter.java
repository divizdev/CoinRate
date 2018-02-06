package ru.divizdev.coinrate.BL;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
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

   private ICoinRateListView _ICoinRateListView;

   private Context _context;
   private List<CoinRate> _list = new ArrayList<>();

   public  CoinRateListPresenter(Context context){
       _context = context;
   }

   public void subscribe(ICoinRateListView ICoinRateListView){
       _ICoinRateListView = ICoinRateListView;
       if(_list.size() > 0 ){
           _ICoinRateListView.showCoinRateList(_list);
       } else {
           loadCoinRate();
       }
   }

   public void refresh(){

       _list.clear(); //TODO: multi thread not lock
       loadCoinRate();
   }

   private void loadCoinRate(){

       _ICoinRateListView.showLoadingProgress(true);

       //TODO: Extract constants
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("https://api.coinmarketcap.com/") //Базовая часть адреса
               .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
               .build();

       CoinRateApi api = retrofit.create(CoinRateApi.class);

       //TODO: Extract constants
       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
       String currency = preferences.getString("pref_currency", "USD");

       api.getData(0,200, currency).enqueue(new Callback<List<CoinRate>>() {
           @Override
           public void onResponse(Call<List<CoinRate>> call, Response<List<CoinRate>> response) {
               if (response.body() != null) {
                   _list.addAll(response.body());

                   _ICoinRateListView.showLoadingProgress(false);
                   _ICoinRateListView.showCoinRateList(_list);
               }
           }

           @Override
           public void onFailure(Call<List<CoinRate>> call, Throwable t) {
               _ICoinRateListView.showLoadingProgress(false);
               _ICoinRateListView.showErrorLoading();
               Log.e("test", "Fail");
           }
       });

   }




    public interface ICoinRateListView {
        void showLoadingProgress(Boolean isView);
        void showCoinRateList(List<CoinRate> list);
        void showErrorLoading();
    }
}

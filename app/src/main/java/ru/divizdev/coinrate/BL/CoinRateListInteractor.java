package ru.divizdev.coinrate.BL;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.divizdev.coinrate.Entities.CoinRate;

/**
 * Created by diviz on 29.01.2018.
 */

public class CoinRateListInteractor {

   private ICoinRateListView _ICoinRateListView;
   private String _curCurrency = "";

   private Context _context;

   private Map<String, List<CoinRate>> _coinRateModel = new HashMap<>();

   public CoinRateListInteractor(Context context){
       _context = context;
       _curCurrency = getCurrencySettings();
   }

    private String getCurrencySettings() {
        //TODO: Extract constants
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        return preferences.getString("pref_currency", "USD");
    }

    public void subscribe(ICoinRateListView ICoinRateListView){
       _ICoinRateListView = ICoinRateListView;
        showList(_curCurrency);
   }

    private void showList(String curCurrency) {
        List<CoinRate> list = _coinRateModel.get(curCurrency);
        if(list != null){
            _ICoinRateListView.showCoinRateList(list);
        } else {
            loadCoinRate(curCurrency);
        }
    }

    public void refresh(){

       loadCoinRate(_curCurrency);

   }

   private void loadCoinRate(final String currency){

       _ICoinRateListView.showLoadingProgress(true);

       //TODO: Extract constants
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("https://api.coinmarketcap.com/") //Базовая часть адреса
               .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
               .build();

       CoinRateApi api = retrofit.create(CoinRateApi.class);

       api.getData(0,200, currency).enqueue(new Callback<List<CoinRate>>() {
           @Override
           public void onResponse(@NonNull Call<List<CoinRate>> call, @NonNull Response<List<CoinRate>> response) {
               if (response.body() != null) {
                   _coinRateModel.put(currency, response.body());//TODO: multi thread not lock

                   _ICoinRateListView.showLoadingProgress(false);
                   _ICoinRateListView.showCoinRateList(response.body());
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

    public void setCurrency(String currency) {

       _curCurrency = currency;
       showList(_curCurrency);

    }


    public interface ICoinRateListView {
        void showLoadingProgress(Boolean isView);
        void showCoinRateList(List<CoinRate> list);
        void showErrorLoading();
    }
}

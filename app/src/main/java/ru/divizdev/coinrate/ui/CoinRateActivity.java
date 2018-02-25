package ru.divizdev.coinrate.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.divizdev.coinrate.App;
import ru.divizdev.coinrate.Entities.CoinRate;
import ru.divizdev.coinrate.R;

public class CoinRateActivity extends AppCompatActivity implements CoinRateListFragment.OnFragmentInteractionListener, DialogSettings.NoticeDialogListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_rate);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CoinRateListFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public void onClickItemCoinRate(CoinRate coinRate) {
        FragmentManager fm = getSupportFragmentManager();

        Fragment detailFragment = DetailFragment.newInstance(coinRate);

        fm.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }




    public void showSettingsDialog(){
                FragmentManager fragmentManager = getSupportFragmentManager();
                 DialogSettings dialogSettings = new DialogSettings();
                 dialogSettings.show(fragmentManager, "");
//                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PrefFragment()).addToBackStack(null).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:

                showSettingsDialog();
                break;
            default:
            return super.onOptionsItemSelected(item);
        }
        return true;

    }

    @Override
    public void onDialogSelectedItem(String currency) {

        App.getCoinRateListPresenter().setCurrency(currency);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

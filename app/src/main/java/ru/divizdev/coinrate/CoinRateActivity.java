package ru.divizdev.coinrate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.divizdev.coinrate.Entities.CoinRate;

public class CoinRateActivity extends AppCompatActivity implements CoinRateListFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_rate);

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
    public void onFragmentInteraction(CoinRate coinRate) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment detailFragment = DetailFragment.newInstance(coinRate);

        fm.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }
}

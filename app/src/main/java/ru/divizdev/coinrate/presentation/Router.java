package ru.divizdev.coinrate.presentation;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import ru.divizdev.coinrate.presentation.detail.ui.DetailFragment;

public class Router {

    public void navToDetail(@NonNull AppCompatActivity activity, CoinRateUI coinRateUI) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment detailFragment = DetailFragment.newInstance(coinRateUI);
        fm.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }

}

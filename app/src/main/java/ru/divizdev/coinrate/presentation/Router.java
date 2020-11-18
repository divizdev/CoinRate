package ru.divizdev.coinrate.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

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

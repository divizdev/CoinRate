package ru.divizdev.coinrate.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;
import ru.divizdev.coinrate.presentation.detail.ui.DetailFragment;

public class Router {

    public void navToDetail(@NonNull AppCompatActivity activity, CoinRateUI coinRateUI) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(activity);
        Bundle bundle = new Bundle();
        bundle.putString(Param.ITEM_ID, coinRateUI.getId());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, coinRateUI.getName());
        firebaseAnalytics.logEvent(Event.SELECT_CONTENT, bundle);

        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment detailFragment = DetailFragment.newInstance(coinRateUI);
        fm.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }

}

package ru.divizdev.coinrate.presentation.main;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.presentation.coinRateList.ui.CoinRateListFragment;
import ru.divizdev.coinrate.utils.LocaleUtils;

public class CoinRateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_rate);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            Fragment fragment = new CoinRateListFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }

        LocaleUtils.setCurrentLocale(locale);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

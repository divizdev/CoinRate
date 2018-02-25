package ru.divizdev.coinrate.ui;

import android.os.Bundle;
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

public class CoinRateActivity extends AppCompatActivity implements CoinRateListFragment.OnFragmentInteractionListener, SettingsDialog.NoticeDialogListener {


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


    public void showSettingsDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.show(fragmentManager, "");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettingsDialog();
                break;
            case R.id.about:
                showDialogAbout();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    private void showDialogAbout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(fragmentManager, "");
    }

    @Override
    public void onDialogSelectedItem(String currency) {

        App.getCoinRateListPresenter().setCurrency(currency);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

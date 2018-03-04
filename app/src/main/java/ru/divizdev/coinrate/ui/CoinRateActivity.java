package ru.divizdev.coinrate.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.divizdev.coinrate.Entities.CoinRateUI;
import ru.divizdev.coinrate.R;

public class CoinRateActivity extends AppCompatActivity implements CoinRateListFragment.IFragmentInteractionListener, SettingsDialog.INoticeDialogListener {


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
    public void onClickItemCoinRate(CoinRateUI coinRateUI) {
        FragmentManager fm = getSupportFragmentManager();

        Fragment detailFragment = DetailFragment.newInstance(coinRateUI);

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

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment != null && fragment instanceof ICurrencyChangeListener){
            ((ICurrencyChangeListener)fragment).onChangeCurrency(currency);
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

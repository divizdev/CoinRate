package ru.divizdev.coinrate.presentation.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.presentation.coinRateList.ui.CoinRateListFragment
import com.google.firebase.analytics.FirebaseAnalytics
import ru.divizdev.coinrate.utils.LocaleUtils
import java.util.*

class CoinRateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_rate)
        val fragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            val fragment: Fragment = CoinRateListFragment()
            fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
        val locale: Locale
        locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0]
        } else {
            resources.configuration.locale
        }
        LocaleUtils.currentLocale = locale
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
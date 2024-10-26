package ru.divizdev.coinrate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.presentation.detail.ui.DetailFragment.Companion.newInstance
import ru.divizdev.coinrate.presentation.entities.CoinRateUI

class Router {
    fun navToDetail(activity: AppCompatActivity, coinRateUI: CoinRateUI) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, coinRateUI.id)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, coinRateUI.name)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        val fm = activity.supportFragmentManager
        val detailFragment: Fragment = newInstance(coinRateUI)
        fm.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit()
    }

    sealed interface Screens {
        data class Detail(val coinRateUI: CoinRateUI): Screens
    }
}

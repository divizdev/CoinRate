package ru.divizdev.coinrate.data

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Created by diviz on 03.03.2018.
 */
class PreferenceManagerSettings(private val _context: Context) : ManagerSettings {


    companion object {
        const val KEY_NAME_PREF = "pref_currency"
        const val DEFAULT_CURRENCY = "USD"
    }

    override var curCurrency: String
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(_context)
            return preferences.getString(KEY_NAME_PREF, "USD") ?: "USD"
        }
        set(value) {
            PreferenceManager.getDefaultSharedPreferences(_context).edit().putString(KEY_NAME_PREF, value).apply()

        }
}
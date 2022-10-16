package ru.divizdev.coinrate.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by diviz on 10.02.2018.
 */
object LocaleUtils {
    private val RUSSIAN_LOCALE = Locale("RU")
    private val ENGLISH_LOCALE = Locale.ENGLISH
    const val SYMBOL_PERCENT = "%"
    private var _currentLocale: Locale? = null
    var currentLocale: Locale?
        get() = if (_currentLocale != null) {
            _currentLocale
        } else RUSSIAN_LOCALE
        set(locale) {
            _currentLocale = locale
        }
    private val currentScale: Int
        private get() = 2

    fun formatBigDecimal(decimal: BigDecimal?): String {
        if(decimal == null) return ""
        val decimalCopy = decimal.setScale(currentScale, RoundingMode.HALF_DOWN)
        val df = NumberFormat.getInstance(currentLocale) as DecimalFormat
        df.minimumFractionDigits = currentScale
        df.isGroupingUsed = true
        return df.format(decimalCopy)
    }
}
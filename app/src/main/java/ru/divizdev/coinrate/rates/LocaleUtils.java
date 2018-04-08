package ru.divizdev.coinrate.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by diviz on 10.02.2018.
 */

public class LocaleUtils {

    private static final Locale RUSSIAN_LOCALE = new Locale("RU");
    private static final Locale ENGLISH_LOCALE = Locale.ENGLISH;
    public static final String SYMBOL_PERCENT = "%";
    private static Locale _currentLocale;

    public static void setCurrentLocale(Locale locale) {
        _currentLocale = locale;
    }

    public static Locale getCurrentLocale() {
        if (_currentLocale != null) {
            return _currentLocale;
        }
        return RUSSIAN_LOCALE;
    }

    private static int getCurrentScale() {
        return 2;
    }

    public static String formatBigDecimal(BigDecimal decimal) {
        BigDecimal decimalCopy = decimal.setScale(getCurrentScale(), RoundingMode.HALF_DOWN);
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(getCurrentLocale());
        df.setMinimumFractionDigits(getCurrentScale());
        df.setGroupingUsed(true);
        return df.format(decimalCopy);
    }

}



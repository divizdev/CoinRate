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


    public static Locale getCurrentLocale() {
        return RUSSIAN_LOCALE;
    }

    public static Locale getRussianLocale() {
        return RUSSIAN_LOCALE;
    }

    public static Locale getEnglishLocale() {
        return ENGLISH_LOCALE;
    }

    public static int getCurrentScale() {
        return 2;
    }

    public static String formatBigDecimal(BigDecimal decimal) {
        BigDecimal decimalCopy = decimal.setScale(getCurrentScale(), RoundingMode.HALF_DOWN);
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(getCurrentLocale());
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(true);
        return df.format(decimalCopy);
    }
}

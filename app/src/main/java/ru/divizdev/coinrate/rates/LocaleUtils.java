package ru.divizdev.coinrate.rates;

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

}

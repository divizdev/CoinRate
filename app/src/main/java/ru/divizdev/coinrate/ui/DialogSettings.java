package ru.divizdev.coinrate.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.divizdev.coinrate.R;

/**
 * Created by diviz on 06.02.2018.
 */

public class DialogSettings extends DialogFragment {
    public static final String KEY_NAME_PREF = "pref_currency";



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String cancel = getContext().getString(R.string.cancel);
        final String currencyDefaultValue = getContext().getString(R.string.pref_currency_default_value);
        final String[] stringArray = getContext().getResources().getStringArray(R.array.pref_currency_array);

        String currentValue = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(KEY_NAME_PREF, currencyDefaultValue);
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if(stringArray[i].compareTo(currentValue) == 0)
            {
                index = i;
                break;
            }
        }

        return new AlertDialog.Builder(getActivity()).
                setTitle(R.string.pref_name_currency).
                setSingleChoiceItems(stringArray, index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = stringArray[i];
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(KEY_NAME_PREF, value).apply();
                        dialogInterface.dismiss();

                    }
                }).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create();
    }
}

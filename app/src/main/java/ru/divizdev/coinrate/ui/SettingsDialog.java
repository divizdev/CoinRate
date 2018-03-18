package ru.divizdev.coinrate.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.rates.PreferenceManagerSettings;

/**
 * Created by diviz on 06.02.2018.
 */

public class SettingsDialog extends DialogFragment {

    private INoticeDialogListener _listener;


    public interface INoticeDialogListener {
        void onDialogSettingsSelectedItem(String currency);
        void onDialogSettingsNegativeClick(DialogFragment dialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String cancel = getContext().getString(R.string.cancel);
        final String currencyDefaultValue = getContext().getString(R.string.pref_currency_default_value);
        final String[] stringArray = getContext().getResources().getStringArray(R.array.pref_currency_array);

        String currentValue = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(PreferenceManagerSettings.KEY_NAME_PREF, currencyDefaultValue);
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].compareTo(currentValue) == 0) {
                index = i;
                break;
            }
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pref_name_currency)
                .setSingleChoiceItems(stringArray, index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = stringArray[i];
                        _listener.onDialogSettingsSelectedItem(value);
                        dialogInterface.dismiss();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _listener.onDialogSettingsNegativeClick(SettingsDialog.this);
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getTargetFragment() instanceof INoticeDialogListener){
            _listener = (INoticeDialogListener) getTargetFragment();

        }else {
            throw new RuntimeException(getTargetFragment().toString()
                    + " must implement INoticeDialogListener");
        }
    }


}

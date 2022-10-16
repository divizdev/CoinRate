package ru.divizdev.coinrate.presentation.coinRateList.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;

/**
 * Created by diviz on 06.02.2018.
 */

public class SettingsDialog extends DialogFragment {

    private INoticeDialogListener _listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String cancel = getContext().getString(R.string.cancel);
        final String currencyDefaultValue = getContext().getString(R.string.pref_currency_default_value);
        final String[] stringArray = getContext().getResources().getStringArray(R.array.pref_currency_array);

        String currentValue = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(PreferenceManagerSettings.KEY_NAME_PREF,
                currencyDefaultValue);
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].compareTo(currentValue) == 0) {
                index = i;
                break;
            }
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pref_name_currency)
                .setSingleChoiceItems(stringArray, index, (dialogInterface, i) -> {
                    String value = stringArray[i];
                    _listener.onDialogSettingsSelectedItem(value);
                    dialogInterface.dismiss();

                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    _listener.onDialogSettingsNegativeClick(SettingsDialog.this);
                    dialogInterface.dismiss();
                })
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getTargetFragment() instanceof INoticeDialogListener) {
            _listener = (INoticeDialogListener) getTargetFragment();

        } else {
            throw new RuntimeException(getTargetFragment().toString()
                    + " must implement INoticeDialogListener");
        }
    }

    public interface INoticeDialogListener {
        void onDialogSettingsSelectedItem(String currency);

        void onDialogSettingsNegativeClick(DialogFragment dialog);
    }


}
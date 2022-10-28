package ru.divizdev.coinrate.presentation.coinRateList.ui

import android.app.Dialog
import android.os.Bundle
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.data.PreferenceManagerSettings
import android.content.DialogInterface
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.lang.RuntimeException

/**
 * Created by diviz on 06.02.2018.
 */
class SettingsDialog : DialogFragment() {
    private var _listener: INoticeDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cancel = requireContext().getString(R.string.cancel)
        val currencyDefaultValue = requireContext().getString(R.string.pref_currency_default_value)
        val stringArray = requireContext().resources.getStringArray(R.array.pref_currency_array)
        val currentValue = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString(
            PreferenceManagerSettings.KEY_NAME_PREF,
            currencyDefaultValue
        )
        var index = 0
        for (i in stringArray.indices) {
            if (stringArray[i].compareTo(currentValue!!) == 0) {
                index = i
                break
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.pref_name_currency)
            .setSingleChoiceItems(stringArray, index) { dialogInterface: DialogInterface, i: Int ->
                val value = stringArray[i]
                _listener?.onDialogSettingsSelectedItem(value)
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                _listener?.onDialogSettingsNegativeClick(this@SettingsDialog)
                dialogInterface.dismiss()
            }
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _listener = if (targetFragment is INoticeDialogListener) {
            targetFragment as INoticeDialogListener?
        } else {
            throw RuntimeException(
                targetFragment.toString()
                        + " must implement INoticeDialogListener"
            )
        }
    }

    interface INoticeDialogListener {
        fun onDialogSettingsSelectedItem(currency: String)
        fun onDialogSettingsNegativeClick(dialog: DialogFragment)
    }
}
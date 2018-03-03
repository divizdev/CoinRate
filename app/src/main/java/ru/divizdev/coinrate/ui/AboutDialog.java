package ru.divizdev.coinrate.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import ru.divizdev.coinrate.R;


public class AboutDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String version = "1.0";
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        View aboutPage = new AboutPage(getContext())
                .setDescription(getContext().getResources().getString(R.string.about_description))
                .addItem(new Element().setTitle("Version " + version))
                .addWebsite("http://divizdev.ru")
                .addPlayStore("ru.divizdev.coinrate")
                .addGitHub("divizdev/CoinRate")
                .create();


        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pref_name_currency)
                .setView(aboutPage)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

    }
}

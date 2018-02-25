package ru.divizdev.coinrate.ui;

import android.app.Dialog;
import android.content.DialogInterface;
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


        View aboutPage = new AboutPage(getContext())
                .setDescription(getContext().getResources().getString(R.string.about_description))
                .addItem(new Element().setTitle("Version 1.0"))
                .addEmail("diviz.dev@gmail.com")
                .addWebsite("http://divizdev.ru")
                .addTwitter("@divizdev")
                .addPlayStore("com.ideashower.readitlater.pro")
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

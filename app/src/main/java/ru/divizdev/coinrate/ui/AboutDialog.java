package ru.divizdev.coinrate.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import ru.divizdev.coinrate.R;


public class AboutDialog extends DialogFragment {

    private static final int LINKS_COLUMNS_COUNT = 2;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AboutView aboutPage = AboutBuilder.with(getContext())
                .setBrief(R.string.about_description)
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addGitHubLink(R.string.git_hub_link)
                .addWebsiteLink(R.string.website_link)
//                .addGooglePlayStoreLink(R.string.url_play_store)
                .setLinksColumnsCount(LINKS_COLUMNS_COUNT)
                .setShowDivider(false)
                .setVersionNameAsAppSubTitle()
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();


        return new AlertDialog.Builder(getActivity())
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

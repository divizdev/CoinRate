package ru.divizdev.coinrate.presentation.about;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import java.util.Objects;

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


        return new AlertDialog.Builder(requireActivity())
                .setView(aboutPage)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create();

    }
}

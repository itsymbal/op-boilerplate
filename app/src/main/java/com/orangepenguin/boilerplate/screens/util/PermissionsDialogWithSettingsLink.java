package com.orangepenguin.boilerplate.screens.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.orangepenguin.boilerplate.R;

import java.util.List;

public class PermissionsDialogWithSettingsLink extends DialogFragment {

    private static final String ARG_INT_SUGGESTION_STRING_ID =
            PermissionsDialogWithSettingsLink.class + "suggestionTextStringId";

    public static final Intent SETTINGS_INTENT =
            new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

    public static PermissionsDialogWithSettingsLink createInstance(int suggestionTextStringId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_INT_SUGGESTION_STRING_ID, suggestionTextStringId);

        PermissionsDialogWithSettingsLink instance = new PermissionsDialogWithSettingsLink();
        instance.setArguments(arguments);
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String message = getString(getArguments().getInt(ARG_INT_SUGGESTION_STRING_ID)) + ' ';

        if (targetActivityExists(getActivity(), SETTINGS_INTENT)) {
            SETTINGS_INTENT.setData(Uri.parse("package:" + getActivity().getPackageName()));
            message += getString(R.string.permission_open_settings_screen);
            builder.setPositiveButton(R.string.generic_ok, (dialog, id) -> startActivity(SETTINGS_INTENT));
        } else {
            message += getString(R.string.permission_open_settings_suggestion);
        }
        builder.setMessage(message);
        builder.setNegativeButton(R.string.generic_cancel, null);

        return builder.create();
    }

    private boolean targetActivityExists(Context context, Intent intent) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfos.isEmpty();
    }
}

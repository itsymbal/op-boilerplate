package com.orangepenguin.boilerplate.screens.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.util.PermissionApi;

public class PermissionsDialogWithRationale extends DialogFragment {

    private static final String ARG_PERMS =
            PermissionsDialogWithRationale.class.getName() + "permissions";
    private static final String ARG_INT_MESSAGE_ID =
            PermissionsDialogWithRationale.class.getName() + "messageId";
    private static final String ARG_INT_REQUEST_CODE =
            PermissionsDialogWithRationale.class.getName() + "requestCode";

    public static PermissionsDialogWithRationale createInstance(int messageId, String[] permissions, int requestCode) {
        Bundle params = new Bundle();
        params.putStringArray(ARG_PERMS, permissions);
        params.putInt(ARG_INT_MESSAGE_ID, messageId);
        params.putInt(ARG_INT_REQUEST_CODE, requestCode);

        PermissionsDialogWithRationale instance = new PermissionsDialogWithRationale();
        instance.setArguments(params);
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(getString(getArguments().getInt(ARG_INT_MESSAGE_ID)))
                .setPositiveButton(R.string.generic_ok,
                        (dialog, id) -> PermissionApi.Companion
                                .requestPermissionsFromActivity(getActivity(),
                                        getArguments().getInt(ARG_INT_REQUEST_CODE),
                                        getArguments().getStringArray(ARG_PERMS)))
                .create();
    }
}

package com.android.wardrobeManager.ui.util;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;

public class WardrobeAlerts {

    public static void showRadioButtonDialog(final Context context, String groupName, final String[] choices, final RadioButtonAlertCallback callback) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
        alt_bld.setTitle(groupName);
        alt_bld.setSingleChoiceItems(choices, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                callback.onClick(context, choices[item], item);
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    public interface RadioButtonAlertCallback {

        void onClick(Context context, String selectedItem, int itemIndex);

    }

}

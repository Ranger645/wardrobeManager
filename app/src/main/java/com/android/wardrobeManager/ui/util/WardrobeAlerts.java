package com.android.wardrobeManager.ui.util;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.EditText;

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

    public static void showStringInputDialog(final Context context, String title, String defaultValue, int inputType, final InputAlertCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        final EditText inputView = new EditText(context);
        inputView.setText(defaultValue);
        inputView.setInputType(inputType);
        builder.setView(inputView);

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onEnterPressed(context, inputView.getText().toString());
            }
        });

        builder.show();
    }

    public interface InputAlertCallback {
        void onEnterPressed(Context context, String value);
    }

}

package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.view.View;

import com.android.wardrobeManager.ui.util.WardrobeAlerts;

import androidx.annotation.NonNull;

public class AdvEditTextEnterButton extends AdvEditButton {

    private int inputType;
    private String defaultValue;
    private WardrobeAlerts.InputAlertCallback inputAlertCallback;

    public AdvEditTextEnterButton(String title, String defaultValue, int inputType, @NonNull Context context) {
        super(title, context);
        this.inputType = inputType;
        this.defaultValue = defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        refreshInputAlertCallback();
    }

    public void setInputAlertCallback(WardrobeAlerts.InputAlertCallback callback) {
        this.inputAlertCallback = callback;
        refreshInputAlertCallback();
    }

    public void refreshInputAlertCallback() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WardrobeAlerts.showStringInputDialog(getContext(), title, defaultValue, inputType, inputAlertCallback);
            }
        });
    }

}

package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.view.View;

import com.android.wardrobeManager.ui.util.WardrobeAlerts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdvEditRadioButton extends AdvEditButton {

    private String[] options;
    private WardrobeAlerts.RadioButtonAlertCallback onRadioClickListener;

    public AdvEditRadioButton(String title, String[] options, @NonNull Context context) {
        super(title, context);
        this.options = options;

        // Default listener: does nothing
        setOnRadioClickListener(new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {

            }
        });
    }

    public void setOnRadioClickListener(WardrobeAlerts.RadioButtonAlertCallback l) {
        onRadioClickListener = l;
        refreshOnClickListener();
    }

    public void setOptions(String[] options) {
        this.options = options;
        refreshOnClickListener();
    }

    private void refreshOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WardrobeAlerts.showRadioButtonDialog(getContext(), title, options, onRadioClickListener);
            }
        });
    }
}

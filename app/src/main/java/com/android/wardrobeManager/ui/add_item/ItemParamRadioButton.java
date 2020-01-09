package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wardrobeManager.ui.util.WardrobeAlerts;

public class ItemParamRadioButton extends ItemParamButton {

    public ItemParamRadioButton(final String name, String defaultValue, final String[] choices, LayoutInflater inflater, final ViewGroup container, final WardrobeAlerts.RadioButtonAlertCallback onClickCallback) {
        super(name, defaultValue, inflater, container);
        this.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WardrobeAlerts.showRadioButtonDialog(container.getContext(), name, choices, new WardrobeAlerts.RadioButtonAlertCallback() {
                    @Override
                    public void onClick(Context context, String selectedItem, int itemIndex) {
                        onClickCallback.onClick(context, selectedItem, itemIndex);
                    }
                });
            }
        });
    }

}

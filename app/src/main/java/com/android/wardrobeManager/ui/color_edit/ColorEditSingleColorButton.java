package com.android.wardrobeManager.ui.color_edit;

import android.view.View;

import com.android.wardrobeManager.R;

import androidx.appcompat.widget.AppCompatButton;

public class ColorEditSingleColorButton extends AppCompatButton {

    private int color = 0xFFFFFFFF;

    public ColorEditSingleColorButton(final ColorEditActivity activity, int color) {
        super(activity);
        this.color = color;

    }

    public int getColor() {
        return color;
    }

}

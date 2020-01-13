package com.android.wardrobeManager.ui.color_edit;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.android.wardrobeManager.R;

import androidx.appcompat.widget.AppCompatButton;

public class ColorEditSingleColorButton extends AppCompatButton {

    private int color = 0xFFFFFFFF;

    public ColorEditSingleColorButton(final ColorEditActivity activity, int color) {
        super(activity);
        this.color = color;

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
}

package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.wardrobeManager.R;

import androidx.annotation.NonNull;

public class AdvEditButton extends FrameLayout {

    public AdvEditButton(String title, @NonNull Context context) {
        super(context);
        initView(title, context);
    }

    public void initView(String title, Context context) {
        setClickable(true);

        View view = inflate(context, R.layout.additem_edit_button, null);
        TextView titleTextView = view.findViewById(R.id.title_field);
        titleTextView.setText(title);
        addView(view);
    }

    public void setValue(String value) {
        TextView titleTextView = findViewById(R.id.value_field);
        titleTextView.setText(value);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width / getResources().getInteger(R.integer.additem_advedit_button_wh_ratio);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

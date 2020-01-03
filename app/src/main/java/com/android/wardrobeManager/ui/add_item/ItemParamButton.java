package com.android.wardrobeManager.ui.add_item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wardrobeManager.R;

public class ItemParamButton {

    private View view;

    public ItemParamButton(String title, String defaultValue, LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.additem_edit_button, container, false);

        TextView titleView = view.findViewById(R.id.title_field);
        titleView.setText(title);
        TextView valueView = view.findViewById(R.id.value_field);
        valueView.setText(defaultValue);

        container.addView(view);
    }

    public void setOnClick(View.OnClickListener onClickListener) {
        view.setOnClickListener(onClickListener);
    }

}

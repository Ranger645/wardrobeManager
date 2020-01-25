package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class AddItemViewPager extends ViewPager {

    private boolean swipingEnabled = true;

    public AddItemViewPager(@NonNull Context context) {
        super(context);
    }

    public AddItemViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return swipingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return swipingEnabled && super.onTouchEvent(ev);
    }

    public void setSwipingEnabled(boolean swipingEnabled) {
        this.swipingEnabled = swipingEnabled;
    }
}

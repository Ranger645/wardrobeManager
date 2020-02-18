package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewColorBar extends View {

    public ViewColorBar(Context context) {
        super(context);
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect fullCanvas = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(fullCanvas, paint);
    }
}

package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class VerticalStripes implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int thickness;

    public VerticalStripes() {
        thickness = 10;
    }

    public VerticalStripes(int thickness) {
        this.thickness = thickness;
    }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        int colorIndex = 0;
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        for (int i = 0; i < bitmap.getWidth(); i += thickness) {
            paint.setColor(colors[colorIndex]);
            canvas.drawRect(i, 0, i + thickness, bitmap.getHeight(), paint);
            colorIndex = (colorIndex + 1) % colors.length;
        }
        return bitmap;
    }
}
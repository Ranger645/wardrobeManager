package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class DiagonalUpRight implements DesignFilterManager.DesignFilter {
    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        int colorIndex = 0;
        int thickness = 30;
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setStrokeWidth(thickness);

        for (int i = 2 * bitmap.getHeight(); i > 0; i -= thickness) {
            paint.setColor(colors[colorIndex]);
            canvas.drawLine(0, i, bitmap.getWidth(), i - bitmap.getHeight(), paint);
            colorIndex = (colorIndex + 1) % colors.length;
        }
        return bitmap;
    }
}

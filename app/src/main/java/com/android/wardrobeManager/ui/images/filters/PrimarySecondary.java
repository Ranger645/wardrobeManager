package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import com.android.wardrobeManager.ui.images.DesignFilterManager;
import java.util.Map;

public class PrimarySecondary implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(colors[0]);
        int top = 0;
        int bottom = bitmap.getHeight();

        for (int color : colors) {
            paint.setColor(color);
            canvas.drawRect(0, top, bitmap.getWidth(), bottom, paint);
            int third = (bottom - top) / 3;
            top += third;
            bottom -= third;
            if (bottom - top <= 2) {
                break;
            }
        }
        return bitmap;
    }
}

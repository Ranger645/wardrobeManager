package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class HorizontalStripes implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int thickness;
    private int colorIndex;

    public HorizontalStripes(int thickness) {
        this.thickness = thickness;
    }

    public HorizontalStripes() {
        this(10);
    }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        bitmap = ref.copy(ref.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        colorIndex = 0;

        for (int i = 0; i < bitmap.getHeight(); i += thickness) {
            paint.setColor(colors[colorIndex]);
            canvas.drawRect(0, i, bitmap.getWidth(), i + thickness, paint);
            colorIndex = (colorIndex + 1) % colors.length;
        }
        return bitmap;
    }
}

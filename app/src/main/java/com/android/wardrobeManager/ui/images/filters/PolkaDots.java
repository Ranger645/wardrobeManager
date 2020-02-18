package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class PolkaDots implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int SPACING = 80;
    private int DOT_RADIUS = 10;
    private int colorIndex = 0;
    private int indent = 0;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

        bitmap = ref.copy(ref.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(colors[0]);
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        for (int i = 0; i < bitmap.getHeight(); i += SPACING / 2) {

            if (indent == 0) {
                indent += SPACING / 2;
            } else {
                indent = 0;
            }

            for (int j = 0; j < bitmap.getHeight(); j += SPACING) {

                int dotColor;

                if (colors.length <= 1) {
                    dotColor = colors[0];
                } else {
                    colorIndex = 1 + (colorIndex % (colors.length - 1));
                    dotColor = colors[colorIndex];
                }

                paint.setColor(dotColor);
                canvas.drawCircle(j + indent, i, DOT_RADIUS, paint);

            }
        }
        return bitmap;
    }
}
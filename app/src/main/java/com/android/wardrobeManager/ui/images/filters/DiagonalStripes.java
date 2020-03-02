package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class DiagonalStripes implements DesignFilterManager.DesignFilter {

    int thickness;
    int angle;

    public DiagonalStripes(int thickness, int angle) { this.thickness = thickness; this.angle = angle; }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        int colorIndex = 0;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2, false);
        Canvas canvas = new Canvas(scaledBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setStrokeWidth(thickness);

        canvas.rotate(angle, scaledBitmap.getWidth() / 2, scaledBitmap.getHeight() / 2);
        canvas.scale(2, 2, scaledBitmap.getWidth() / 2, scaledBitmap.getHeight() / 2);

        for (int i = 0; i < scaledBitmap.getHeight(); i += thickness / 2) {
            paint.setColor(colors[colorIndex]);
            canvas.drawRect(0, i, scaledBitmap.getWidth(), i + thickness / 2, paint);
            colorIndex = (colorIndex + 1) % colors.length;
        }
        return scaledBitmap;
    }
}

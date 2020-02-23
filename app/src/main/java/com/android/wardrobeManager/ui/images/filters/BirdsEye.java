package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

public class BirdsEye implements DesignFilterManager.DesignFilter {
    int checkerSize = 4;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        bitmap = ref.copy(ref.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        for (int i = 0; i < bitmap.getHeight(); i += checkerSize) {
            for (int j = 0; j < bitmap.getWidth(); j += checkerSize) {
                if ((i / checkerSize) % 2 == (j / checkerSize) % 2) {
                    paint.setColor(colors[0]);
                    canvas.drawRect(i, j, i + checkerSize, j + checkerSize, paint);
                }
            }
        }
        return bitmap;
    }
}
package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

public class Checker implements DesignFilterManager.DesignFilter {
    int checkerSize;
    int colorIndex = 0;

    public Checker(int checkerSize) { this.checkerSize = checkerSize; }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(colors[0]);
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        for (int i = 0; i < bitmap.getHeight(); i += checkerSize) {
            for (int j = 0; j < bitmap.getWidth(); j += checkerSize) {
                if ((i + j) / checkerSize % 2 == 0) {
                    if (colors.length == 1) {
                        paint.setColor(Color.WHITE);
                    } else {
                        // The first color index is displayed in half of the squares
                        // while the rest of the colors are cycled through
                        colorIndex = (colorIndex + 1) % (colors.length - 1);
                        paint.setColor(colors[colorIndex + 1]);
                    }
                    canvas.drawRect(i, j, i + checkerSize, j + checkerSize, paint);
                }
            }
        }
        return bitmap;
    }
}

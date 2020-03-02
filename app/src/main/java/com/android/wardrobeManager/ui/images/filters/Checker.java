package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.WardrobeManager;
import com.android.wardrobeManager.ui.images.DesignFilterManager;

public class Checker implements DesignFilterManager.DesignFilter {
    int checkerSize;

    boolean useBirdsEye = false;

    public Checker(int checkerSize) { this.checkerSize = checkerSize; }

    public Checker(int checkerSize, boolean useBirdsEye) { this.checkerSize = checkerSize; this.useBirdsEye = useBirdsEye; }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(colors[0]);
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        for (int i = 0; i < bitmap.getWidth(); i += checkerSize) {

            // This consistently cycles the starting row color so the pattern looks
            // better when there are more than two colors selected
            int colorIndex;
            if (colors.length > 1) {
                colorIndex = (i / checkerSize + 1) / 2 % (colors.length - 1);
            } else {
                colorIndex = 0;
            }

            for (int j = 0; j < bitmap.getHeight(); j += checkerSize) {
                if ((i + j) / checkerSize % 2 == 0) {
                    if (colors.length == 1) {
                        if (useBirdsEye) {
                            paint.setColor(ContextCompat.getColor(WardrobeManager.getContext(), R.color.clothingItemAutoImageDefaultColor1));
                        } else {
                            paint.setColor(ContextCompat.getColor(WardrobeManager.getContext(), R.color.clothingItemAutoImageDefaultColor2));
                        }
                    } else {
                        // The first color index is displayed in half of the squares
                        // while the rest of the colors are cycled through
                        paint.setColor(colors[colorIndex + 1]);
                        colorIndex = (colorIndex + 1) % (colors.length - 1);
                    }
                    canvas.drawRect(j, i, j + checkerSize, i + checkerSize, paint);
                }
            }
        }
        return bitmap;
    }
}

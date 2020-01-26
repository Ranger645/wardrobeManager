package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Map;

public class DiagonalDownRight implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int thickness = 20;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

        int yStart = bitmap.getHeight() - 1;
        int cycleColors = 0;
        int selectedColor = 0;

        while (yStart >= 0) {

            int x = 0;
            int y = yStart;
            while (x < bitmap.getWidth() && y < bitmap.getHeight()) {
                if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                    bitmap.setPixel(x, y, colors[selectedColor]);
                }
                x++;
                y++;
            }

            yStart--;
            cycleColors++;
            selectedColor = cycleColors / thickness % colors.length;

        }

        int xStart = 0;

        while (xStart < bitmap.getWidth()) {

            int x = xStart;
            int y = 0;
            while (x < bitmap.getWidth() && y < bitmap.getHeight()) {
                if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                    bitmap.setPixel(x, y, colors[selectedColor]);
                }
                x++;
                y++;
            }

            xStart++;
            cycleColors++;
            selectedColor = cycleColors / thickness % colors.length;

        }


        return bitmap;
    }
}
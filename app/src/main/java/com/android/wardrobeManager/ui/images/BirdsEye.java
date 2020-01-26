package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Map;

public class BirdsEye implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    int checkerSize = 6;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

        for (int i = 0; i < bitmap.getHeight() * bitmap.getWidth(); i++) {

            int x = i % bitmap.getWidth();
            int y = i / bitmap.getWidth();


            if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                if (x / checkerSize % 2 == y / checkerSize % 2) {
                    bitmap.setPixel(x, y, colors[0]);
                } else {
                    if (colors.length == 1) {
                        bitmap.setPixel(x, y, Color.BLACK);
                    } else {
                        bitmap.setPixel(x, y, colors[1]);
                    }

                }
            }

        }


        return bitmap;
    }
}
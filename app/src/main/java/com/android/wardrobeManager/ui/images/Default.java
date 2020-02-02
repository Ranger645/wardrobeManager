package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Map;

public class Default implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

            for (int i = 0; i < bitmap.getWidth() * bitmap.getHeight(); i++) {

                int x = i % bitmap.getWidth();
                int y = i / bitmap.getWidth();

                if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                    if (colors.length > 0) {
                        bitmap.setPixel(x, y, colors[0]);
                    } else {
                        bitmap.setPixel(x, y, Color.RED);
                    }

                }
            }

        return bitmap;
    }
}

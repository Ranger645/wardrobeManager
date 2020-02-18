package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class VerticalStripes implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int thickness;

    public VerticalStripes() {
        thickness = 10;
    }

    public VerticalStripes(int thickness) {
        this.thickness = thickness;
    }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {


        for (int i = 0; i < bitmap.getWidth() * bitmap.getHeight(); i++) {
            int x = i % bitmap.getWidth();
            int y = i / bitmap.getWidth();

            if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                int stripeColor = colors[(x / thickness) % (colors.length)];

                bitmap.setPixel(i % bitmap.getWidth(), i / bitmap.getWidth(), stripeColor);

            }
        }
        return bitmap;
    }
}
package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class HorizontalStripes implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int thickness;

    public HorizontalStripes(int thickness) {
        this.thickness = thickness;
    }

    public HorizontalStripes() {
        this(10);
    }

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

        for (int i = 0; i < bitmap.getWidth() * bitmap.getHeight(); i++) {
            int x = i % bitmap.getWidth();
            int y = i / bitmap.getWidth();

            if (Color.alpha(ref.getPixel(x, y)) != 0) {
                int stripeColor = colors[(y / thickness) % (colors.length)];

                bitmap.setPixel(i % bitmap.getWidth(), i / bitmap.getWidth(), stripeColor);

            }
        }
        return bitmap;
    }
}

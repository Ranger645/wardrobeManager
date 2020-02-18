package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class PrimarySecondary implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        for(int n = 0; n < bitmap.getHeight(); n++) {
            int color = 0;
            int ymin = 0, ymax = bitmap.getHeight();
            int div = bitmap.getHeight() / 3;
            while (color + 1 < colors.length && ymin + div < n && n < ymax - div) {
                ymin += div;
                ymax -= div;
                div /= 3;
                color++;
            }
            for (int i = 0; i < bitmap.getWidth(); i++) {
                if (ref.getPixel(i, n) != 0xFFFFFFFF) {
                    bitmap.setPixel(i, n, colors[color]);
                }
            }
        }
        return bitmap;
    }
}

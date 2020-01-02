package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ColorStyleFilterManager {

    protected Map<String, ColorStyleFilter> filters;

    public static Bitmap filterColorStyle(String colorStyle, Bitmap bitmap, Bitmap ref, int[] colors) {
        return new ColorStyleFilterManager().filter(colorStyle, bitmap, ref, colors);
    }

    public ColorStyleFilterManager() {
        filters = new HashMap<>();

        // TODO: MOVE THIS TO INDIVIDUAL CLASSES
        filters.put("PRIMARY_SECONDARY", new ColorStyleFilter() {
            @Override
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
        });
    }

    public Bitmap filter(String colorStyle, Bitmap bitmap, Bitmap ref, int[] colors) {
        return this.filters.get(colorStyle).filter(bitmap, ref, colors);
    }

    protected interface ColorStyleFilter {
        Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors);
    }

}

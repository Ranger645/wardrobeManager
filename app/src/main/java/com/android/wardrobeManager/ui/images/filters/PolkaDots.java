package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class PolkaDots implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int spacing = 80;
    private int dotRadius = 10;
    private int colorIndex = 0;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {


        for (int i = 0; i < bitmap.getWidth() * bitmap.getHeight(); i++) {

            int x = i % bitmap.getWidth();
            int y = i / bitmap.getWidth();

            if (colors.length < 2) {
                if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                    bitmap.setPixel(x, y, 0xFFEEEEEE);
                }
            } else {
                if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                    bitmap.setPixel(x, y, colors[0]);
                }
            }
        }

        for (int i = 0; i < bitmap.getHeight(); i += spacing / 2) {
            for (int j = 0; j < bitmap.getWidth(); j += spacing) {

                int dotColor;

                if (colors.length <= 1) {
                    dotColor = colors[0];
                } else {
                    colorIndex++;
                    if (colorIndex == colors.length) {
                        colorIndex = 1;
                    }
                    dotColor = colors[colorIndex];
                }

                int indent = i % spacing;

                for (int x = j + indent - dotRadius; x < j + indent + dotRadius; x++) {
                    for (int y = i - dotRadius; y < i + dotRadius; y++) {

                        if (x >= 0 && x < bitmap.getWidth() && y >= 0 && y < bitmap.getHeight()) {

                            if (Math.pow(i - y, 2) + Math.pow(j + indent - x, 2) <= Math.pow(dotRadius, 2)) {
                                if (ref.getPixel(x, y) != 0xFFFFFFFF) {
                                    bitmap.setPixel(x, y, dotColor);
                                }
                            }
                        }
                    }
                }
            }
        }
        return bitmap;
    }
}
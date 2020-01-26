package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.Map;

public class HorizontalGradient implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

        Log.d("qqq", "A");
        boolean defaultColoring;
        int sections;

        if (colors.length == 1) {
            defaultColoring = true;
            sections = 1;
        } else {
            defaultColoring = false;
            sections = colors.length - 1;
        }

        for (int i = 0; i < sections; i++) {

            int gradientStart;
            int gradientEnd;

            if (defaultColoring) {
                gradientStart = Color.argb(Color.alpha(colors[0]), Color.red(colors[0]) / 2, Color.green(colors[0]) / 2, Color.blue(colors[0]) / 2);
                gradientEnd = colors[0];
            } else {
                gradientStart = colors[i];
                gradientEnd = colors[i + 1];
            }

            int sectionHeight = bitmap.getHeight() / sections;

            for (int j = 0; j < sectionHeight; j++) {

                int red = Color.red(gradientStart) + (int)((Color.red(gradientEnd) - Color.red(gradientStart)) * ((float)j / sectionHeight));
                int green = Color.green(gradientStart) + (int)((Color.green(gradientEnd) - Color.green(gradientStart)) * ((float)j / sectionHeight));
                int blue = Color.blue(gradientStart) + (int)((Color.blue(gradientEnd) - Color.blue(gradientStart)) * ((float)j / sectionHeight));
                int alpha = Color.alpha(gradientStart) + (int)((Color.alpha(gradientEnd) - Color.alpha(gradientStart)) * ((float)j / sectionHeight));

                int gradientRow = Color.argb(alpha, red, green, blue);

                for (int y = 0; y < bitmap.getHeight(); y++) {
                    if (ref.getPixel(bitmap.getWidth() / sections * i + j, y) != 0xFFFFFFFF) {
                        bitmap.setPixel(bitmap.getWidth() / sections * i + j, y, gradientRow);
                    }
                }


            }

        }
        return bitmap;
    }
}
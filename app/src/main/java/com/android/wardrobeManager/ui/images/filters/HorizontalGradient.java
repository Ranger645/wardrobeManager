package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class HorizontalGradient implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    boolean defaultColoring;
    int sections;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        bitmap = ref.copy(ref.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(colors[0]);

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
                int gradientColumn = Color.argb(alpha, red, green, blue);
                int columnXPosition = bitmap.getWidth() / sections * i + j;
                paint.setColor(gradientColumn);
                canvas.drawRect(columnXPosition, 0, columnXPosition + 1, bitmap.getHeight(), paint);
            }
        }
        return bitmap;
    }
}

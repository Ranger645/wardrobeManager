package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import com.android.wardrobeManager.ui.images.DesignFilterManager;

public class HorizontalGradient implements DesignFilterManager.DesignFilter {
    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(colors[0]);
        int sections = Math.max(colors.length - 1, 1);

        for (int i = 0; i < sections; i++) {
            int gradientStart;
            int gradientEnd;

            if (colors.length == 1) {
                gradientStart = Color.argb(Color.alpha(colors[0]), Color.red(colors[0]) / 2, Color.green(colors[0]) / 2, Color.blue(colors[0]) / 2);
                gradientEnd = colors[0];
            } else {
                gradientStart = colors[i];
                gradientEnd = colors[i + 1];
            }

            int width = bitmap.getWidth() / sections;
            paint.setShader(new LinearGradient(width * i, 0, width * (i + 1), 0, gradientStart, gradientEnd, Shader.TileMode.MIRROR));
            canvas.drawRect(width * i, 0, width * (i + 1), bitmap.getHeight(), paint);
        }
        return bitmap;
    }
}
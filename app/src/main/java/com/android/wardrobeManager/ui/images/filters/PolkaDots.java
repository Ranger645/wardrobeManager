package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class PolkaDots implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;
    private int spacing = 80;
    private int dotRadius = 10;
    private int colorIndex = 0;
    private int indent = 0;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {


        Bitmap output = ref.copy(ref.getConfig(), true);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        paint.setColor(colors[0]);
        canvas.drawRect(0, 0, output.getWidth(), output.getHeight(), paint);

        for (int i = 0; i < output.getHeight(); i += spacing / 2) {

            if (indent == 0) {
                indent += spacing / 2;
            } else {
                indent = 0;
            }

            for (int j = 0; j < output.getHeight(); j += spacing) {

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

                paint.setColor(dotColor);
                canvas.drawCircle(j + indent, i, dotRadius, paint);

            }
        }

        return output;

    }
}
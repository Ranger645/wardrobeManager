package com.android.wardrobeManager.ui.images.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.android.wardrobeManager.ui.images.DesignFilterManager;

import java.util.Map;

public class Default implements DesignFilterManager.DesignFilter {

    protected Map<String, DesignFilterManager.DesignFilter> filters;

    public Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors) {

        Bitmap output = ref.copy(ref.getConfig(), true);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        paint.setColor(colors[0]);

        canvas.drawRect(0, 0, output.getWidth(), output.getHeight(), paint);

        return output;

    }



}

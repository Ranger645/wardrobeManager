package com.android.wardrobeManager.ui.color_edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class ColorEditPreview extends View {

    private static final int COLOR_INDICATOR_THICKNESS = 30;
    private static final int COLOR_INDICATOR_RADIUS = 75;
    private int touchX = -100, touchY = -100;

    private Bitmap imageBitmap;

    public ColorEditPreview(Context context, Bitmap imageBitmap) {
        super(context);
        this.imageBitmap = imageBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect fullCanvas1 = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint defaultBrush = new Paint();
        canvas.drawBitmap(imageBitmap, null, fullCanvas1, defaultBrush);

        float touchXf = touchX;
        float touchYf = touchY;
        if (touchX >= 0 && touchY >= 0 && touchX < canvas.getWidth() && touchY < canvas.getHeight()) {
            int scaledX = (int) ((touchXf / canvas.getWidth()) * imageBitmap.getWidth());
            int scaledY = (int) ((touchYf / canvas.getHeight()) * imageBitmap.getHeight());

            Paint thickBrush = new Paint();
            thickBrush.setColor(imageBitmap.getPixel(scaledX, scaledY));
            thickBrush.setStyle(Paint.Style.STROKE);
            thickBrush.setStrokeWidth(COLOR_INDICATOR_THICKNESS);

            Paint thinBrush = new Paint();
            thinBrush.setColor(Color.BLACK);
            thinBrush.setStyle(Paint.Style.STROKE);
            thinBrush.setStrokeWidth(4);

            canvas.drawCircle(touchX, touchY, COLOR_INDICATOR_RADIUS - COLOR_INDICATOR_THICKNESS / 2, thinBrush);
            canvas.drawCircle(touchX, touchY, COLOR_INDICATOR_RADIUS, thickBrush);
            canvas.drawCircle(touchX, touchY, COLOR_INDICATOR_RADIUS + COLOR_INDICATOR_THICKNESS / 2, thinBrush);
        }
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = (int) event.getX();
            touchY = (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

        }
        invalidate();
        return true;
    }
}

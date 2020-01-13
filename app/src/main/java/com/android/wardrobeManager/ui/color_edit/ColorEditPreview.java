package com.android.wardrobeManager.ui.color_edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.android.wardrobeManager.R;

public class ColorEditPreview extends View {

    private static final int COLOR_INDICATOR_THICKNESS = 30;
    private static final int COLOR_INDICATOR_RADIUS = 75;

    private int touchX = -100, touchY = -100;

    private Bitmap imageBitmap;
    private boolean customImage = false;

    private ColorSelectListener colorSelectListener;

    public ColorEditPreview(Context context, Bitmap imageBitmap, boolean customImage) {
        super(context);
        this.imageBitmap = imageBitmap;
        this.customImage = customImage;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect fullCanvas1 = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint defaultBrush = new Paint();
        canvas.drawBitmap(imageBitmap, null, fullCanvas1, defaultBrush);

        if (touchX >= 0 && touchY >= 0 && touchX < canvas.getWidth() && touchY < canvas.getHeight()) {
            Paint thickBrush = new Paint();
            thickBrush.setColor(getColorAtViewPoint(touchX, touchY));
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

    public void setColorSelectListener(ColorSelectListener listener) {
        this.colorSelectListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (customImage) {
            if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                touchX = (int) event.getX();
                touchY = (int) event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (colorSelectListener != null)
                    colorSelectListener.onColorSelect(getColorAtViewPoint(touchX, touchY));
                touchX = touchY = -100;
            }
            invalidate();
        }
        return true;
    }

    private int getColorAtViewPoint(float viewX, float viewY) {
        int scaledX = (int) ((viewX / this.getWidth()) * imageBitmap.getWidth());
        int scaledY = (int) ((viewY / this.getHeight()) * imageBitmap.getHeight());
        int color = imageBitmap.getPixel(scaledX, scaledY);
        if ((color & 0xFF000000) == 0)
            return getResources().getColor(R.color.colorAccent, getContext().getTheme());
        return color;
    }

    public interface ColorSelectListener {
        void onColorSelect(int color);
    }
}

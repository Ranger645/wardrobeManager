package com.android.wardrobeManager.ui.color_edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.android.wardrobeManager.R;

public class ManualColorSelectorView extends View {

    private float rotation = 0;

    private float oldX = -1, oldY = -1, newX = -1, newY = -1;

    public ManualColorSelectorView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint defaultBrush = new Paint();
        Bitmap imageBitmap = ManualColorSelectorGraphic.getColorSelectorImage();

        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight();

        if (newX != oldX || newY != oldY) {
            rotation = 180f - (float) Math.toDegrees(Math.atan((centerX - oldX) / (centerY - oldY))) - (float) Math.toDegrees(Math.atan((newX - centerX) / (newY - centerY)));
            rotation = rotation % 360;
            oldX = newX;
            oldY = newY;
        }

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate(-imageBitmap.getWidth() / 2, -imageBitmap.getHeight() / 2); // Centers image
        matrix.postScale(2 * canvas.getHeight() / ((float)imageBitmap.getWidth()), 2 * canvas.getHeight() / ((float)imageBitmap.getHeight()));
        matrix.postRotate(rotation);
        matrix.postTranslate(canvas.getWidth() / 2, canvas.getHeight());
        canvas.drawBitmap(imageBitmap, matrix, defaultBrush);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            newX = event.getX();
            newY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            newX = oldX;
            newY = oldY;
        }

        invalidate();
        return true;
    }

}

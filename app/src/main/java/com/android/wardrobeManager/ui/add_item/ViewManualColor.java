package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.ui.color_edit.ManualColorSelectorGraphic;
import com.android.wardrobeManager.ui.color_edit.ManualColorSelectorView;
import com.android.wardrobeManager.ui.util.Utility;

import androidx.annotation.Nullable;

public class ViewManualColor extends View {

    // TODO: Implement hard coded values based around variable width and height along with functions
    // for computing x, y, w, and h value of different parts of the drawn stuff.

    // Relative to width and height of canvas:
    private static final double COLOR_SELECT_BUTTON_RADIUS_PERCENT = 0.65;
    private static final double COLOR_WHEEL_RADIUS_PERCENT = 0.9;
    private static final double COLOR_WHEEL_BORDER_PERCENT = 0.85;

    private static final int SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD = 20;

    private boolean hasMoved = false;
    private float rotation = 0;
    private float oldX = -1, oldY = -1, newX = -1, newY = -1;

    private Bitmap wheelImage = null;

    private ManualColorSelectorView.ManualColorSelectorUpdateListener colorChangeListener = new ManualColorSelectorView.ManualColorSelectorUpdateListener() {
        @Override
        public void onNewColorSelect(int newColor) {}
    };
    private ManualColorSelectorView.ManualColorSelectorUpdateListener colorSelectListener = new ManualColorSelectorView.ManualColorSelectorUpdateListener() {
        @Override
        public void onNewColorSelect(int newColor) {}
    };

    public ViewManualColor(Context context) {
        super(context);
        wheelImage = Utility.drawableToBitmap(R.drawable.color_selector_wheel);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint defaultBrush = new Paint();
        defaultBrush.setColor(Color.GRAY);
        if (wheelImage == null)
            wheelImage = Utility.drawableToBitmap(R.drawable.color_selector_wheel);

        float colorSelectorWidth = getColorWheelWidth();
        float colorSelectorHeight = colorSelectorWidth;
        float colorSelectorCenterX = canvas.getWidth() / 2;
        float colorSelectorCenterY = canvas.getHeight() / 2;

        // Calculating new angle:
        if (newX != oldX || newY != oldY) {
            double oldAngle = Math.toDegrees(Math.atan((oldX - colorSelectorCenterX) / (oldY - colorSelectorCenterY)));
            oldAngle = translateRelativeAngleToAbsolute(oldAngle, oldX - colorSelectorCenterX, oldY - colorSelectorCenterY);
            double newAngle = Math.toDegrees(Math.atan((newX - colorSelectorCenterX) / (newY - colorSelectorCenterY)));
            newAngle = translateRelativeAngleToAbsolute(newAngle, newX - colorSelectorCenterX, newY - colorSelectorCenterY);
            this.rotation += (oldAngle - newAngle);
            oldX = newX;
            oldY = newY;
        }

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate(-wheelImage.getWidth() / 2, -wheelImage.getHeight() / 2);
        matrix.postScale(colorSelectorWidth / ((float) wheelImage.getWidth()), colorSelectorHeight / ((float) wheelImage.getHeight()));
        matrix.postRotate(rotation);
        matrix.postTranslate(colorSelectorCenterX, colorSelectorCenterY);

        Paint separatorBrush = new Paint();
        separatorBrush.setColor(Color.WHITE);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorWheelBorderRadius(), separatorBrush);
        Paint selectedColor = new Paint();
        if (rotation < 0)
            rotation += 360;
        selectedColor.setColor(ManualColorSelectorGraphic.getColorAtAngle(rotation));
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorSelectButtonRadius(), selectedColor);
        canvas.drawBitmap(wheelImage, matrix, defaultBrush);

    }

    private double translateRelativeAngleToAbsolute(double relativeAngle, float x, float y) {
        if (relativeAngle < 0)
            relativeAngle += 90;
        if (x > 0 && y >= 0)
            return relativeAngle;
        if (x >= 0 && y < 0)
            return relativeAngle + 90;
        if (x <= 0 && y > 0)
            return relativeAngle + 270;
        if (x < 0 && y <= 0)
            return relativeAngle + 180;
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            hasMoved = true;
            newX = event.getX();
            newY = event.getY();
            this.colorChangeListener.onNewColorSelect(ManualColorSelectorGraphic.getColorAtAngle(rotation));
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hasMoved = false;
            oldX = event.getX();
            oldY = event.getY();
            newX = event.getX();
            newY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            double colorSelectButtonRadiusSquared = Math.pow(getColorSelectButtonRadius(), 2);
            if (Utility.distanceSquared(oldX, oldY, newX, newY) < SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD * SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD
                    && Utility.distanceSquared(getWidth() / 2, getHeight() / 2, newX, newY) < colorSelectButtonRadiusSquared) {
                this.colorSelectListener.onNewColorSelect(ManualColorSelectorGraphic.getColorAtAngle(rotation));
            }
            newX = oldX;
            newY = oldY;
        }

        invalidate();
        return true;
    }

    public interface ManualColorSelectorUpdateListener {
        void onNewColorSelect(int newColor);
    }

    protected float getColorWheelWidth() {
        return (float) COLOR_WHEEL_RADIUS_PERCENT * this.getWidth();
    }

    protected float getColorSelectButtonRadius() {
        return (float) COLOR_SELECT_BUTTON_RADIUS_PERCENT * this.getWidth() / 2;
    }

    protected float getColorWheelBorderRadius() {
        return (float) COLOR_WHEEL_BORDER_PERCENT * this.getWidth() / 2;
    }

    public void setColorChangeListener(ManualColorSelectorView.ManualColorSelectorUpdateListener colorChangeListener) {
        this.colorChangeListener = colorChangeListener;
    }

    public void setColorSelectListener(ManualColorSelectorView.ManualColorSelectorUpdateListener colorSelectListener) {
        this.colorSelectListener = colorSelectListener;
    }

    public int getColor() {
        return ManualColorSelectorGraphic.getColorAtAngle(rotation);
    }
}

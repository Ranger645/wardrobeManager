package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.ui.color_edit.ManualColorSelectorGraphic;
import com.android.wardrobeManager.ui.color_edit.ManualColorSelectorView;
import com.android.wardrobeManager.ui.util.Utility;

import androidx.annotation.Nullable;

public class ViewManualColor extends View {

    // Relative to width and height of canvas:
    private static final double COLOR_WHEEL_RING_PERCENT = 0.3;
    private static final double COLOR_WHEEL_RADIUS_PERCENT = 0.9;
    private static final double COLOR_WHEEL_BORDER_PERCENT = 0.85;

    private static final int SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD = 20;

    private boolean hasMoved = false;
    // 0 for none, 1 for color wheel, 2 for gradient wheel
    private int movingWheel = 0;
    private float colorWheelRotation = 0;
    private float gradientWheelRotation = 0;
    private float oldX = -1, oldY = -1, newX = -1, newY = -1;

    private Bitmap wheelImage = null;
    private Paint defaultBrush = new Paint();
    private Paint singleColorBrush = new Paint();
    private Paint gradientWheelBrush = null;

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
            if (movingWheel == 1)
                this.colorWheelRotation += (oldAngle - newAngle);
            else if (movingWheel == 2)
                this.gradientWheelRotation += (oldAngle - newAngle);
            oldX = newX;
            oldY = newY;
        }

        int selectedColor = ManualColorSelectorGraphic.getColorAtAngle(colorWheelRotation);

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate(-wheelImage.getWidth() / 2, -wheelImage.getHeight() / 2);
        matrix.postScale(colorSelectorWidth / ((float) wheelImage.getWidth()), colorSelectorHeight / ((float) wheelImage.getHeight()));
        matrix.postRotate(colorWheelRotation);
        matrix.postTranslate(colorSelectorCenterX, colorSelectorCenterY);

        if (gradientWheelBrush == null) {
            gradientWheelBrush = new Paint();
            gradientWheelBrush.setAntiAlias(true);
        }
        SweepGradient sweepGradient = new SweepGradient(colorSelectorCenterX, colorSelectorCenterY,
                new int[] {selectedColor, 0xFF000000, 0xFFFFFFFF, selectedColor}, null);
        gradientWheelBrush.setShader(sweepGradient);

        canvas.drawBitmap(wheelImage, matrix, defaultBrush);

        canvas.save();
        canvas.rotate((270 + gradientWheelRotation) % 360, colorSelectorCenterX, colorSelectorCenterY);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorGradientWheelRadius(), gradientWheelBrush);
        canvas.restore();

        if (colorWheelRotation < 0)
            colorWheelRotation += 360;
        singleColorBrush.setColor(selectedColor);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorSelectButtonRadius(), singleColorBrush);

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
            this.colorChangeListener.onNewColorSelect(ManualColorSelectorGraphic.getColorAtAngle(colorWheelRotation));
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hasMoved = false;
            oldX = event.getX();
            oldY = event.getY();
            newX = event.getX();
            newY = event.getY();
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            float gradientRadius = getColorGradientWheelRadius();
            if (Utility.distanceSquared(oldX, oldY, centerX, centerY) < gradientRadius * gradientRadius)
                movingWheel = 2;
            else
                movingWheel = 1;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            double colorSelectButtonRadiusSquared = Math.pow(getColorSelectButtonRadius(), 2);
            if (Utility.distanceSquared(oldX, oldY, newX, newY) < SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD * SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD
                    && Utility.distanceSquared(getWidth() / 2, getHeight() / 2, newX, newY) < colorSelectButtonRadiusSquared) {
                this.colorSelectListener.onNewColorSelect(ManualColorSelectorGraphic.getColorAtAngle(colorWheelRotation));
            }
            newX = oldX;
            newY = oldY;
            movingWheel = 0;
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

    protected float getColorGradientWheelRadius() {
        float colorWheelRadius = getColorWheelWidth() / 2;
        return (float) (colorWheelRadius - colorWheelRadius * COLOR_WHEEL_RING_PERCENT);
    }

    protected float getColorSelectButtonRadius() {
        float colorWheelRadius = getColorWheelWidth() / 2;
        return (float) (colorWheelRadius - 2 * colorWheelRadius * COLOR_WHEEL_RING_PERCENT);
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
        return ManualColorSelectorGraphic.getColorAtAngle(colorWheelRotation);
    }
}

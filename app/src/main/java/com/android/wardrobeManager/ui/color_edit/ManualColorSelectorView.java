package com.android.wardrobeManager.ui.color_edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.ui.util.Utility;

public class ManualColorSelectorView extends View {

    // TODO: Implement hard coded values based around variable width and height along with functions
    // for computing x, y, w, and h value of different parts of the drawn stuff.

    // Relative to width and height of canvas:
    private static final double COLOR_SELECT_BUTTON_RADIUS_PERCENT = 0.7;
    private static final double COLOR_WHEEL_RADIUS_PERCENT = 0.9;

    private static final int SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD = 20;

    private boolean hasMoved = false;
    private float rotation = 0;
    private float oldX = -1, oldY = -1, newX = -1, newY = -1;

    private ManualColorSelectorUpdateListener colorChangeListener = new ManualColorSelectorUpdateListener() {
        @Override
        public void onNewColorSelect(int newColor) {}
    };
    private ManualColorSelectorUpdateListener colorSelectListener = new ManualColorSelectorUpdateListener() {
        @Override
        public void onNewColorSelect(int newColor) {}
    };

    public ManualColorSelectorView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth/2, parentHeight);
        this.setLayoutParams(new FrameLayout.LayoutParams(parentWidth,parentWidth));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint defaultBrush = new Paint();
        defaultBrush.setColor(Color.GRAY);
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.color_selector_wheel);

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
        matrix.postTranslate(-imageBitmap.getWidth() / 2, -imageBitmap.getHeight() / 2);
        matrix.postScale(colorSelectorWidth / ((float) imageBitmap.getWidth()), colorSelectorHeight / ((float) imageBitmap.getHeight()));
        matrix.postRotate(rotation);
        matrix.postTranslate(colorSelectorCenterX, colorSelectorCenterY);

        Paint separatorBrush = new Paint();
        separatorBrush.setColor(Color.WHITE);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorSelectButtonRadius() + 10, separatorBrush);
        Paint selectedColor = new Paint();
        if (rotation < 0)
            rotation += 360;
        selectedColor.setColor(ManualColorSelectorGraphic.getColorAtAngle(rotation));
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorSelectButtonRadius(), selectedColor);
        canvas.drawBitmap(imageBitmap, matrix, defaultBrush);

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

    public float getColorWheelWidth() {
        return (float) COLOR_WHEEL_RADIUS_PERCENT * this.getWidth();
    }

    public float getColorSelectButtonRadius() {
        return (float) COLOR_SELECT_BUTTON_RADIUS_PERCENT * this.getWidth() / 2;
    }

    public void setColorChangeListener(ManualColorSelectorUpdateListener colorChangeListener) {
        this.colorChangeListener = colorChangeListener;
    }

    public void setColorSelectListener(ManualColorSelectorUpdateListener colorSelectListener) {
        this.colorSelectListener = colorSelectListener;
    }

    public int getColor() {
        return ManualColorSelectorGraphic.getColorAtAngle(rotation);
    }

}

package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.view.MotionEvent;
import android.view.View;

import com.android.wardrobeManager.ui.color_edit.ManualColorSelectorGraphic;
import com.android.wardrobeManager.ui.color_edit.ManualColorSelectorView;
import com.android.wardrobeManager.ui.util.Utility;

public class ViewManualColor extends View {

    // Relative to width and height of canvas:
    private static final double COLOR_WHEEL_RING_PERCENT = 0.3;
    private static final double COLOR_WHEEL_RADIUS_PERCENT = 0.9;
    private static final float SEPARATOR_PERCENT = 0.02f;

    private static final int SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD = 20;

    // 0 for none, 1 for color wheel, 2 for gradient wheel
    private int movingWheel = 0;
    private float colorWheelRotation = 0;
    private float gradientWheelRotation = 0;
    private float oldX = -1, oldY = -1, newX = -1, newY = -1;

    private static final int[] COLOR_WHEEL_COLORS = new int[] {
            0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFFFF00FF, 0xFFFF0000
    };
    private static int DEFAULT_SELECTED_COLOR = COLOR_WHEEL_COLORS[0];
    private int selectedColor = DEFAULT_SELECTED_COLOR;
    private int rawSelectedColor = DEFAULT_SELECTED_COLOR;
    private int [] gradientColors = new int[] {
        rawSelectedColor, Color.BLACK, Color.WHITE, rawSelectedColor
    };
    private static final int SEPARATOR_COLOR = Color.WHITE;

    private Bitmap canvasBuffer = null;
    private Canvas canvas = null;
    private Paint defaultBrush = new Paint();
    private Paint singleColorBrush = new Paint();
    private Paint colorWheelBrush = null;
    private Paint gradientWheelBrush = null;

    public ViewManualColor(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas finalCanvas) {
        super.onDraw(finalCanvas);
        if (canvasBuffer == null) {
            canvasBuffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(canvasBuffer);
        }

        defaultBrush.setColor(Color.GRAY);

        float colorSelectorCenterX = getWidth() / 2;
        float colorSelectorCenterY = getHeight() / 2;

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

        // Drawing the color wheel
        if (colorWheelBrush == null) {
            colorWheelBrush = new Paint();
            colorWheelBrush.setAntiAlias(true);
        }
        SweepGradient colorWheelSweep = new SweepGradient(colorSelectorCenterX, colorSelectorCenterY, COLOR_WHEEL_COLORS, null);
        colorWheelBrush.setShader(colorWheelSweep);
        canvas.save();
        canvas.rotate((270 + colorWheelRotation) % 360, colorSelectorCenterX, colorSelectorCenterY);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorWheelRadius(), colorWheelBrush);
        canvas.restore();

        // Getting a new raw selected color if the color wheel moved
        if (movingWheel == 1)
            rawSelectedColor = canvasBuffer.getPixel((int) colorSelectorCenterX, (int) (colorSelectorCenterY - getColorWheelRadius() + 5));

        // Drawing the gradient wheel
        if (gradientWheelBrush == null) {
            gradientWheelBrush = new Paint();
            gradientWheelBrush.setAntiAlias(true);
        }
        gradientColors[0] = rawSelectedColor;
        gradientColors[gradientColors.length - 1] = rawSelectedColor;
        SweepGradient gradientWheelSweep = new SweepGradient(colorSelectorCenterX, colorSelectorCenterY, gradientColors, null);
        gradientWheelBrush.setShader(gradientWheelSweep);
        canvas.save();
        canvas.rotate((270 + gradientWheelRotation) % 360, colorSelectorCenterX, colorSelectorCenterY);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getGradientWheelRadius(), gradientWheelBrush);
        canvas.restore();

        // If either of the wheels have changed, set the selected color accordingly.
        if (movingWheel != 0) {
            selectedColor = canvasBuffer.getPixel((int) colorSelectorCenterX, (int) (colorSelectorCenterY - getGradientWheelRadius() + 5));
            DEFAULT_SELECTED_COLOR = selectedColor;
        }

        // Drawing the key outline
        singleColorBrush.setColor(SEPARATOR_COLOR);
        float halfKeyWidth = getKeyWidth() / 2;
        float halfSepWidth = getSeparatorSize();
        canvas.drawRoundRect(colorSelectorCenterX - halfKeyWidth - halfSepWidth,
                colorSelectorCenterY - getColorWheelRadius() - halfSepWidth,
                colorSelectorCenterX + halfKeyWidth + halfSepWidth, colorSelectorCenterY,
                40, 40, singleColorBrush);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorSelectButtonRadius(), singleColorBrush);

        // Drawing the colored key
        singleColorBrush.setColor(selectedColor);
        canvas.drawRoundRect(colorSelectorCenterX - halfKeyWidth,
                colorSelectorCenterY - getColorWheelRadius(),
                colorSelectorCenterX + halfKeyWidth, colorSelectorCenterY,
                40, 40, singleColorBrush);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, getColorSelectButtonRadius() - getSeparatorSize(), singleColorBrush);

        finalCanvas.drawBitmap(canvasBuffer, 0, 0, defaultBrush);
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
            newX = event.getX();
            newY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldX = event.getX();
            oldY = event.getY();
            newX = event.getX();
            newY = event.getY();
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            float gradientRadius = getGradientWheelRadius();
            if (Utility.distanceSquared(oldX, oldY, centerX, centerY) < gradientRadius * gradientRadius)
                movingWheel = 2;
            else
                movingWheel = 1;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            double colorSelectButtonRadiusSquared = Math.pow(getColorSelectButtonRadius(), 2);
            if (Utility.distanceSquared(oldX, oldY, newX, newY) < SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD * SELECT_BUTTON_TOUCH_DISTANCE_THRESHOLD
                    && Utility.distanceSquared(getWidth() / 2, getHeight() / 2, newX, newY) < colorSelectButtonRadiusSquared) {
            }
            newX = oldX;
            newY = oldY;
            movingWheel = 0;
        }

        invalidate();
        return true;
    }

    protected float getColorWheelRadius() {
        return (float) (COLOR_WHEEL_RADIUS_PERCENT * this.getWidth()) / 2;
    }

    protected float getGradientWheelRadius() {
        float colorWheelRadius = getColorWheelRadius();
        return (float) (colorWheelRadius - colorWheelRadius * COLOR_WHEEL_RING_PERCENT);
    }

    protected float getColorSelectButtonRadius() {
        float colorWheelRadius = getColorWheelRadius();
        return (float) (colorWheelRadius - 2 * colorWheelRadius * COLOR_WHEEL_RING_PERCENT);
    }

    protected float getSeparatorSize() {
        return SEPARATOR_PERCENT * this.getWidth();
    }

    protected float getKeyWidth() {
        return getColorWheelRadius() - getGradientWheelRadius();
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public float getColorWheelRotation() {
        return colorWheelRotation;
    }

    public void setColorWheelRotation(float colorWheelRotation) {
        this.colorWheelRotation = colorWheelRotation;
    }

    public float getGradientWheelRotation() {
        return gradientWheelRotation;
    }

    public void setGradientWheelRotation(float gradientWheelRotation) {
        this.gradientWheelRotation = gradientWheelRotation;
    }
}

package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class ViewColorBar extends View {

    private static Bitmap X_BITMAP = null;
    private int xBitmapVerticalPercent = 75;
    private int xBitmapSideLength = 0;
    private long lastTapTime = 0;
    private static final long MIN_TIME_FOR_SECOND_TAP = 100;

    private ColorRemovalCallback colorRemovalCallback = new ColorRemovalCallback() {
        @Override
        public void onColorRemoved(int[] newColors, int removedColor) {

        }
    };

    private int[] colors;
    private int selectedColorIndex = -1;

    private Bitmap roundedColorBarBuffer = null;
    private int radius = -1;
    private int colorWidth = -1;
    private Paint paint = new Paint();

    public ViewColorBar(Context context) {
        super(context);
        init();
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        X_BITMAP = Utility.drawableToBitmap(R.drawable.dialog_ic_close_normal_holo_light);
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        roundedColorBarBuffer = null;
        invalidate();
    }

    public void setColorRemovalCallback(ColorRemovalCallback callback) {
        this.colorRemovalCallback = callback;
    }

    public void unselectAllColors() {
        selectedColorIndex = -1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (radius < 0)
            radius = ClothingItemImageManager.getRoundedCornerRadius();

        if (roundedColorBarBuffer == null) {
            roundedColorBarBuffer = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas roundedCanvas = new Canvas(roundedColorBarBuffer);

            colorWidth = roundedCanvas.getWidth() / colors.length;
            for (int i = 0; i < colors.length; i++) {
                paint.setColor(colors[i]);
                roundedCanvas.drawRect(i * colorWidth, 0, (i + 1) * colorWidth, roundedCanvas.getHeight(), paint);
            }
        }
        paint.setAlpha(0xFF);
        canvas.drawBitmap(Utility.roundBitmap(roundedColorBarBuffer, radius, radius), 0, 0, paint);

        if (selectedColorIndex >= 0 && selectedColorIndex < colors.length) {
            // There is a selected color and the x needs to be drawn:
            int newXBitmapSideLength = (int) (canvas.getHeight() * (xBitmapVerticalPercent / 100.0));
            if (newXBitmapSideLength != xBitmapSideLength) {
                xBitmapSideLength = newXBitmapSideLength;
                X_BITMAP = Bitmap.createScaledBitmap(X_BITMAP, xBitmapSideLength, xBitmapSideLength, false);
            }

            int xX = (colorWidth / 2 - xBitmapSideLength / 2) + colorWidth * selectedColorIndex;
            int xY = (canvas.getHeight() / 2 - xBitmapSideLength / 2);
            Log.d("X_IMAGE", xX + ", " + xY + ", " + selectedColorIndex + ", " + colorWidth);
            canvas.drawBitmap(X_BITMAP, xX, xY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long currentTime = System.currentTimeMillis();
        if (System.currentTimeMillis() - lastTapTime <= MIN_TIME_FOR_SECOND_TAP) {
            return false;
        }
        lastTapTime = currentTime;

        int newSelected = (int) (event.getX() / colorWidth);
        if (selectedColorIndex == newSelected) {
            // Color must be removed
            int[] newColors = new int[colors.length - 1];
            int iter = 0;
            int selectedColor = colors[selectedColorIndex];
            for (int color : colors)
                if (color != selectedColor)
                    newColors[iter++] = color;
            colorRemovalCallback.onColorRemoved(newColors, selectedColor);
            selectedColorIndex = -1;
        } else {
            selectedColorIndex = newSelected;
        }

        invalidate();
        return true;
    }

    public interface ColorRemovalCallback {
        void onColorRemoved(int[] newColors, int removedColor);
    }

}

package com.android.wardrobeManager.ui.add_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.Utility;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class ViewColorBar extends View {

    private int[] colors;

    public ViewColorBar(Context context) {
        super(context);
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap roundedBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas roundedCanvas = new Canvas(roundedBitmap);

        Paint paint = new Paint();
        int width = roundedCanvas.getWidth() / colors.length;
        for (int i = 0; i < colors.length; i++) {
            paint.setColor(colors[i]);
            roundedCanvas.drawRect(i * width, 0, (i + 1) * width, roundedCanvas.getHeight(), paint);
            Log.d("COLOR", "The color is " + colors[i]);
        }

        int radius = ClothingItemImageManager.getRoundedCornerRadius();
        paint.setAlpha(0);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        paint.setAlpha(0xFF);
        canvas.drawBitmap(Utility.roundBitmap(roundedBitmap, radius, radius), 0, 0, paint);
    }
}

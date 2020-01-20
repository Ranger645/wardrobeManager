package com.android.wardrobeManager.ui.color_edit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

import com.android.wardrobeManager.ui.util.Utility;

public class ManualColorGrayscaleView extends View {

    private static final float GRAYSCALE_SELECTION_OUTSIDE_RADIUS_PERCENTAGE = 60f;
    private static final float GRAYSCALE_SELECTION_INSIDE_RADIUS_PERCENTAGE = 55f;

    private double percentage = 50;
    private int color = 0xFF000000;

    private ManualColorGrayscaleListener colorGrayscaleListener = new ManualColorGrayscaleListener() {
        @Override
        public void onNewColorSelect(double newColor) {

        }
    };

    public ManualColorGrayscaleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        Paint paint1 = new Paint();
        LinearGradient shader1 = new LinearGradient(0,0, w / 2, 0, 0xFF000000, color, Shader.TileMode.CLAMP);
        paint1.setShader(shader1);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0,2, w / 2, h - 4, paint1);

        Paint paint2 = new Paint();
        LinearGradient shader2 = new LinearGradient(w / 2,0, w, 0, color, 0xFFFFFFFF, Shader.TileMode.CLAMP);
        paint2.setShader(shader2);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(w / 2,2, w, h - 4, paint2);

        float outsideRadius = (GRAYSCALE_SELECTION_OUTSIDE_RADIUS_PERCENTAGE / 100f) * (h / 2.0f);
        float insideRadius = (GRAYSCALE_SELECTION_INSIDE_RADIUS_PERCENTAGE / 100f) * (h / 2.0f);
        float selectionX = (float) (w * percentage / 100.0);
        Paint selectorBackground = new Paint();
        selectorBackground.setColor(Color.BLACK);
        canvas.drawCircle(selectionX, h / 2, outsideRadius, selectorBackground);
        Paint selectorColor = new Paint();
        selectorColor.setColor(Utility.convertColorPercentageToColor(color, percentage));
        canvas.drawCircle(selectionX, h / 2, insideRadius, selectorColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        percentage = 100 * event.getX() / ((double) this.getWidth());
        if (percentage < 0)
            percentage = 0;
        if (percentage > 100)
            percentage = 100;
        invalidate();

        this.colorGrayscaleListener.onNewColorSelect(percentage);
        return true;
    }

    public interface ManualColorGrayscaleListener {
        void onNewColorSelect(double colorPercentage);
    }

    public void setColorGrayscaleListener(ManualColorGrayscaleListener colorGrayscaleListener) {
        this.colorGrayscaleListener = colorGrayscaleListener;
    }

    public double getColorPercentage() {
        return percentage;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

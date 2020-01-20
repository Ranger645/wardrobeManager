package com.android.wardrobeManager.ui.color_edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

public class ManualColorGrayscaleView extends View {

    private double percentage = 50;

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
        LinearGradient shader1 = new LinearGradient(0,0, w / 2, 0, 0xFFFFFFFF, 0xFFFF0000, Shader.TileMode.CLAMP);
        paint1.setShader(shader1);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0,2, w / 2, h - 4, paint1);

        Paint paint2 = new Paint();
        LinearGradient shader2 = new LinearGradient(w / 2,0, w, 0, 0xFFFF0000, 0xFF000000, Shader.TileMode.CLAMP);
        paint2.setShader(shader2);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(w / 2,2, w, h - 4, paint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        percentage = event.getX() / ((double) this.getWidth());
        invalidate();

        this.colorGrayscaleListener.onNewColorSelect(percentage);
        return super.onTouchEvent(event);
    }

    public interface ManualColorGrayscaleListener {
        void onNewColorSelect(double colorPercentage);
    }

    public void setColorGrayscaleListener(ManualColorGrayscaleListener colorGrayscaleListener) {
        this.colorGrayscaleListener = colorGrayscaleListener;
    }
}

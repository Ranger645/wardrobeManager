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

public class ManualColorSelectorView extends View {

    private float rotation = 0;

    private float oldX = -1, oldY = -1, newX = -1, newY = -1;

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
        // canvas.drawRect(new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), defaultBrush);
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.color_selector_wheel);

        float colorSelectorWidth = canvas.getWidth() - 100;
        float colorSelectorHeight = canvas.getHeight() - 100;
        float colorSelectorCenterX = canvas.getWidth() / 2;
        float colorSelectorCenterY = canvas.getHeight() / 2;

        float dRot = 0f;
        if (newX != oldX || newY != oldY) {
            dRot = 180f - (float) Math.toDegrees(Math.atan((colorSelectorCenterX - oldX) / (colorSelectorCenterY - oldY))) - (float) Math.toDegrees(Math.atan((newX - colorSelectorCenterX) / (newY - colorSelectorCenterY)));

            float direction = 0f;
            float velX = newX - oldX;
            float speedX = Math.abs(velX);
            float velY = newY - oldY;
            float speedY = Math.abs(velY);
            if (speedX > speedY)
                direction = velX / speedX;
            else
                direction = (newX > colorSelectorCenterX ? 1 : -1) * velY / speedY;

            rotation = (0.02f * direction * dRot + rotation) % 360;
            oldX = newX;
            oldY = newY;
        }

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate(-imageBitmap.getWidth() / 2, -imageBitmap.getHeight() / 2);
        matrix.postScale(colorSelectorWidth / ((float) imageBitmap.getWidth()), colorSelectorHeight / ((float) imageBitmap.getHeight()));
        matrix.postRotate(rotation);
        matrix.postTranslate(colorSelectorCenterX, colorSelectorCenterY);

        Paint borderBrush = new Paint();
        borderBrush.setColor(Color.BLACK);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, colorSelectorWidth / 2 + 50, borderBrush);
        Paint separatorBrush = new Paint();
        separatorBrush.setColor(Color.WHITE);
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, colorSelectorWidth / 2 - 10, separatorBrush);
        Paint selectedColor = new Paint();
        if (rotation < 0)
            rotation += 360;
        selectedColor.setColor(ManualColorSelectorGraphic.getColorAtAngle(rotation));
        canvas.drawCircle(colorSelectorCenterX, colorSelectorCenterY, colorSelectorWidth / 2 - 110, selectedColor);
        canvas.drawBitmap(imageBitmap, matrix, defaultBrush);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            newX = event.getX();
            newY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldX = event.getX();
            oldY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            newX = oldX;
            newY = oldY;
        }

        invalidate();
        return true;
    }

}

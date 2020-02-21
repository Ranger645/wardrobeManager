package com.android.wardrobeManager.ui.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

public class Utility {

    public static long[] hexListStrToLongArray(String hexNumbers, String delim) {
        String[] hexStrs = hexNumbers.split(delim);
        long[] longArr = new long[hexStrs.length];
        for (int i = 0; i < hexStrs.length; i++)
            longArr[i] = Long.parseLong(hexStrs[i], 16);
        return longArr;
    }

    public static int[] hexListStrToIntArray(String hexNumbers, String delim) {
        String[] hexStrs = hexNumbers.split(delim);
        int[] intArr = new int[hexStrs.length];
        for (int i = 0; i < hexStrs.length; i++)
            intArr[i] = (int) Long.parseLong(hexStrs[i], 16);
        return intArr;
    }

    public static int[] parseClothingItemColors(String colors) {
        return hexListStrToIntArray(colors, ",");
    }

    public static double distanceSquared(double ax, double ay, double bx, double by) {
        return (ax - bx) * (ax - bx) + (ay - by) * (ay - by);
    }

    public static int convertColorPercentageToColor(int color, double percentage) {
        int r = (0x00FF0000 & color) >> 16;
        int g = (0x0000FF00 & color) >> 8;
        int b = 0x000000FF & color;
        if (percentage < 50) {
            // Dark end
            percentage /= 50.0;
            r *= percentage;
            g *= percentage;
            b *= percentage;
            return 0xFF000000 | (r << 16) | (g << 8) | b;
        } else {
            // Light end
            int dr = 255 - r;
            int dg = 255 - g;
            int db = 255 - b;
            percentage = (percentage - 50) / 50.0;
            dr *= percentage;
            dg *= percentage;
            db *= percentage;
            r += dr;
            g += dg;
            b += db;
        }
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    public static Bitmap roundBitmap(Bitmap mbitmap, int xRadius, int yRadius) {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), xRadius, yRadius, mpaint);
        return imageRounded;
    }

}

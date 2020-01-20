package com.android.wardrobeManager.ui.util;

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

}

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

}

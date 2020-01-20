package com.android.wardrobeManager.ui.color_edit;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.wardrobeManager.ui.images.ImageIo;

public class ManualColorSelectorGraphic {

    private static final String COLOR_SELECTOR_IMAGE_FILENAME = "color_selector_wheel.png";
    private static final int PIXEL_SIZE = 512;

    private static final int[][] COLOR_ORDER = new int[][] {
            new int[] { 0xFF, 0x00, 0x00 },
            new int[] { 0xFF, 0xFF, 0x00 },
            new int[] { 0x00, 0xFF, 0x00 },
            new int[] { 0x00, 0xFF, 0xFF },
            new int[] { 0x00, 0x00, 0xFF },
            new int[] { 0xFF, 0x00, 0xFF },
    };

    private static Bitmap colorSelectorImage = null;

    public static void initialize(Application application) {
        colorSelectorImage = ImageIo.loadImageFromFile(COLOR_SELECTOR_IMAGE_FILENAME, application);
        if (colorSelectorImage == null) {
            colorSelectorImage = Bitmap.createBitmap(PIXEL_SIZE, PIXEL_SIZE, Bitmap.Config.ARGB_8888);


            final int halfSideLengthSquared = PIXEL_SIZE * PIXEL_SIZE / 4;
            final int transparentColor = 0x00000000;
            final int center = PIXEL_SIZE / 2;

            for (int x = 0; x < colorSelectorImage.getWidth(); x++) {
                for (int y = 0; y < colorSelectorImage.getHeight(); y++) {
                    int dist = (center - x) * (center - x) + (center - y) * (center - y);
                    if (dist <= halfSideLengthSquared && dist > 40000) {
                        colorSelectorImage.setPixel(x, y, getColorForXY(x - center, y - center));

                    } else
                        colorSelectorImage.setPixel(x, y, transparentColor);
                }
            }
            ImageIo.saveBitMapToFile(colorSelectorImage, COLOR_SELECTOR_IMAGE_FILENAME, application);
        }
    }

    private static int getColorForXY(int x, int y) {
        double pixelAngle = Math.toDegrees(Math.atan(y / ((double) x)));
        if (pixelAngle < 0)
            pixelAngle += 90;
        pixelAngle = 90 - pixelAngle;
        if (x > 0 && y < 0)
            pixelAngle += 90;
        if (x < 0 && y < 0)
            pixelAngle += 180;
        if (x < 0 && y > 0)
            pixelAngle += 270;

        double angleDiv = 360.0 / COLOR_ORDER.length;
        int[] minColorBound = COLOR_ORDER[(int) (pixelAngle / angleDiv)];
        int[] maxColorBound = COLOR_ORDER[((int) (pixelAngle / angleDiv + 1)) % COLOR_ORDER.length];
        double percentageFromMinToMax = (pixelAngle % angleDiv) / angleDiv;
        int[] color = new int[minColorBound.length];
        int returnColor = 0xFF;
        for (int i = 0; i < minColorBound.length; i++) {
            color[i] = (int) (percentageFromMinToMax * (maxColorBound[i] - minColorBound[i]) + minColorBound[i]);
            returnColor <<= 8;
            returnColor |= color[i];
        }
        return returnColor;
    }

    public static int getColorAtAngle(double angle) {
        angle = (angle + 180) % 360;
        int radius = colorSelectorImage.getWidth() / 2;
        int adjustedRadius = radius - 20;
        return colorSelectorImage.getPixel((int) (adjustedRadius * Math.sin(Math.toRadians(angle))) + radius,
                (int) (adjustedRadius * Math.cos(Math.toRadians(angle))) + radius);
    }

    public static Bitmap getColorSelectorImage() {
        return colorSelectorImage;
    }

}

package com.android.wardrobeManager.ui.color_edit;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.android.wardrobeManager.ui.images.ImageIo;

public class ManualColorSelectorGraphic {

    private static final String COLOR_SELECTOR_IMAGE_FILENAME = "color_selector_wheel.png";
    private static final int PIXEL_SIZE = 512;
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
                    if ((center - x) * (center - x) + (center - y) * (center - y) <= halfSideLengthSquared) {
                        colorSelectorImage.setPixel(x, y, Color.GREEN);

                    } else
                        colorSelectorImage.setPixel(x, y, transparentColor);
                }
            }
            ImageIo.saveBitMapToFile(colorSelectorImage, COLOR_SELECTOR_IMAGE_FILENAME, application);
        }
    }

    public static Bitmap getColorSelectorImage() {
        return colorSelectorImage;
    }

}

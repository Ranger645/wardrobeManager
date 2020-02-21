package com.android.wardrobeManager.ui.images;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.WardrobeManager;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDatabase;
import com.android.wardrobeManager.ui.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import androidx.annotation.Dimension;

public class ClothingItemImageManager {

    private static int rounded_corner_radius_px = -1;

    private static final int IMAGE_SIDE_LENGTH = 512;

    final static String CLOTHING_ITEM_IMAGE_FOLDER = "clothing_item_images";

    final static SparseArray<Bitmap> customBitmapBuffer = new SparseArray<>();
    final static SparseArray<Bitmap> generatedBitmapBuffer = new SparseArray<>();

    public static Bitmap dynamicClothingItemLoadRounded(ClothingItem toLoad) {
        Bitmap mbitmap = ClothingItemImageManager.dynamicClothingItemLoad(toLoad);
        if (rounded_corner_radius_px < 0) {
            // Getting the radius from the pixel equivalent of the dp measurement
            Resources resources = WardrobeManager.getContext().getResources();
            rounded_corner_radius_px = (resources.getDimensionPixelSize(R.dimen.rounded_corner_radius)) / 2;
        }
        return Utility.roundBitmap(mbitmap, rounded_corner_radius_px, rounded_corner_radius_px);
    }

    public static Bitmap dynamicClothingItemLoad(ClothingItem toLoad) {
        return dynamicClothingItemLoad(toLoad, true);
    }

    public static Bitmap dynamicClothingItemLoad(ClothingItem toLoad, boolean useImageBuffer) {
        return dynamicClothingItemLoad(toLoad, useImageBuffer, true);
    }

    public static Bitmap dynamicClothingItemLoad(ClothingItem toLoad, boolean useCustomImageBuffer, boolean useImageBuffer) {
        if (useImageBuffer && toLoad.isCustomImage()) {
            Bitmap bufferVal = customBitmapBuffer.get(toLoad.getId(), null);
            if (useCustomImageBuffer && bufferVal != null) {
                return bufferVal;
            }
            String path = getClothingItemImagePath(Integer.toString(toLoad.getId()));
            Bitmap bitmap = ImageIo.loadImageFromFile(path);
            if (bitmap != null) {
                customBitmapBuffer.put(toLoad.getId(), bitmap);
            }

            if (bitmap == null) {
                Log.e("IMAGE_LOAD", "Failed to find custom image. Falling back to auto-generate.");
                bitmap = dynamicClothingItemLoad(toLoad, useCustomImageBuffer, false);
            }
            return bitmap;
        } else {
            int hash = getImageHash(toLoad);
            Bitmap bitmap = generatedBitmapBuffer.get(hash, null);
            if (useImageBuffer && bitmap != null) {
                return bitmap;
            }

            String imagePath = getClothingItemImagePath(Integer.toString(hash));
            bitmap = ImageIo.loadImageFromFile(imagePath);

            if (bitmap == null) {
                bitmap = generateClothingItemImage(toLoad);
                ImageIo.saveBitMapToFile(bitmap, imagePath);
            }

            generatedBitmapBuffer.put(hash, bitmap);
            return bitmap;
        }
    }

    public static int getImageHash(ClothingItem toHash) {
        StringBuilder builder = new StringBuilder();
        builder.append(toHash.getSubType());
        builder.append(toHash.getColors());
        builder.append(toHash.getDesign());
        return builder.toString().hashCode();
    }

    public static ClothingItem saveClothinItemCustomImage(ClothingItem item, Bitmap bitmap) {
        item.setCustomImage(true);
        ImageIo.saveBitMapToFile(bitmap, getClothingItemImagePath(Integer.toString(item.getId())));
        return item;
    }

    public static File getClothingItemFileToSaveTo(ClothingItem item) {
        return new File(ImageIo.getImageDir(), getClothingItemImagePath(Integer.toString(item.getId())));
    }

    private static String getClothingItemImagePath(String imageName) {
        StringBuilder pathBuilder = new StringBuilder(CLOTHING_ITEM_IMAGE_FOLDER);
        pathBuilder.append(File.pathSeparator);
        pathBuilder.append(imageName);
        return pathBuilder.toString();
    }

    private static Bitmap generateClothingItemImage(ClothingItem toLoad) {
        // Creates an image from the clothing item object and saves it to the app's storage
        Log.w("IMAGE_GEN", "Generating new image");
        Application application = WardrobeManager.getInstance();
        int refRes = application.getResources().getIdentifier(toLoad.getSubType(), "drawable", application.getPackageName());
        Drawable d = application.getResources().getDrawable(refRes, application.getTheme());
        Bitmap reference = Bitmap.createScaledBitmap(drawableToBitmap(d), IMAGE_SIDE_LENGTH, IMAGE_SIDE_LENGTH, false);

        int[] colors = Utility.parseClothingItemColors(toLoad.getColors());
        return DesignFilterManager.filterDesign(toLoad.getDesign(), reference, colors);
    }

    private static Bitmap drawableToBitmap (Drawable drawable) {
        return drawableToBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    private static Bitmap drawableToBitmap (Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static void removeBufferValue(int id) {
        customBitmapBuffer.remove(id);
    }
}

package com.android.wardrobeManager.ui.images;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDatabase;
import com.android.wardrobeManager.ui.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClothingItemImageManager {

    final static String CLOTHING_ITEM_IMAGE_FOLDER = "clothing_item_images";

    public static Bitmap dynamicClothingItemLoad(Application application, ClothingItem toLoad) {
        if (toLoad.isCustomImage()) {
            String path = getClothingItemImagePath(Integer.toString(toLoad.getId()));
            Bitmap bitmap = ImageIo.loadImageFromFile(path, application);
            if (bitmap == null) {
                Log.e("IMAGE_LOAD", "Failed to find custom image. Falling back to auto-generate.");
                toLoad.setCustomImage(false);
                bitmap = dynamicClothingItemLoad(application, toLoad);
            }
            return bitmap;
        } else {
            int hash = getImageHash(toLoad);
            String imagePath = getClothingItemImagePath(Integer.toString(hash));
            Bitmap bitmap = ImageIo.loadImageFromFile(imagePath, application);
            if (bitmap != null)
                return bitmap;

            // Generating file manually and saving it to disc
            bitmap = generateClothingItemImage(toLoad, application);
            ImageIo.saveBitMapToFile(bitmap, imagePath, application);
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

    public static ClothingItem saveClothinItemCustomImage(ClothingItem item, Bitmap bitmap, Application application) {
        item.setCustomImage(true);
        ImageIo.saveBitMapToFile(bitmap, getClothingItemImagePath(Integer.toString(item.getId())), application);
        return item;
    }

    private static String getClothingItemImagePath(String imageName) {
        StringBuilder pathBuilder = new StringBuilder(CLOTHING_ITEM_IMAGE_FOLDER);
        pathBuilder.append(File.pathSeparator);
        pathBuilder.append(imageName);
        return pathBuilder.toString();
    }

    private static Bitmap generateClothingItemImage(ClothingItem toLoad, Application application) {
        // Creates an image from the clothing item object and saves it to the app's storage
        Log.w("IMAGE_GEN", "Generating new image");
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        int refRes = application.getResources().getIdentifier(toLoad.getSubType(), "drawable", application.getPackageName());
        Drawable d = application.getResources().getDrawable(refRes, application.getTheme());
        Bitmap reference = Bitmap.createScaledBitmap(drawableToBitmap(d), bitmap.getWidth(), bitmap.getHeight(), false);

        int[] colors = Utility.parseClothingItemColors(toLoad.getColors());
        return ColorStyleFilterManager.filterColorStyle(toLoad.getDesign(), bitmap, reference, colors);
    }

    private static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}

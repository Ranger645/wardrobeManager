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

    final static String image_folder = "Images";

    public static void deleteAllClothingItemImages(Application application) {
        ContextWrapper cw = new ContextWrapper(application.getApplicationContext());
        File directory = cw.getDir(image_folder, Context.MODE_PRIVATE);

        File[] files = directory.listFiles();
        for (File file : files)
            file.delete();
    }

    public static Bitmap dynamicClothingItemLoad(Application application, ClothingItem toLoad) {
        ContextWrapper cw = new ContextWrapper(application.getApplicationContext());
        File directory = cw.getDir(image_folder, Context.MODE_PRIVATE);

        int hash = getImageHash(toLoad);
        File persistentImage = new File(directory, Integer.toString(hash));
        if (persistentImage.isFile()) {
            try {
                Log.d("IMAGE_GEN", "Retrieving image");
                return BitmapFactory.decodeStream(new FileInputStream(persistentImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Generating file manually and saving it to disc
        Bitmap bitmap = generateClothingItemImage(toLoad, application);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(persistentImage);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
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

    public static int getImageHash(ClothingItem toHash) {
        StringBuilder builder = new StringBuilder();
        builder.append(toHash.getSubType());
        builder.append(toHash.getColors());
        builder.append(toHash.getDesign());
        // Log.d("IMAGE_HASH", "values:" + builder.toString() + " hash:" + builder.toString().hashCode());
        return builder.toString().hashCode();
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

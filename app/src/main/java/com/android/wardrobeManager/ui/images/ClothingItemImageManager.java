package com.android.wardrobeManager.ui.images;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.database.ClothingItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClothingItemImageManager {

    final static String image_folder = "Images";

    public static Bitmap dynamicClothingItemLoad(Application application, ClothingItem toLoad) {
        ContextWrapper cw = new ContextWrapper(application.getApplicationContext());
        File directory = cw.getDir(image_folder, Context.MODE_PRIVATE);

        int hash = getImageHash(toLoad);
        File persistentImage = new File(directory, Integer.toString(hash));
        if (persistentImage.isFile()) {
            try {
                return BitmapFactory.decodeStream(new FileInputStream(persistentImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Generating file manually and saving it to disc
        Bitmap bitmap = generateClothingItemImage(toLoad);
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

    private static Bitmap generateClothingItemImage(ClothingItem toLoad) {
        // Creates an image from the clothing item object and saves it to the app's storage
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        int refRes = Resources.getSystem().getIdentifier("shirt", "drawable", null);
        Bitmap reference = BitmapFactory.decodeResource(Resources.getSystem(), refRes);
        String colors = toLoad.getColors();
        for(int i = 0; i < bitmap.getWidth(); i++) {
            for (int n = 0; n < bitmap.getHeight(); n++) {
                bitmap.setPixel(n, i, 0xFF000000);
            }
        }
        return bitmap;
    }

    public static int getImageHash(ClothingItem toHash) {
        StringBuilder builder = new StringBuilder(toHash.getColors());
        builder.append(toHash.getBrand());
        builder.append(toHash.getMaterial());
        builder.append(toHash.getSeason());
        builder.append(toHash.getType());
        builder.append(toHash.getCost());
        return builder.hashCode();
    }

}

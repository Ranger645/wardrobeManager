package com.android.wardrobeManager.ui.images;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIo {

    private static final String MASTER_IMAGE_DIR = "images";

    public static Bitmap loadImageFromFile(String path, Application application) {
        ContextWrapper cw = new ContextWrapper(application.getApplicationContext());
        File directory = cw.getDir(MASTER_IMAGE_DIR, Context.MODE_PRIVATE);

        File persistentImage = new File(directory, path);
        if (persistentImage.isFile()) {
            try {
                Log.d("IMAGE_GEN", "Retrieving image");
                return BitmapFactory.decodeStream(new FileInputStream(persistentImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void saveBitMapToFile(Bitmap toSave, String path, Application application) {
        ContextWrapper cw = new ContextWrapper(application.getApplicationContext());
        File directory = cw.getDir(MASTER_IMAGE_DIR, Context.MODE_PRIVATE);
        File persistentImage = new File(directory, path);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(persistentImage);
            // Use the compress method on the BitMap object to write image to the OutputStream
            toSave.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteAllImages(Application application) {
        ContextWrapper cw = new ContextWrapper(application.getApplicationContext());
        File directory = cw.getDir(MASTER_IMAGE_DIR, Context.MODE_PRIVATE);

        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

}

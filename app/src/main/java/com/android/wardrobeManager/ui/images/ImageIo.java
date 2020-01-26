package com.android.wardrobeManager.ui.images;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.android.wardrobeManager.WardrobeManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIo {

    private static final String MASTER_IMAGE_DIR = "images";

    public static Bitmap loadImageFromFile(String path) {
        File persistentImage = new File(getImageDir(), path);
        if (persistentImage.isFile()) {
            try {
                Log.d("IMAGE_GEN", "Retrieving image");
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(persistentImage));
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(persistentImage.getAbsolutePath());
                    String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    if (orientString == null)
                        return bitmap;
                    Log.d("TEST", "orientString: " + orientString);
                    int orientation = Integer.parseInt(orientString);
                    Log.d("TEST", "orientation: " + orientation);
                    int rotationAngle = 0;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                        rotationAngle = 90;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                        rotationAngle = 180;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                        rotationAngle = 270;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotationAngle);
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void saveBitMapToFile(Bitmap toSave, String path) {
        File persistentImage = new File(getImageDir(), path);

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

    public static void deleteAllImages() {
        File[] files = getImageDir().listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    public static File getImageDir() {
        ContextWrapper cw = new ContextWrapper(WardrobeManager.getInstance().getApplicationContext());
        return cw.getDir(MASTER_IMAGE_DIR, Context.MODE_PRIVATE);
    }

}

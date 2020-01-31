package com.android.wardrobeManager.ui.add_item;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

public class AddImageEditPreviewFragment extends Fragment {

    private View.OnClickListener onCropListener, onSaveListener, onCloseListener, onTrashListener, onCameraClickedListener;

    private ImageView previewView;

    public AddImageEditPreviewFragment() {
        View.OnClickListener blankListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        onCropListener = onSaveListener = onCloseListener = onTrashListener = onCameraClickedListener = blankListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_imageedit_preview, container, false);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

        previewView = view.findViewById(R.id.add_item_preview);
        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                setPreviewImage(ClothingItemImageManager.dynamicClothingItemLoad(clothingItem));
            }
        });

        ImageView saveButton = view.findViewById(R.id.add_save_button);
        synchronized (onSaveListener) {
            saveButton.setOnClickListener(onSaveListener);
        }

        ImageView closeButton = view.findViewById(R.id.add_close_button);
        synchronized (onCloseListener) {
            closeButton.setOnClickListener(onCloseListener);
        }

        ImageView trashButton = view.findViewById(R.id.add_trash_button);
        synchronized (onTrashListener) {
            trashButton.setOnClickListener(onTrashListener);
        }

        ImageView cameraButton = view.findViewById(R.id.add_camera_button);
        synchronized (onCameraClickedListener) {
            cameraButton.setOnClickListener(onCameraClickedListener);
        }

        ImageView cropButton = view.findViewById(R.id.add_scissors_button);
        synchronized (onCropListener) {
            cropButton.setOnClickListener(onCropListener);
        }

        return view;
    }

    public void setPreviewImage(Bitmap bitmap) {
        if (previewView == null)
            return;
        BitmapShader shader;
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        RectF rect = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());

        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        int radius = 40;
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        canvas.drawRoundRect(rect, radius, radius, p);
        canvas.drawRoundRect(rect, radius, radius, paint);
        previewView.setImageBitmap(bitmap);
    }

    public void setOnSaveListener(View.OnClickListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        setClickListener(R.id.add_save_button, onSaveListener);
    }

    public void setOnCloseListener(View.OnClickListener onCloseListener) {
        Log.d("CLOSE", "SEtting on close listener");
        this.onCloseListener = onCloseListener;
        setClickListener(R.id.add_close_button, onCloseListener);
    }

    public void setOnTrashClickedListener(View.OnClickListener onCloseListener) {
        this.onCameraClickedListener = onCloseListener;
        setClickListener(R.id.add_trash_button, onCloseListener);
    }

    public void setOnCropListener(View.OnClickListener onCropListener) {
        this.onCropListener = onCropListener;
        setClickListener(R.id.add_trash_button, onCropListener);
    }

    public void setOnCameraClickedListener(View.OnClickListener onCameraClickedListener) {
        this.onCameraClickedListener = onCameraClickedListener;
        setClickListener(R.id.add_camera_button, onCameraClickedListener);
    }

    private void setClickListener(int viewResId, View.OnClickListener listener) {
        if (getView() != null) {
            View view = getView().findViewById(viewResId);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }
}

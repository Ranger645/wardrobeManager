package com.android.wardrobeManager.ui.add_item;


import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

public class PreviewFragment extends Fragment {

    private View.OnClickListener onPreviewClickedListener, onSaveListener, onCloseListener, onCameraClickedListener;

    public PreviewFragment() {
        View.OnClickListener blankListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        onPreviewClickedListener = onSaveListener = onCloseListener = onCameraClickedListener = blankListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_preview, container, false);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

        final ImageView previewButton = view.findViewById(R.id.add_item_preview);
        synchronized (onPreviewClickedListener) {
            previewButton.setOnClickListener(onPreviewClickedListener);
        }
        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                previewButton.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(
                        getActivity().getApplication(), clothingItem));
            }
        });

        ImageView saveButton = view.findViewById(R.id.add_item_check_mark_button);
        synchronized (onSaveListener) {
            saveButton.setOnClickListener(onSaveListener);
        }

        ImageView closeButton = view.findViewById(R.id.add_item_close_button);
        synchronized (closeButton) {
            closeButton.setOnClickListener(onCloseListener);
        }

        ImageView cameraButton = view.findViewById(R.id.add_item_camera_button);
        synchronized (cameraButton) {
            cameraButton.setOnClickListener(onCameraClickedListener);
        }

        return view;
    }

    public void setOnPreviewClickedListener(View.OnClickListener onPreviewClickedListener) {
        this.onPreviewClickedListener = onPreviewClickedListener;
        setClickListener(R.id.add_item_preview, onPreviewClickedListener);
    }

    public void setOnSaveListener(View.OnClickListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        setClickListener(R.id.add_item_check_mark_button, onSaveListener);
    }

    public void setOnCloseListener(View.OnClickListener onCloseListener) {
        this.onCloseListener = onCloseListener;
        setClickListener(R.id.add_item_close_button, onCloseListener);
    }

    public void setOnCameraClickedListener(View.OnClickListener onCameraClickedListener) {
        this.onCameraClickedListener = onCameraClickedListener;
        setClickListener(R.id.add_item_camera_button, onCameraClickedListener);
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

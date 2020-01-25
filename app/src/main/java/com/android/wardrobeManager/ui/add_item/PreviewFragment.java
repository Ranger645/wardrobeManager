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

    private ImageButton previewButton;
    private ImageView saveButton, closeButton, cameraButton;

    public PreviewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_preview, container, false);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

        previewButton = view.findViewById(R.id.add_item_preview);
        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                previewButton.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(
                        getActivity().getApplication(), clothingItem));
            }
        });

        saveButton = view.findViewById(R.id.add_item_check_mark_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemActivity activity = (AddItemActivity) getActivity();
                addItemViewModel.persistToDatabase();
                activity.backToCloset();
            }
        });
        closeButton = view.findViewById(R.id.add_item_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemActivity activity = (AddItemActivity) getActivity();
                activity.backToCloset();
            }
        });
        cameraButton = view.findViewById(R.id.add_item_camera_button);

        return view;
    }

}

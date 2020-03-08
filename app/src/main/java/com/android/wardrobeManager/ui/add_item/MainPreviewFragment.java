package com.android.wardrobeManager.ui.add_item;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

public class MainPreviewFragment extends Fragment {

    private ImageView previewImageView;

    public MainPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_preview, container, false);
        previewImageView = view.findViewById(R.id.preview_imageview);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        final AddItemActivity activity = (AddItemActivity) getActivity();

        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem item) {
                previewImageView.setImageBitmap(
                        ClothingItemImageManager.dynamicClothingItemLoadRounded(item));
            }
        });

        ImageView saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemViewModel.persistToDatabase();
                activity.backToCloset();
            }
        });
        ImageView closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToCloset();
            }
        });

        return view;
    }

}

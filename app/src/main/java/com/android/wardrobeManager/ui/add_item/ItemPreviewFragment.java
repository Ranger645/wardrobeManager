package com.android.wardrobeManager.ui.add_item;


import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemPreviewFragment extends Fragment {

    public ItemPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_preview, container, false);

        // Setting the image for the preview to use
        ClothingItem  clothingItem = getArguments().getParcelable("clothingItem");
        ImageButton image = view.findViewById(R.id.add_item_preview);
        image.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(
                getActivity().getApplication(), clothingItem));

        return view;
    }

}

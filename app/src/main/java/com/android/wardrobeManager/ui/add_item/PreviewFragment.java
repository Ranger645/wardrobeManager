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

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends Fragment {

    public PreviewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_preview, container, false);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        final ImageButton image = view.findViewById(R.id.add_item_preview);
        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                Log.d("DEBUG","Updating preview window: " + clothingItem.toString());
                image.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(
                        getActivity().getApplication(), clothingItem));
            }
        });

        return view;
    }



}

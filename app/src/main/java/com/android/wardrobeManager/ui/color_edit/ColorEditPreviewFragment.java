package com.android.wardrobeManager.ui.color_edit;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.ColorEditViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

public class ColorEditPreviewFragment extends Fragment {

    private ColorEditPreview previewView;

    public static ColorEditPreviewFragment getInstance(ClothingItem item) {
        ColorEditPreviewFragment frag = new ColorEditPreviewFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("clothingItem", item);
        frag.setArguments(bundle);

        return frag;
    }

    public ColorEditPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout rootLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_color_edit_preview, container, false);

        ClothingItem clothingItem = getArguments().getParcelable("clothingItem");
        final ColorEditViewModel viewModel = ViewModelProviders.of(getActivity()).get(ColorEditViewModel.class);

        previewView = new ColorEditPreview(this.getActivity(),
                ClothingItemImageManager.dynamicClothingItemLoad(clothingItem),
                clothingItem.isCustomImage());
        previewView.setColorSelectListener(new ColorEditPreview.ColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                viewModel.addClothingItemColor(0xFF000000 | color);
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT);
        previewView.setLayoutParams(params);
        rootLayout.addView(previewView);

        viewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem item) {
                previewView.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(clothingItem));
                previewView.setScaleType(viewModel.getClothingItemScaleType());
            }
        });

        return rootLayout;
    }

    public ColorEditPreview getPreviewView() {
        return previewView;
    }
}

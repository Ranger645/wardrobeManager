package com.android.wardrobeManager.ui.color_edit;


import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

/**
 * A simple {@link Fragment} subclass.
 */
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
        final AddItemViewModel viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

        previewView = new ColorEditPreview(this.getActivity(),
                ClothingItemImageManager.dynamicClothingItemLoad(getActivity().getApplication(), clothingItem),
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

        return rootLayout;
    }

    public ColorEditPreview getPreviewView() {
        return previewView;
    }
}

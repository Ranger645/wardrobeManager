package com.android.wardrobeManager.ui.add_item;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.util.Utility;

public class MiscColorBarFragment extends Fragment {


    public MiscColorBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misc_color_bar, container, false);
        AddItemViewModel viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

        final ViewColorBar colorBar = view.findViewById(R.id.color_bar);
        viewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem item) {
                colorBar.setColors(Utility.hexListStrToIntArray(item.getColors(), ","));
            }
        });
        colorBar.setColorRemovalCallback(new ViewColorBar.ColorRemovalCallback() {
            @Override
            public void onColorRemoved(int[] newColors, int removedColor) {
                viewModel.removeClothingItemColor(removedColor);
            }
        });

        return view;
    }

}

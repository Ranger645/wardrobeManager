package com.android.wardrobeManager.ui.add_item;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;

public class ItemParamEditFragment extends Fragment {

    public ItemParamEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        final ClothingItem clothingItem = addItemViewModel.getClothingItem().getValue();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_param_edit, container, false);
        LinearLayout editList = view.findViewById(R.id.additem_edit_list);

        final String[] typeOptions = getResources().getStringArray(R.array.clothing_item_options_type);
        final ItemParamRadioButton btnType = new ItemParamRadioButton("Type", clothingItem.getType(), typeOptions, inflater, editList, new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setType(selectedItem);
                String[] subtypes = getSubtypeResourceForType(selectedItem, addItemViewModel);
                item.setSubType(subtypes[0]);
                addItemViewModel.setClothingItem(item);
            }
        });

        final ItemParamButton btnSubType = new ItemParamButton("Sub Type", clothingItem.getSubType(), inflater, editList);
        btnSubType.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subTypeOptions = getSubtypeResourceForType(clothingItem.getType(), addItemViewModel);
                WardrobeAlerts.showRadioButtonDialog(getContext(), "Sub Type", subTypeOptions, new WardrobeAlerts.RadioButtonAlertCallback() {
                    @Override
                    public void onClick(Context context, String selectedItem, int itemIndex) {
                        ClothingItem item = addItemViewModel.getClothingItem().getValue();
                        item.setSubType(selectedItem);
                        addItemViewModel.getClothingItem().setValue(item);
                    }
                });
            }
        });

        final String[] designOptions = getResources().getStringArray(R.array.clothing_item_options_design);
        final ItemParamRadioButton btnDesign = new ItemParamRadioButton("Design", clothingItem.getDesign(), designOptions, inflater, editList, new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setDesign(selectedItem);
                addItemViewModel.setClothingItem(item);
            }
        });

        // TODO: Implement style slider

        final String[] materialOptions = getResources().getStringArray(R.array.clothing_item_options_material);
        final ItemParamRadioButton btnMaterial = new ItemParamRadioButton("Material", clothingItem.getMaterial(), materialOptions, inflater, editList, new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setMaterial(selectedItem);
                addItemViewModel.setClothingItem(item);
            }
        });

        // TODO: Implement manual brand entering view

        // TODO: Implement numerical cost entering

        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                Log.d("DEBUG", "Clothing item changed to: " + clothingItem.toString());
                btnType.setValueText(clothingItem.getType());
                btnSubType.setValueText(clothingItem.getSubType());
                btnDesign.setValueText(clothingItem.getDesign());
                btnMaterial.setValueText(clothingItem.getMaterial());
            }
        });

        return view;
    }

    private String[] getSubtypeResourceForType(String type, AddItemViewModel viewModel) {
        if (viewModel == null)
            viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        Resources resources = getResources();
        String arrIdentifier = resources.getString(R.string.subtype_prefix) + type;
        int subTypeId = resources.getIdentifier(arrIdentifier,"array", getActivity().getPackageName());
        return resources.getStringArray(subTypeId);
    }

}

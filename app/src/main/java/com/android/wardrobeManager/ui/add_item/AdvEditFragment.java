package com.android.wardrobeManager.ui.add_item;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;

public class AdvEditFragment extends Fragment {

    public AdvEditFragment() {
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

        String[] typeOptions = getResources().getStringArray(R.array.clothing_item_options_type);
        String[] subTypeOptions = getSubtypesForType(clothingItem.getType(), addItemViewModel);
        String[] designOptions = getResources().getStringArray(R.array.clothing_item_options_design);
        String[] materialOptions = getResources().getStringArray(R.array.clothing_item_options_material);
        String[] sizeOptions = getResources().getStringArray(R.array.clothing_item_options_size);

        final AdvEditRadioButton btnEditType = new AdvEditRadioButton("Type", typeOptions, getContext());
        final AdvEditRadioButton btnEditSubType = new AdvEditRadioButton("Sub Type", subTypeOptions, getContext());
        final AdvEditRadioButton btnEditDesign = new AdvEditRadioButton("Design", designOptions, getContext());

        final AdvEditRadioButton btnEditMaterial = new AdvEditRadioButton("Material", materialOptions, getContext());
        final AdvEditTextEnterButton btnEditBrand = new AdvEditTextEnterButton("Brand", clothingItem.getBrand(), InputType.TYPE_CLASS_TEXT, getContext());
        final AdvEditTextEnterButton btnEditCost = new AdvEditTextEnterButton("Cost", Float.toString(clothingItem.getCost()), InputType.TYPE_CLASS_NUMBER, getContext());
        final AdvEditRadioButton btnEditSize = new AdvEditRadioButton("Size", sizeOptions, getContext());

        btnEditType.setOnRadioClickListener(new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setType(selectedItem);
                String[] subTypes = getSubtypesForType(selectedItem, addItemViewModel);
                btnEditSubType.setOptions(subTypes);
                item.setSubType(subTypes[0]);
                addItemViewModel.setClothingItem(item);
            }
        });
        btnEditSubType.setOnRadioClickListener(new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setSubType(selectedItem);
                addItemViewModel.getClothingItem().setValue(item);
            }
        });
        btnEditDesign.setOnRadioClickListener(new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setDesign(selectedItem);
                addItemViewModel.setClothingItem(item);
            }
        });

        btnEditMaterial.setOnRadioClickListener(new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setMaterial(selectedItem);
                addItemViewModel.setClothingItem(item);
            }
        });
        btnEditBrand.setInputAlertCallback(new WardrobeAlerts.InputAlertCallback() {
            @Override
            public void onEnterPressed(Context context, String value) {
                Log.e("DEBUG", "HELLO WORLD");
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setBrand(value);
                addItemViewModel.setClothingItem(item);
            }
        });
        btnEditCost.setInputAlertCallback(new WardrobeAlerts.InputAlertCallback() {
            @Override
            public void onEnterPressed(Context context, String value) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setCost(Float.parseFloat(value));
                addItemViewModel.setClothingItem(item);
            }
        });
        btnEditSize.setOnRadioClickListener(new WardrobeAlerts.RadioButtonAlertCallback() {
            @Override
            public void onClick(Context context, String selectedItem, int itemIndex) {
                ClothingItem item = addItemViewModel.getClothingItem().getValue();
                item.setSize(selectedItem);
                addItemViewModel.setClothingItem(item);
            }
        });

        editList.addView(btnEditType);
        editList.addView(btnEditSubType);
        editList.addView(btnEditDesign);
        // TODO: Implement style slider
        editList.addView(btnEditMaterial);
        editList.addView(btnEditBrand);
        editList.addView(btnEditCost);
        editList.addView(btnEditSize);

        addItemViewModel.getClothingItem().observe(getActivity(), new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                Log.d("ITEM_CHANGE", clothingItem.toString());
                btnEditType.setValue(clothingItem.getType());
                btnEditSubType.setValue(clothingItem.getSubType());
                btnEditDesign.setValue(clothingItem.getDesign());
                btnEditMaterial.setValue(clothingItem.getMaterial());
                btnEditBrand.setValue(clothingItem.getBrand());
                btnEditCost.setValue(Float.toString(clothingItem.getCost()));
                btnEditSize.setValue(clothingItem.getSize());
            }
        });

        return view;
    }

    private String[] getSubtypesForType(String type, AddItemViewModel viewModel) {
        if (viewModel == null)
            viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        Resources resources = getResources();
        String arrIdentifier = resources.getString(R.string.subtype_prefix) + type;
        int subTypeId = resources.getIdentifier(arrIdentifier,"array", getActivity().getPackageName());
        return resources.getStringArray(subTypeId);
    }

}

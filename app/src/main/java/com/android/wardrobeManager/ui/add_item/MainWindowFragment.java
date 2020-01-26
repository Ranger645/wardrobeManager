package com.android.wardrobeManager.ui.add_item;


import android.graphics.Camera;
import android.os.Bundle;

import androidx.camera.core.Preview;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.color_edit.ColorEditManualFragment;
import com.android.wardrobeManager.ui.color_edit.ColorEditPreviewFragment;

public class MainWindowFragment extends Fragment {

    private static final String PREVIEW_FRAGMENT_TAG = "PREVIEW";
    private static final String CAMERA_FRAGMENT_TAG = "CAMERA";
    private boolean previewDisplayed = true;

    private CameraStatusChangeListener cameraStatusChangeListener;

    public MainWindowFragment() {

    }

    private PreviewFragment createPreviewFragment() {
        PreviewFragment previewFragment = new PreviewFragment();
        final AddItemActivity activity = (AddItemActivity) getActivity();
        final AddItemViewModel viewModel = ViewModelProviders.of(activity).get(AddItemViewModel.class);
        previewFragment.setOnSaveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.persistToDatabase();
                activity.backToCloset();
            }
        });
        previewFragment.setOnCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToCloset();
            }
        });
        previewFragment.setOnCameraClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchColorEditFragments(CAMERA_FRAGMENT_TAG);
            }
        });
        return previewFragment;
    }

    private CameraFragment createCameraFragment() {
        CameraFragment cameraFragment = new CameraFragment();
        cameraFragment.setPictureTakenCallback(new CameraFragment.CameraPictureTakenCallback() {
            @Override
            public void onPictureTaken() {
                switchColorEditFragments(PREVIEW_FRAGMENT_TAG);
            }
        });
        return cameraFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_window, container, false);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_window_fragment_holder, createPreviewFragment(), PREVIEW_FRAGMENT_TAG);
        ft.commit();

        return view;
    }

    public void setCameraStatusChangeListener(CameraStatusChangeListener cameraStatusChangeListener) {
        this.cameraStatusChangeListener = cameraStatusChangeListener;
    }

    private void switchColorEditFragments(String tagToSwitchTo) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (!previewDisplayed && tagToSwitchTo.equals(PREVIEW_FRAGMENT_TAG) ) {
            ft.replace(R.id.main_window_fragment_holder, createPreviewFragment(), tagToSwitchTo);
        } else if (previewDisplayed && tagToSwitchTo.equals(CAMERA_FRAGMENT_TAG)) {
            ft.replace(R.id.main_window_fragment_holder, createCameraFragment(), tagToSwitchTo);
        }
        ft.commit();
        previewDisplayed = !previewDisplayed;
        cameraStatusChangeListener.onCameraStatusChange(!previewDisplayed);
    }

    public interface CameraStatusChangeListener {
        void onCameraStatusChange(boolean cameraOpen);
    }

}

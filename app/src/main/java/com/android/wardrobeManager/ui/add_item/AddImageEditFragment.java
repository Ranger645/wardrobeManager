package com.android.wardrobeManager.ui.add_item;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;

public class AddImageEditFragment extends Fragment {

    private static final String PREVIEW_FRAGMENT_TAG = "PREVIEW";
    private static final String CAMERA_FRAGMENT_TAG = "CAMERA";
    private boolean previewDisplayed = true;

    private CameraStatusChangeListener cameraStatusChangeListener;

    public AddImageEditFragment() {

    }

    private AddImageEditPreviewFragment createPreviewFragment() {
        AddImageEditPreviewFragment previewFragment = new AddImageEditPreviewFragment();
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
                Log.d("CLOSING", "On close detected");
                activity.backToCloset();
            }
        });
//        previewFragment.setOnCameraClickedListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchColorEditFragments(CAMERA_FRAGMENT_TAG);
//            }
//        });
        return previewFragment;
    }

    private AddImageEditCameraFragment createCameraFragment() {
        AddImageEditCameraFragment cameraFragment = new AddImageEditCameraFragment();
        cameraFragment.setPictureTakenCallback(new AddImageEditCameraFragment.CameraPictureTakenCallback() {
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
        View view = inflater.inflate(R.layout.fragment_add_imageedit, container, false);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.add_imageedit_fragment, createPreviewFragment(), PREVIEW_FRAGMENT_TAG);
        ft.commit();

        return view;
    }

    public void setCameraStatusChangeListener(CameraStatusChangeListener cameraStatusChangeListener) {
        this.cameraStatusChangeListener = cameraStatusChangeListener;
    }

    private void switchColorEditFragments(String tagToSwitchTo) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (!previewDisplayed && tagToSwitchTo.equals(PREVIEW_FRAGMENT_TAG) ) {
            ft.replace(R.id.add_imageedit_fragment, createPreviewFragment(), tagToSwitchTo);
        } else if (previewDisplayed && tagToSwitchTo.equals(CAMERA_FRAGMENT_TAG)) {
            ft.replace(R.id.add_imageedit_fragment, createCameraFragment(), tagToSwitchTo);
        }
        ft.commit();
        previewDisplayed = !previewDisplayed;
        cameraStatusChangeListener.onCameraStatusChange(!previewDisplayed);
    }

    public interface CameraStatusChangeListener {
        void onCameraStatusChange(boolean cameraOpen);
    }

}

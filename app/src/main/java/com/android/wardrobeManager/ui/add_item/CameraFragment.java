package com.android.wardrobeManager.ui.add_item;


import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.FlashMode;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.AspectRatio;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraFragment extends Fragment {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    private TextureView textureView = null;
    private ImageView takePictureButton = null;

    private CameraPictureTakenCallback pictureTakenCallback;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        textureView = view.findViewById(R.id.camera_view);
        takePictureButton = view.findViewById(R.id.camera_take_picture_button);

        if(allPermissionsGranted()){
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        return view;
    }

    private void startCamera() {
        Log.d("CAMERA", "Starting camera");
        CameraX.unbindAll();

        Size screen = new Size(textureView.getWidth() / 4, textureView.getHeight() / 4); //size of the screen

        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    //to update the surface texture we  have to destroy it first then re-add it
                    @Override
                    public void onUpdated(Preview.PreviewOutput output){
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView, 0);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                        updateTransform();
                    }
                });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getActivity().getWindowManager().getDefaultDisplay().getRotation()).setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ClothingItemImageManager.getClothingItemFileToSaveTo(
                        addItemViewModel.getClothingItem().getValue());
                imgCap.takePicture(file, Executors.newSingleThreadExecutor(), new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        ClothingItem newItem = addItemViewModel.getClothingItem().getValue();
                        ClothingItemImageManager.removeBufferValue(newItem.getId());
                        newItem.setCustomImage(true);
                        pictureTakenCallback.onPictureTaken();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {

                    }
                });
            }
        });

        //bind to lifecycle:
        CameraX.bindToLifecycle(getActivity(), preview, imgCap);
    }

    private void updateTransform() {
        Matrix mx = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();

        float cX = w / 2f;
        float cY = h / 2f;

        int rotationDgr;
        int rotation = (int) textureView.getRotation();

        switch(rotation){
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }

        mx.postRotate((float) rotationDgr, cX, cY);
        textureView.setTransform(mx);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera();
            } else {
                Toast.makeText(getActivity(), "Permissions not granted by the user.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean allPermissionsGranted(){
        for (String permission : REQUIRED_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void setPictureTakenCallback(CameraPictureTakenCallback pictureTakenCallback) {
        this.pictureTakenCallback = pictureTakenCallback;
    }

    public interface CameraPictureTakenCallback {
        void onPictureTaken();
    }

}

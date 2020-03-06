package com.android.wardrobeManager.backend;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class AddItemViewModel extends ClothingItemViewModel {

    private float manualColorViewColorRotation = 0;
    private float manualColorViewGradientRotation = 0;

    public AddItemViewModel(@NonNull Application application) { super(application); }

    public float getManualColorViewColorRotation() {
        return manualColorViewColorRotation;
    }

    public void setManualColorViewColorRotation(float manualColorViewColorRotation) {
        this.manualColorViewColorRotation = manualColorViewColorRotation;
    }

    public float getManualColorViewGradientRotation() {
        return manualColorViewGradientRotation;
    }

    public void setManualColorViewGradientRotation(float manualColorViewGradientRotation) {
        this.manualColorViewGradientRotation = manualColorViewGradientRotation;
    }
}

package com.android.wardrobeManager.ui.add_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.color_edit.ColorEditActivity;
import com.android.wardrobeManager.ui.util.Utility;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;
import com.android.wardrobeManager.ui.util.WardrobeAlerts.*;

public class AddItemActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;
    private static final int FLING_UP_MIN_GESTURE_VELOCITY = 1000;

    private AddItemViewPager addItemViewPager = null;
    private AddItemViewPagerAdapter addItemViewPagerAdapter = null;

    private static SparseArray<String> colorToStringMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        gestureDetector = new GestureDetector(this, this);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        Bundle bundle = getIntent().getExtras();

        // This parcelable will be passed when there is a particular item that needs to be edited.
        final ClothingItem clothingItem = bundle.getParcelable("clothingItem");
        if (clothingItem != null)
            addItemViewModel.setClothingItem(clothingItem);
        if (addItemViewModel.getClothingItem() == null)
            addItemViewModel.setClothingItem(new ClothingItem());

        addItemViewPager = findViewById(R.id.add_item_view_pager);
        addItemViewPagerAdapter = new AddItemViewPagerAdapter(
                getSupportFragmentManager(),
                AddItemViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        addItemViewPager.setAdapter(addItemViewPagerAdapter);
        addItemViewPagerAdapter.getMainWindow().setCameraStatusChangeListener(new MainWindowFragment.CameraStatusChangeListener() {
            @Override
            public void onCameraStatusChange(boolean cameraOpen) {
                if (cameraOpen)
                    addItemViewPager.setSwipingEnabled(false);
                else
                    addItemViewPager.setSwipingEnabled(true);
            }
        });

        if (colorToStringMap == null) {
            initColorToStringMap();
        }

        ConstraintLayout colorEditButton = findViewById(R.id.color_edit_button);
        colorEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToColorEdit();
            }
        });

        addItemViewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                TextView nameEditText = findViewById(R.id.name_edit_field);
                nameEditText.setText(getClothingItemAutomaticName(clothingItem));

                updateColorDisplay(clothingItem);
            }
        });

    }

    protected void goToColorEdit() {
        AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        Intent intent = new Intent(AddItemActivity.this, ColorEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("clothingItem", addItemViewModel.getClothingItem().getValue());
        intent.putExtras(bundle);

        Pair<View, String> p1 = Pair.create((View) addItemViewPager, "preview_transition");
        Pair<View, String> p2 = Pair.create(findViewById(R.id.color_edit_button), "color_transition");
        ActivityOptions previewTransitionOptions = ActivityOptions.makeSceneTransitionAnimation(AddItemActivity.this, p1, p2);
        startActivity(intent, previewTransitionOptions.toBundle());
    }

    @Override
    public void onBackPressed() {
        final String saveTag = "Save";
        final String quitTag = "Quit";

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);

        RadioButtonAlertCallback callback = new RadioButtonAlertCallback() {
            public void onClick(Context context, String selectedItem, int itemIndex) {
                if (selectedItem.equals(saveTag)) {
                    addItemViewModel.persistToDatabase();
                }
                backToCloset();
            }
        };
        WardrobeAlerts.showRadioButtonDialog(this, "Quit", new String[] {saveTag, quitTag}, callback);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        super.dispatchTouchEvent(e);
        return gestureDetector.onTouchEvent(e);
    }

    protected void backToCloset() {
        Intent intent = new Intent(AddItemActivity.this, ClosetActivity.class);
        startActivity(intent);
    }

    private void updateColorDisplay(ClothingItem item) {
        int[] colors = Utility.parseClothingItemColors(item.getColors());
        LinearLayout displayLayout = findViewById(R.id.color_edit_button_display);
        displayLayout.removeAllViews();
        displayLayout.setBackgroundColor(Color.BLUE);
        for (int i = 0; i < colors.length; i++) {
            View view = new View(displayLayout.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setBackgroundColor(colors[i]);
            displayLayout.addView(view);
        }
    }

    private static void initColorToStringMap() {
        colorToStringMap = new SparseArray<>();
        colorToStringMap.put(Color.BLACK, "Black");
        colorToStringMap.put(0xFFFF00FF, "Purple");
        colorToStringMap.put(Color.BLUE, "Blue");
        colorToStringMap.put(Color.CYAN, "Cyan");
        colorToStringMap.put(Color.GREEN, "Green");
        colorToStringMap.put(Color.YELLOW, "Yellow");
        colorToStringMap.put(0xFFFFFF00, "Orange");
        colorToStringMap.put(Color.RED, "Red");
        colorToStringMap.put(Color.WHITE, "White");
    }

    private static String getClothingItemAutomaticName(ClothingItem item) {
        StringBuilder builder = new StringBuilder();
        String colorStr = item.getColors();
        long[] colors = Utility.hexListStrToLongArray(colorStr, ",");
        for (int i = 0; i < colors.length; i++) {
            long roundedColor = 0;
            while (colors[i] != 0) {
                Log.d("COLOR", "Color[i]" + colors[i]);
                roundedColor <<= 8;
                if ((colors[i] & 0xFF) >= 0x80) {
                    roundedColor |= 0xFF;
                }
                colors[i] >>= 8;
            }
            Log.d("COLOR", "Rounded color: " + roundedColor);
            builder.append(colorToStringMap.get((int) roundedColor, "<NULL>"));
            if (i < colors.length - 1) {
                if (colors.length >= 3)
                    builder.append(", ");
                else
                    builder.append(" ");
                if (i == colors.length - 2)
                    builder.append("and ");
            }
        }
        builder.append(" ");
        builder.append(item.getDesign());
        builder.append(" ");
        builder.append(item.getSubType());
        return builder.toString();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int minSpeed = getResources().getInteger(R.integer.FLING_MIN_GESTURE_SPEED);
        if (velocityY < -minSpeed) {
            goToColorEdit();
        }
        return false;
    }
}

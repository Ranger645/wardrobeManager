package com.android.wardrobeManager.ui.add_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.util.Utility;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;
import com.android.wardrobeManager.ui.util.WardrobeAlerts.*;

import java.util.LinkedList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private FrameLayout mainFragmentHolder, miscActionFragmentHolder, controlFragmentHolder;
    private enum FragmentId {
        MAIN_PREVIEW, MAIN_CAMERA, MAIN_MANUAL_COLOR, MAIN_EDIT,
        MISC_CAMERA, MISC_COLOR_BAR,
        CONTROL_AUTO, CONTROL_MANUAL_1, CONTROL_MANUAL_2,
        NONE
    }
    private FragmentId mainFragmentHolderFragmentId = FragmentId.NONE,
            miscActionFragmentHolderFragmentId = FragmentId.NONE,
            controlFragmentHolderFragmentId = FragmentId.NONE;

    private AddItemViewModel addItemViewModel;

    private GestureDetector gestureDetector;
    private static final int FLING_UP_MIN_GESTURE_VELOCITY = 1000;

    private static SparseArray<String> colorToStringMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        if (colorToStringMap == null) {
            initColorToStringMap();
        }

        gestureDetector = new GestureDetector(this, this);

        addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        Bundle bundle = getIntent().getExtras();

        // This parcelable will be passed when there is a particular item that needs to be edited.
        final ClothingItem clothingItem = bundle.getParcelable("clothingItem");
        if (clothingItem != null)
            addItemViewModel.setClothingItem(clothingItem);
        if (addItemViewModel.getClothingItem() == null)
            addItemViewModel.setClothingItem(new ClothingItem());

        mainFragmentHolder = findViewById(R.id.add_main_fragment_holder);
        miscActionFragmentHolder = findViewById(R.id.add_misc_action_fragment_holder);
        controlFragmentHolder = findViewById(R.id.add_control_fragment_holder);

        showPreviewScreen();

//        addItemViewPager = findViewById(R.id.add_item_view_pager);
//        addItemViewPagerAdapter = new AddItemViewPagerAdapter(
//                getSupportFragmentManager(),
//                AddItemViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        addItemViewPager.setAdapter(addItemViewPagerAdapter);
//        addItemViewPagerAdapter.getImageEditFragment().setCameraStatusChangeListener(new AddImageEditFragment.CameraStatusChangeListener() {
//            @Override
//            public void onCameraStatusChange(boolean cameraOpen) {
//                if (cameraOpen)
//                    addItemViewPager.setSwipingEnabled(false);
//                else
//                    addItemViewPager.setSwipingEnabled(true);
//            }
//        });

//        FrameLayout colorEditButton = findViewById(R.id.add_color_edit_button);
//        colorEditButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToColorEdit();
//            }
//        });

        addItemViewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                TextView nameEditText = findViewById(R.id.add_name_edit_field);
                nameEditText.setText(clothingItem.getSubType());
            }
        });

    }

    public void showPreviewScreen() {
        FragmentManager manager = getSupportFragmentManager();
        List<FragmentTransaction> transactions = new LinkedList<>();

        if (mainFragmentHolderFragmentId != FragmentId.MAIN_PREVIEW) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(mainFragmentHolder.getId(), new MainPreviewFragment());
            transactions.add(transaction);
            mainFragmentHolderFragmentId = FragmentId.MAIN_PREVIEW;
        }

        if (miscActionFragmentHolderFragmentId != FragmentId.MISC_COLOR_BAR) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(miscActionFragmentHolder.getId(), new MiscColorBarFragment());
            transactions.add(transaction);
            miscActionFragmentHolderFragmentId = FragmentId.MISC_COLOR_BAR;
        }

        if (controlFragmentHolderFragmentId != FragmentId.CONTROL_AUTO &&
                controlFragmentHolderFragmentId != FragmentId.CONTROL_MANUAL_1 &&
                controlFragmentHolderFragmentId != FragmentId.CONTROL_MANUAL_2) {

            if (addItemViewModel.getClothingItem().getValue().isCustomImage()) {

            } else {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(controlFragmentHolder.getId(), new ControlAutoFragment());
                transactions.add(transaction);
            }
        }

        for (FragmentTransaction transaction : transactions)
            transaction.commit();
    }

    public void showCameraScreen() {}

    public void showEditScreen() {}

    public void showCustomColorScreen() {}

//    protected void goToColorEdit() {
//        AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
//        Intent intent = new Intent(AddItemActivity.this, ColorEditActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("clothingItem", addItemViewModel.getClothingItem().getValue());
//        intent.putExtras(bundle);
//
//        Pair<View, String> p1 = Pair.create((View) addItemViewPager, "preview_transition");
//        Pair<View, String> p2 = Pair.create(findViewById(R.id.add_color_edit_button), "color_transition");
//        ActivityOptions previewTransitionOptions = ActivityOptions.makeSceneTransitionAnimation(AddItemActivity.this, p1, p2);
//        startActivity(intent, previewTransitionOptions.toBundle());
//    }

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
        if (miscActionFragmentHolderFragmentId == FragmentId.MISC_COLOR_BAR) {
            ViewColorBar colorBar = miscActionFragmentHolder.findViewById(R.id.color_bar);
            Rect hitRect = new Rect();
            colorBar.getHitRect(hitRect);
            if (!hitRect.contains((int) (e.getX() - miscActionFragmentHolder.getX()), (int) (e.getY() - miscActionFragmentHolder.getY()))) {
                colorBar.unselectAllColors();
            }
        }
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
            // goToColorEdit();
        }
        return false;
    }
}

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.Utility;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;
import com.android.wardrobeManager.ui.util.WardrobeAlerts.*;

import java.util.LinkedList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

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

        ImageView smallPreview = findViewById(R.id.add_small_preview);
        smallPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviewScreen();
            }
        });

        showPreviewScreen();

        addItemViewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem clothingItem) {
                TextView nameEditText = findViewById(R.id.add_name_edit_field);
                nameEditText.setText(clothingItem.getSubType());

                ImageView smallPreview = findViewById(R.id.add_small_preview);
                smallPreview.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoadRounded(clothingItem));
            }
        });

    }

    private FragmentTransaction getMainPreviewFragmentTransaction(FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(mainFragmentHolder.getId(), new MainPreviewFragment());
        mainFragmentHolderFragmentId = FragmentId.MAIN_PREVIEW;
        return transaction;
    }

    private FragmentTransaction getMiscColorBarFragmentTransaction(FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(miscActionFragmentHolder.getId(), new MiscColorBarFragment());
        miscActionFragmentHolderFragmentId = FragmentId.MISC_COLOR_BAR;
        return transaction;
    }

    private FragmentTransaction getControlAutoFragmentTransaction(FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(controlFragmentHolder.getId(), new ControlAutoFragment());
        controlFragmentHolderFragmentId = FragmentId.CONTROL_AUTO;
        return transaction;
    }

    private FragmentTransaction getControlManualFragmentTransaction(FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        // TODO:
        // transaction.replace(controlFragmentHolder.getId(), new ControlAutoFragment());
        // controlFragmentHolderFragmentId = FragmentId.CONTROL_MANUAL_1;
        return transaction;
    }

    private FragmentTransaction getControlFragmentTransaction(FragmentManager manager) {
        FragmentTransaction transaction = null;
        if (controlFragmentHolderFragmentId != FragmentId.CONTROL_AUTO &&
                controlFragmentHolderFragmentId != FragmentId.CONTROL_MANUAL_1 &&
                controlFragmentHolderFragmentId != FragmentId.CONTROL_MANUAL_2) {
            if (addItemViewModel.getClothingItem().getValue().isCustomImage()) {
                transaction = getControlManualFragmentTransaction(manager);
            } else {
                transaction = getControlAutoFragmentTransaction(manager);
            }
        }
        return transaction;
    }

    private FragmentTransaction getMainEditFragmentTransaction(FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(mainFragmentHolder.getId(), new MainEditFragment());
        mainFragmentHolderFragmentId = FragmentId.MAIN_EDIT;
        return transaction;
    }

    private FragmentTransaction getMainCustomColorTransaction(FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(mainFragmentHolder.getId(), new MainManualColor());
        mainFragmentHolderFragmentId = FragmentId.MAIN_MANUAL_COLOR;
        return transaction;
    }

    private static void executeFragmentTransactions(List<FragmentTransaction> transactions) {
        for (FragmentTransaction transaction : transactions)
            if (transaction != null)
                transaction.commit();
    }

    public void showPreviewScreen() {
        FragmentManager manager = getSupportFragmentManager();
        List<FragmentTransaction> transactions = new LinkedList<>();

        if (mainFragmentHolderFragmentId != FragmentId.MAIN_PREVIEW) {
            transactions.add(getMainPreviewFragmentTransaction(manager));
        }

        if (miscActionFragmentHolderFragmentId != FragmentId.MISC_COLOR_BAR) {
            transactions.add(getMiscColorBarFragmentTransaction(manager));
        }

        transactions.add(getControlFragmentTransaction(manager));

        executeFragmentTransactions(transactions);
    }

    public void showCameraScreen() {}

    public void showEditScreen() {
        FragmentManager manager = getSupportFragmentManager();
        List<FragmentTransaction> transactions = new LinkedList<>();

        if (mainFragmentHolderFragmentId != FragmentId.MAIN_EDIT) {
            transactions.add(getMainEditFragmentTransaction(manager));
        }

        if (miscActionFragmentHolderFragmentId != FragmentId.MISC_COLOR_BAR) {
            transactions.add(getMiscColorBarFragmentTransaction(manager));
        }

        transactions.add(getControlFragmentTransaction(manager));

        executeFragmentTransactions(transactions);
    }

    public void showCustomColorScreen() {
        FragmentManager manager = getSupportFragmentManager();
        List<FragmentTransaction> transactions = new LinkedList<>();

        if (mainFragmentHolderFragmentId != FragmentId.MAIN_MANUAL_COLOR) {
            transactions.add(getMainCustomColorTransaction(manager));
        }

        if (miscActionFragmentHolderFragmentId != FragmentId.MISC_COLOR_BAR) {
            transactions.add(getMiscColorBarFragmentTransaction(manager));
        }

        transactions.add(getControlFragmentTransaction(manager));

        executeFragmentTransactions(transactions);
    }

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
        if (e.getAction() == MotionEvent.ACTION_DOWN && miscActionFragmentHolderFragmentId == FragmentId.MISC_COLOR_BAR) {
            ViewColorBar colorBar = miscActionFragmentHolder.findViewById(R.id.color_bar);
            Rect hitRect = new Rect();
            miscActionFragmentHolder.getHitRect(hitRect);
            if (!hitRect.contains((int) (e.getX()), (int) (e.getY()))) {
                colorBar.unselectAllColors();
            }
        }
        return super.dispatchTouchEvent(e);
    }

    protected void backToCloset() {
        Intent intent = new Intent(AddItemActivity.this, ClosetActivity.class);
        startActivity(intent);
    }
}

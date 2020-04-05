package com.android.wardrobeManager.ui.closet;

import android.app.ActivityOptions;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import com.android.wardrobeManager.R;
import com.android.wardrobeManager.StartActivity;
import com.android.wardrobeManager.backend.ClothingItemDatabaseViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.add_item.AddItemActivity;
import java.util.List;

public class ClosetActivity extends AppCompatActivity implements ClosetClothingItemClickListener {

    private ClothingItemDatabaseViewModel clothingItemDatabaseViewModel;
    private ClosetShadeFragment clickableShade;
    private ClosetShadeFragment nonClickableShade;
    private ClosetMenuFragment closetMenu;
    
    private ClosetSortByFragment sortBy;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private boolean menuIsOpen = false;
    private boolean sortByIsOpen = false;

    private int sortingType = 0;

    private ClosetShadeFragment sortByTopShade;
    private ClosetShadeFragment sortByLeftShade;
    private ClosetShadeFragment sortByBottomShade;
    private ClosetShadeFragment sortByRightShade;
    private ClosetShadeFragment sortByMiddleShade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2 ));
        recyclerView.setHasFixedSize(true);

        fragmentManager = getSupportFragmentManager();
        clickableShade = new ClosetShadeFragment(true);
        nonClickableShade = new ClosetShadeFragment(false);
        sortBy = new ClosetSortByFragment();
        closetMenu = new ClosetMenuFragment();

        sortByTopShade = new ClosetShadeFragment(true);
        sortByLeftShade = new ClosetShadeFragment(true);
        sortByBottomShade = new ClosetShadeFragment(true);
        sortByRightShade = new ClosetShadeFragment(true);
        sortByMiddleShade = new ClosetShadeFragment(false);

        final ClosetAdapter adapter = new ClosetAdapter(this);
        recyclerView.setAdapter(adapter);

        clothingItemDatabaseViewModel = ViewModelProviders.of(this).get(ClothingItemDatabaseViewModel.class);
        clothingItemDatabaseViewModel.getClothingItems().observe(this, new Observer<List<ClothingItem>>() {
            @Override
            public void onChanged(List<ClothingItem> clothingItems) {
                adapter.setItems(clothingItems);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ClosetActivity.this, StartActivity.class));
    }

    public void openMenu(View view) {
        if (view.getId() == findViewById(R.id.closet_menu_button).getId()) {
            if (!closetMenu.fragmentIsOpen() && !nonClickableShade.fragmentIsOpen() && !clickableShade.fragmentIsOpen()) {
                menuIsOpen = true;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.add(R.id.shade_layout_holder, clickableShade).commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.add(R.id.shade_layout_holder, nonClickableShade).commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.fade_out);
                fragmentTransaction.add(R.id.menu_layout_holder, closetMenu);
                fragmentTransaction.commit();

                findViewById(R.id.closet_menu_button).setVisibility(View.INVISIBLE);
            }
        }
    }

    public void onSortByClicked(View view) {
        if (view.getId() == findViewById(R.id.sort_by_button).getId()) {
            if (closetMenu.fragmentIsOpen() && nonClickableShade.fragmentIsOpen() && clickableShade.fragmentIsOpen()) {
                closeMenu();
                menuIsOpen = false;
                sortByIsOpen = true;

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_extended_towards_right, R.anim.fade_out);
                fragmentTransaction.add(R.id.sort_by_holder, sortBy);
                fragmentTransaction.commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.add(R.id.sort_by_top_clickable_shade, sortByTopShade);
                fragmentTransaction.add(R.id.sort_by_left_clickable_shade, sortByLeftShade);
                fragmentTransaction.add(R.id.sort_by_bottom_clickable_shade, sortByBottomShade);
                fragmentTransaction.add(R.id.sort_by_right_clickable_shade, sortByRightShade);
                fragmentTransaction.add(R.id.sort_by_middle_unclickable_shade, sortByMiddleShade);
                fragmentTransaction.commit();

                findViewById(R.id.closet_menu_button).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClothingItemClick(ClothingItem item, ImageButton clothingImageView) {
        if (!menuIsOpen && !sortByIsOpen)  {
            Intent intent = new Intent(ClosetActivity.this, AddItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("clothingItem", item);
            intent.putExtras(bundle);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ClosetActivity.this, clothingImageView, "add_item_viewpager_transition");
            startActivity(intent, options.toBundle());
        }
    }

    public void shadeClicked(View view) {
        if (true/*finish statemeant here*/) {
            if (menuIsOpen) {
                closeMenu();
                Animation slideMenuButton = AnimationUtils.loadAnimation(this, R.anim.wait_then_slide_from_right);
                findViewById(R.id.closet_menu_button).startAnimation(slideMenuButton);
                findViewById(R.id.closet_menu_button).setVisibility(View.VISIBLE);
            } else if (sortByIsOpen) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_extended_away_left, R.anim.slide_extended_away_left);
                fragmentTransaction.remove(sortBy);
                fragmentTransaction.commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.remove(sortByTopShade).commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.remove(sortByLeftShade).commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.remove(sortByBottomShade).commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.remove(sortByRightShade).commit();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.remove(sortByMiddleShade).commit();

                Animation slideMenuButton = AnimationUtils.loadAnimation(this, R.anim.wait_then_slide_from_right);
                findViewById(R.id.closet_menu_button).startAnimation(slideMenuButton);
                findViewById(R.id.closet_menu_button).setVisibility(View.VISIBLE);
            }
        }
    }

    public void closeMenu() {
        if (menuIsOpen) {
            menuIsOpen = false;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.remove(clickableShade).commit();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.remove(nonClickableShade).commit();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_away_right);
            fragmentTransaction.remove(closetMenu);
            fragmentTransaction.commit();
        }
    }

    public void sortByToggleClicked(View view) {

        findViewById(R.id.laundryStatusButton).setBackground(getDrawable(R.drawable.binary_button_deselected));
        findViewById(R.id.alphabeticalButton).setBackground(getDrawable(R.drawable.binary_button_deselected));
        findViewById(R.id.colorButton).setBackground(getDrawable(R.drawable.binary_button_deselected));
        findViewById(R.id.sizeButton).setBackground(getDrawable(R.drawable.binary_button_deselected));

        if (findViewById(R.id.laundryStatusButton) == view) {
            findViewById(R.id.laundryStatusButton).setBackground(getDrawable(R.drawable.binary_button_selected));
            sortingType = 1;
        } else if (findViewById(R.id.alphabeticalButton) == view) {
            findViewById(R.id.alphabeticalButton).setBackground(getDrawable(R.drawable.binary_button_selected));
            sortingType = 2;
        } else if (findViewById(R.id.colorButton) == view) {
            findViewById(R.id.colorButton).setBackground(getDrawable(R.drawable.binary_button_selected));
            sortingType = 3;
        } else if (findViewById(R.id.sizeButton) == view) {
            findViewById(R.id.sizeButton).setBackground(getDrawable(R.drawable.binary_button_selected));
            sortingType = 4;
        }
    }

}

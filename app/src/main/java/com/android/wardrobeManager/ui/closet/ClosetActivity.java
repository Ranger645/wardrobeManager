package com.android.wardrobeManager.ui.closet;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.StartActivity;
import com.android.wardrobeManager.WardrobeManager;
import com.android.wardrobeManager.backend.ClothingItemDatabaseViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.add_item.AddItemActivity;
import com.android.wardrobeManager.ui.color_edit.ColorEditPreviewFragment;

import java.security.KeyStore;
import java.text.AttributedCharacterIterator;
import java.util.List;
import java.util.jar.Attributes;

public class ClosetActivity extends AppCompatActivity implements ClosetClothingItemClickListener {

    private ClothingItemDatabaseViewModel clothingItemDatabaseViewModel;
    private ClosetShadeFragment clickableShade;
    private ClosetShadeFragment nonClickableShade;
    private ClosetMenuFragment closetMenu;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean menuIsOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2 ));
        recyclerView.setHasFixedSize(true);

        fragmentManager = getSupportFragmentManager();
        clickableShade = new ClosetShadeFragment();
        nonClickableShade = new ClosetShadeFragment();
        closetMenu = new ClosetMenuFragment();

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

    @Override
    public void onClothingItemClick(ClothingItem item, ImageButton clothingImageView) {
        if (!menuIsOpen)  {
            Intent intent = new Intent(ClosetActivity.this, AddItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("clothingItem", item);
            intent.putExtras(bundle);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ClosetActivity.this, clothingImageView, "add_item_viewpager_transition");
            startActivity(intent, options.toBundle());
        }
    }

    public void shadeClicked(View view) {
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
            fragmentTransaction.remove(closetMenu);fragmentTransaction.commit();

            Animation slideMenuButton = AnimationUtils.loadAnimation(this, R.anim.wait_then_slide_from_right);
            findViewById(R.id.closet_menu_button).startAnimation(slideMenuButton);
            findViewById(R.id.closet_menu_button).setVisibility(View.VISIBLE);
        }
    }

}

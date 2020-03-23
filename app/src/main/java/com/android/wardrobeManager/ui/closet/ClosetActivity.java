package com.android.wardrobeManager.ui.closet;

import android.app.ActivityOptions;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
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
    private FragmentTransaction fragmentTransaction;
    private View closetMenuButton;
    private boolean menuIsOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2 ));
        recyclerView.setHasFixedSize(true);

        clickableShade = new ClosetShadeFragment();
        nonClickableShade = new ClosetShadeFragment();
        closetMenu = new ClosetMenuFragment();
        closetMenuButton = findViewById(R.id.closet_menu_button);

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
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.add(R.id.shade_layout_holder, clickableShade).commit();

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.add(R.id.shade_layout_holder, nonClickableShade).commit();

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.fade_out);
                fragmentTransaction.add(R.id.menu_layout_holder, closetMenu);
                fragmentTransaction.commit();

                Animation slideMenuButton = AnimationUtils.loadAnimation(this, R.anim.slide_away_extended_right);
                closetMenuButton.startAnimation(slideMenuButton);
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
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.remove(clickableShade).commit();

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.remove(nonClickableShade).commit();

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_away_right);
            fragmentTransaction.remove(closetMenu);
            fragmentTransaction.commit();

            Animation slideMenuButton = AnimationUtils.loadAnimation(this, R.anim.wait_then_slide_from_right);
            closetMenuButton.startAnimation(slideMenuButton);
            closetMenuButton.setVisibility(View.VISIBLE);
        }
    }

}

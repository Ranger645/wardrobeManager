package com.android.wardrobeManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.wardrobeManager.clothing_classes.Shirt;
import com.android.wardrobeManager.clothing_classes.Shoes;
import com.android.wardrobeManager.clothing_classes.Shorts;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private ArrayList<ClothingItem> closetClothes;
    Shirt displayShirt;
    Shorts displayShorts;
    Shoes displayShoes;

    ImageView shirtImage;
    ImageView shortsImage;
    ImageView shoesImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (getIntent().getParcelableArrayListExtra("closetClothes") != null) {
            closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        } else {
            initializingClosetClothes();
        }

        displayShirt = (Shirt) randomClosetItem(Shirt.class);
        displayShorts = (Shorts) randomClosetItem(Shorts.class);
        displayShoes = (Shoes) randomClosetItem(Shoes.class);

        shirtImage = findViewById(R.id.shirtImage);
        shortsImage = findViewById(R.id.shortsImage);
        shoesImage = findViewById(R.id.shoesImage);

        shuffleOutfit(new View(this));
    }



    public void goToCloset(View view) {

        Intent intent = new Intent(StartActivity.this, ClosetActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        startActivity(intent);
    }

    public void goToExpandCloset(View view) {
        Intent intent = new Intent(StartActivity.this, ExpandClosetActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("previousActivity", "StartActivity");
        startActivity(intent);
    }

    private void initializingClosetClothes() {

        closetClothes = new ArrayList<>();
        closetClothes.add(new Shirt(getResources().getColor(R.color.red)));
        closetClothes.add(new Shirt(getResources().getColor(R.color.green)));
        closetClothes.add(new Shoes(getResources().getColor(R.color.black)));
        closetClothes.add(new Shorts(getResources().getColor(R.color.black)));
        closetClothes.add(new Shorts(getResources().getColor(R.color.green)));
        closetClothes.add(new Shoes(getResources().getColor(R.color.blue)));
        closetClothes.add(new Shirt(getResources().getColor(R.color.pink)));
        closetClothes.add(new Shirt(getResources().getColor(R.color.blue)));
        closetClothes.add(new Shirt(getResources().getColor(R.color.blue)));
        closetClothes.add(new Shirt(getResources().getColor(R.color.brown)));

    }

    public void shuffleShirt(View view) {
        displayShirt = (Shirt) randomClosetItem(Shirt.class);
        updateColors();
    }

    public void shuffleShorts(View view) {
        displayShorts = (Shorts) randomClosetItem(Shorts.class);
        updateColors();
    }

    public void shuffleShoes(View view) {
        displayShoes = (Shoes) randomClosetItem(Shoes.class);
        updateColors();
    }

    public void shuffleOutfit(View view) {
        displayShirt = (Shirt) randomClosetItem(Shirt.class);
        displayShorts = (Shorts) randomClosetItem(Shorts.class);
        displayShoes = (Shoes) randomClosetItem(Shoes.class);
        updateColors();
    }

    public ClothingItem randomClosetItem(Class clothingType) {
        ArrayList<ClothingItem> items = new ArrayList<>();

        for (int i = 0; i < closetClothes.size(); i++) {
            if (closetClothes.get(i).getClass().equals(clothingType)) {
                items.add(closetClothes.get(i));
            }
        }

        if (items.size() > 0) {
            return items.get((int) (Math.random() * items.size()));
        }

        return null;

    }

    public void updateColors() {

        if (displayShirt != null) {
            shirtImage.setColorFilter(displayShirt.getColor(), PorterDuff.Mode.OVERLAY);
        }
        if (displayShorts != null) {
            shortsImage.setColorFilter(displayShorts.getColor(), PorterDuff.Mode.OVERLAY);
        }
        if (displayShoes != null) {
            shoesImage.setColorFilter(displayShoes.getColor(), PorterDuff.Mode.OVERLAY);
        }
    }

}

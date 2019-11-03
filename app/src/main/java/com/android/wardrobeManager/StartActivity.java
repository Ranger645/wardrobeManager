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

    ArrayList<ClothingItem> closetClothes;
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

        closetClothes = new ArrayList<>();
        initializingClosetClothes();
        displayShirt = randomClosetShirt();
        displayShorts = randomClosetShorts();
        displayShoes = randomClosetShoes();

        shirtImage = findViewById(R.id.shirtImage);
        shortsImage = findViewById(R.id.shortsImage);
        shoesImage = findViewById(R.id.shoesImage);

    }

    public void goToCloset(View view) {
        startActivity(new Intent(StartActivity.this, ClosetActivity.class));
    }

    public void goToExpandCloset(View view) {
        startActivity(new Intent(StartActivity.this, ExpandClosetActivity.class));
    }

    private void initializingClosetClothes() {
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
        displayShirt = randomClosetShirt();
        updateColors();
    }

    public Shirt randomClosetShirt() {
        ArrayList<Shirt> shirts = new ArrayList<Shirt>();

        for (int i = 0; i < closetClothes.size(); i++) {
            if (closetClothes.get(i) instanceof Shirt) {
                shirts.add((Shirt) closetClothes.get(i));
            }
        }

        if (shirts.size() > 0) {
            return shirts.get((int) (Math.random() * shirts.size()));
        }

        return null;
    }

    public void shuffleShorts(View view) {
        displayShorts = randomClosetShorts();
        updateColors();
    }

    public Shorts randomClosetShorts() {
        ArrayList<Shorts> shorts = new ArrayList<Shorts>();

        for (int i = 0; i < closetClothes.size(); i++) {
            if (closetClothes.get(i) instanceof Shorts) {
                shorts.add((Shorts) closetClothes.get(i));
            }
        }

        if (shorts.size() > 0) {
            return shorts.get((int) (Math.random() * shorts.size()));
        }

        return null;
    }

    public void shuffleShoes(View view) {
        displayShoes = randomClosetShoes();
        updateColors();
    }

    public Shoes randomClosetShoes() {
        ArrayList<Shoes> shoes = new ArrayList<Shoes>();

        for (int i = 0; i < closetClothes.size(); i++) {
            if (closetClothes.get(i) instanceof Shoes) {
                shoes.add((Shoes) closetClothes.get(i));
            }
        }

        if (shoes.size() > 0) {
            return shoes.get((int) (Math.random() * shoes.size()));
        }

        return null;
    }


    public void updateColors() {

        shirtImage.setColorFilter(displayShirt.getColor(), PorterDuff.Mode.OVERLAY);
        shortsImage.setColorFilter(displayShorts.getColor(), PorterDuff.Mode.OVERLAY);
        shoesImage.setColorFilter(displayShoes.getColor(), PorterDuff.Mode.OVERLAY);

    }

}

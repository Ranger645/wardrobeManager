package com.android.wardrobeManager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

public class NewClothingItemActivity extends AppCompatActivity {

    boolean RGBShowing = true;
    boolean HSBShowing = false;
    boolean HEXShowing = false;





    int color = Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clothing_item);

        setClothingColor(color);

        // RGB Seekers
        SeekBar redSeeker = findViewById(R.id.redSeeker);
        redSeeker.setOnSeekBarChangeListener(new seekerListener());
        SeekBar greenSeeker = findViewById(R.id.greenSeeker);
        greenSeeker.setOnSeekBarChangeListener(new seekerListener());
        SeekBar blueSeeker = findViewById(R.id.blueSeeker);
        blueSeeker.setOnSeekBarChangeListener(new seekerListener());

        // HSB Seekers
        SeekBar hueSeeker = findViewById(R.id.hueSeeker);
        hueSeeker.setOnSeekBarChangeListener(new seekerListener());
        SeekBar saturationSeeker = findViewById(R.id.saturationSeeker);
        saturationSeeker.setOnSeekBarChangeListener(new seekerListener());
        SeekBar brightnessSeeker = findViewById(R.id.brightnessSeeker);
        brightnessSeeker.setOnSeekBarChangeListener(new seekerListener());

        Spinner colorSpinnerType = findViewById(R.id.colorPickerTypeSpinner);
        colorSpinnerType.setOnItemSelectedListener(new spinnerListener());


    }

    public void colorPickerUpdate(View view) {

        if (view.getParent() == findViewById(R.id.RGBLayout)) {
            updateRGBColor();
        } else if (view.getParent() == findViewById(R.id.HSBLayout)) {
            updateHSBColor();
        }
        else {
            updateHEXColor();
        }

    }

    public void updateHEXColor() {

    }

    public void updateHSBProgressToColor() {
        int tempColor = color;

        float[] hsbColor = new float[3];
        Color.RGBToHSV(Color.red(tempColor), Color.green(tempColor), Color.blue(tempColor), hsbColor);

        ((SeekBar)findViewById(R.id.hueSeeker)).setProgress((int)hsbColor[0]);
        ((SeekBar)findViewById(R.id.saturationSeeker)).setProgress((int) (hsbColor[1] * 1000));
        ((SeekBar)findViewById(R.id.brightnessSeeker)).setProgress((int) (hsbColor[2] * 500));



    }

    public void updateRGBProgressToColor() {

        int tempColor = color;

        ((SeekBar)findViewById(R.id.redSeeker)).setProgress(Color.red(tempColor));
        ((SeekBar)findViewById(R.id.greenSeeker)).setProgress(Color.green(tempColor));
        ((SeekBar)findViewById(R.id.blueSeeker)).setProgress(Color.blue(tempColor));
    }

    public void updateRGBColor() {


        int red = ((SeekBar)findViewById(R.id.redSeeker)).getProgress();
        int green = ((SeekBar)findViewById(R.id.greenSeeker)).getProgress();
        int blue = ((SeekBar)findViewById(R.id.blueSeeker)).getProgress();



        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;



        setClothingColor(0xFF000000 | red | green | blue);


    }

    public void updateHSBColor() {


        float hue = (float)((SeekBar)findViewById(R.id.hueSeeker)).getProgress();
        float saturation = (float)((SeekBar)findViewById(R.id.saturationSeeker)).getProgress() / 1000f;
        float brightness = (float)((SeekBar)findViewById(R.id.brightnessSeeker)).getProgress() / 1000f;

        int newColor = ColorUtils.HSLToColor(new float[] {hue, saturation, brightness});

        setClothingColor(newColor);



    }

    public void setClothingColor(int newColor) {
        color = newColor;

        int[][] colorStates = {
                { android.R.attr.state_enabled},
                {-android.R.attr.state_enabled},
                {-android.R.attr.state_checked},
                { android.R.attr.state_pressed}
        };

        int[] colorArray = {color, color, color, color};

        ColorStateList colorList = new ColorStateList(colorStates, colorArray);
        findViewById(R.id.colorPreview).setBackgroundTintList(colorList);
    }

    public void goToPreviousActivity(View view) {
        finish();
    }

    public void changeColorPicker(String colorPickerType) {
        if (colorPickerType.equals("RGB")) {
            findViewById(R.id.RGBLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.HSBLayout).setVisibility(View.GONE);
            findViewById(R.id.HEXLayout).setVisibility(View.GONE);

            updateRGBProgressToColor();

            RGBShowing = true;
            HSBShowing = false;
            HEXShowing = false;


        } else if (colorPickerType.equals("HSB")) {
            findViewById(R.id.RGBLayout).setVisibility(View.GONE);
            findViewById(R.id.HSBLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.HEXLayout).setVisibility(View.GONE);

            updateHSBProgressToColor();

            RGBShowing = false;
            HSBShowing = true;
            HEXShowing = false;

        } else if (colorPickerType.equals("HEX")) {
            findViewById(R.id.RGBLayout).setVisibility(View.GONE);
            findViewById(R.id.HSBLayout).setVisibility(View.GONE);
            findViewById(R.id.HEXLayout).setVisibility(View.VISIBLE);

            RGBShowing = false;
            HSBShowing = false;
            HEXShowing = true;

        }
    }

    private class seekerListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            colorPickerUpdate(seekBar);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }

    private class spinnerListener implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            changeColorPicker(((Spinner)findViewById(R.id.colorPickerTypeSpinner)).getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {}

    }

    private class hexConverter {

        public int hexToInt(String hexCode) {

            if (isHexNumber(hexCode)) {

            }





            return 0;
        }

        public String intToHex(int colorInt) {
            return "FF0000";
        }

        public boolean isHexNumber(String hexCode) {

            if (hexCode.length() > 6) {
                return false;
            }

            hexCode.toUpperCase();

            for (int i = 0; i < hexCode.length(); i++) {

                String character = hexCode.substring(i, i + 1);
                boolean acceptableCharacter = false;

                for (int j = 0; j < 10; j++) {
                    if (character.equals(new Integer(j).toString())) {
                        acceptableCharacter = true;
                        break;
                    }
                }

                if (!acceptableCharacter) {
                    switch(character) {
                        case "A":
                            acceptableCharacter = true;
                            break;
                        case "B":
                            acceptableCharacter = true;
                            break;
                        case "C":
                            acceptableCharacter = true;
                            break;
                        case "D":
                            acceptableCharacter = true;
                            break;
                        case "E":
                            acceptableCharacter = true;
                            break;
                        case "F":
                            acceptableCharacter = true;
                            break;
                        case "a":
                            acceptableCharacter = true;
                            break;

                    }
                }
                 if (!acceptableCharacter) {
                     return false;
                 }

            }
            return true;

        }

    }
}



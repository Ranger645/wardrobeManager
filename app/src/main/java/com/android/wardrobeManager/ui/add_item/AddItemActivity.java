package com.android.wardrobeManager.ui.add_item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;
import com.android.wardrobeManager.ui.util.WardrobeAlerts.*;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    @Override
    public void onBackPressed() {

        ///////////////////////////////////////////////////////////////
        // Temporary solution until x and check buttons are finished //
        ///////////////////////////////////////////////////////////////

        final String saveTag = "Save";
        final String quitTag = "Quit";

        RadioButtonAlertCallback callback = new RadioButtonAlertCallback() {
            public void onClick(Context context, String selectedItem, int itemIndex) {

                // TODO: Make this actually save to the closet.
                if (selectedItem.equals(saveTag)) {

                } else if (selectedItem.equals(quitTag)) {

                } else {

                }

                Intent intent = new Intent(AddItemActivity.this, ClosetActivity.class);
                startActivity(intent);
            }
        };
        WardrobeAlerts.showRadioButtonDialog(this, "Quit", new String[] {saveTag, quitTag}, callback);

        ///////////////////////////////////////////////////////////////
    }
}

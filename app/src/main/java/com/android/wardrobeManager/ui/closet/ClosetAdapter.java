package com.android.wardrobeManager.ui.closet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.database.ClothingItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ClosetItemHolder> {

    private List<ClothingItem> clothingItems = new ArrayList<>();

    @NonNull
    @Override
    public ClosetItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.closet_item_icon, parent, false);
        return new ClosetItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClosetItemHolder holder, int position) {
        /*
        TODO: Make the ClothingItem object actually control the appearance of the bnImage. Right now
        it is just a constant image and this is obviously wrong.
         */
        // ClothingItem item = clothingItems.get(position);
        holder.bnImage.setImageResource(R.drawable.shirt);
    }

    @Override
    public int getItemCount() {
        return clothingItems.size();
    }

    public void setItems(List<ClothingItem> items) {
        this.clothingItems = items;
        notifyDataSetChanged();
    }

    class ClosetItemHolder extends RecyclerView.ViewHolder {

        private ImageButton bnImage;

        public ClosetItemHolder(@NonNull View itemView) {
            super(itemView);
            bnImage = itemView.findViewById(R.id.bn_clothing_item);
        }
    }

}

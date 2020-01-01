package com.android.wardrobeManager.ui.closet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.add_item.AddItemActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ClosetItemHolder> {

    private Context context = null;

    private List<ClothingItem> clothingItems = new ArrayList<>();

    public ClosetAdapter(Context context) {
        this.context = context;
    }

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
        TODO: Make the ClothingItem object actually control the appearance of the bnImage. Right now it is just a constant image and this is obviously wrong.
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
            itemView.setTransitionName("");
            bnImage = itemView.findViewById(R.id.bn_clothing_item);

            // Clicking on the button will direct to the clothing edit activity
            bnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, AddItemActivity.class));
                }
            });
        }
    }

}

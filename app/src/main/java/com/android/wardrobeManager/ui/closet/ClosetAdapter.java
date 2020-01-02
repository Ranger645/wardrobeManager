package com.android.wardrobeManager.ui.closet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.add_item.AddItemActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ClosetItemHolder> {

    private Context context = null;

    private List<ClothingItem> clothingItems = new ArrayList<>();
    private SparseArray<Bitmap> clothingItemBitmaps = new SparseArray<>();

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
        ClothingItem item = clothingItems.get(position);
        holder.setClothingItem(item);
        int hash = ClothingItemImageManager.getImageHash(item);
        Bitmap bitmap = clothingItemBitmaps.get(hash);
        if (bitmap != null)
            holder.bnImage.setImageBitmap(bitmap);
        else
            holder.bnImage.setImageResource(R.drawable.undefined);
    }

    @Override
    public int getItemCount() {
        return clothingItems.size();
    }

    public void setItems(List<ClothingItem> items, SparseArray<Bitmap> clothingItemBitmaps) {
        this.clothingItems = items;
        this.clothingItemBitmaps = clothingItemBitmaps;
        notifyDataSetChanged();
    }

    class ClosetItemHolder extends RecyclerView.ViewHolder {

        private ImageButton bnImage;
        private ClothingItem clothingItem;

        public ClosetItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTransitionName("");
            bnImage = itemView.findViewById(R.id.bn_clothing_item);

            // Clicking on the button will direct to the clothing edit activity
            bnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("clothingItem", clothingItem);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        public void setClothingItem(ClothingItem item) {
            clothingItem = item;
        }
    }

}

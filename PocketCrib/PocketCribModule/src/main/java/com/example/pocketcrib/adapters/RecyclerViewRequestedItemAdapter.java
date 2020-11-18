package com.example.pocketcrib.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcrib.R;
import com.example.pocketcrib.fragments.RequestedItemsFragment;
import com.example.pocketcrib.models.Item;

import java.util.ArrayList;

public class RecyclerViewRequestedItemAdapter extends RecyclerView.Adapter<RecyclerViewRequestedItemAdapter.RequestedViewHolder> {


    private ArrayList<Item> list;

    public RecyclerViewRequestedItemAdapter(ArrayList<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RequestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_item, parent, false);
        return new RequestedViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RequestedViewHolder holder, int position) {
        if (list != null) {
            Item item = list.get(position);
            holder.txtItemName.setText(item.getItemName());
            holder.txtItemQty.setText(item.getItemQty());
            holder.txtItemType.setText(item.getItemType());

        } else {
            holder.txtItemName.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 1;
        }
    }

    public static class RequestedViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        TextView txtItemType;
        TextView txtItemQty;

        public RequestedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txt_item_name);
            txtItemQty = itemView.findViewById(R.id.txt_item_qty);
            txtItemType = itemView.findViewById(R.id.txt_item_type);
        }
    }
}

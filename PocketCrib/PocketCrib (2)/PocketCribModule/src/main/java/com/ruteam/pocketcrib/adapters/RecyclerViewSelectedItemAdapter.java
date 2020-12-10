package com.ruteam.pocketcrib.adapters;
//RUteam
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruteam.pocketcrib.R;
import com.ruteam.pocketcrib.models.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewSelectedItemAdapter extends RecyclerView.Adapter<RecyclerViewSelectedItemAdapter.ViewHolder> {

    private ArrayList<Item> list;

    public RecyclerViewSelectedItemAdapter(ArrayList<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemName;
        TextView txtItemQty;
        TextView txtItemType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txt_item_name);
            txtItemQty = itemView.findViewById(R.id.txt_item_qty);
            txtItemType = itemView.findViewById(R.id.txt_item_type);

        }
    }
}

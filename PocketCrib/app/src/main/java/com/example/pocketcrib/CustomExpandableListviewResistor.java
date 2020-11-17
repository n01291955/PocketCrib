package com.example.pocketcrib;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pocketcrib.models.Item;
import com.example.pocketcrib.utils.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.paperdb.Book;
import io.paperdb.Paper;


public class CustomExpandableListviewResistor extends BaseExpandableListAdapter {
    private static final String TAG = "CustomExpandableListvie";
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    ArrayList<Item> arrayList;

    public CustomExpandableListviewResistor(Context context, List<String> expandableListTitle, HashMap<String, List<String>>
            expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        Paper.init(context);
        arrayList = new ArrayList<>();
//        Paper.book().destroy();

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(int listPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, childPosition);
//         CHILD HEADER
        if (childPosition == 0) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_items_resistor, null);
            ElegantNumberButton elegantNumberButton = convertView.findViewById(R.id.btn_elegant_item);

        }

        // LESSON CELL
        else {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_items_resistor, null);

        }


        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItems);
        expandedListTextView.setText(expandedListText);

        ElegantNumberButton elegantNumberButton = convertView.findViewById(R.id.btn_elegant_item);
        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        DatabaseReference resistor = FirebaseDatabase.getInstance().getReference("Resistor");
        resistor.addValueEventListener(new ValueEventListener() {
            ArrayList<Item> items = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Item item = ds.getValue(Item.class);
                    String key = ds.getKey();
                    item.setKey(key);
                    items.add(item);


                }

                final int[] itemQty = {Integer.valueOf(items.get(childPosition).getItemQty())};

                elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {


                        if (newValue < oldValue) {
                            itemQty[0] = itemQty[0] + 1;
                            Toast.makeText(context, itemQty[0] + " Items left", Toast.LENGTH_SHORT).show();
                        } else if (itemQty[0] == 0) {
                            Toast.makeText(context, "Not available", Toast.LENGTH_SHORT).show();


                            elegantNumberButton.setNumber(String.valueOf(oldValue));
                        } else if (itemQty[0] > 0) {
                            itemQty[0] = itemQty[0] - 1;
                            Toast.makeText(context, itemQty[0] + " Items left", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            String number = elegantNumberButton.getNumber();
                            String text = expandedListTextView.getText().toString();
//                    Paper.book().write(Common.NUMBER, number);

                            if (Integer.valueOf(number) == 0) {
                                Toast.makeText(context, "Please select items", Toast.LENGTH_SHORT).show();
                                checkBox.setChecked(false);
                            } else {
                                Item item = new Item(text, number, "Resistor");
                                item.setKey(items.get(childPosition).getKey());
                                arrayList.add(item);
                                Paper.book().write(Common.ITEM, arrayList);
                                Paper.book().write(Common.ITEM_REQ_FRAG, arrayList);
                            }



                        } else {

                            Paper.book().delete(Common.ITEM);
                            Paper.book().delete(Common.ITEM_REQ_FRAG);

                        }
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_resistor, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }
}


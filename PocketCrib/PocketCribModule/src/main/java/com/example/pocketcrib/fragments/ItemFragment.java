package com.example.pocketcrib.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pocketcrib.CustomExpandableListviewCapacitor;
import com.example.pocketcrib.CustomExpandableListviewResistor;
import com.example.pocketcrib.CustomExpandableListviewTransistor;
import com.example.pocketcrib.R;
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

import io.paperdb.Paper;

public class ItemFragment extends Fragment {
    private static final String TAG = "ItemFragment";

    //Resistor
    private ExpandableListView expandableListViewResistor;
    private CustomExpandableListviewResistor customExpandableListviewResistor;
    private HashMap<String, List<String>> expandableListDetailResistor;
    private List<String> expandableListTitleResistor;


    //Transistor
    private ExpandableListView expandableListViewTransistor;
    private CustomExpandableListviewTransistor customExpandableListviewTransistor;
    private HashMap<String, List<String>> expandableListDetailTransistor;
    private List<String> expandableListTitleTransistor;

    //Capacitor
    private ExpandableListView expandableListViewCapacitor;
    private CustomExpandableListviewCapacitor customExpandableListviewCapacitor;
    private HashMap<String, List<String>> expandableListDetailCapacitor;
    private List<String> expandableListTitleCapacitor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Paper.init(getActivity().getApplicationContext());


        Paper.book().delete(Common.ITEM_TRANSISTOR);
        Paper.book().delete(Common.ITEM);
        Paper.book().delete(Common.ITEM_CAPACITOR);
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        expandableListViewResistor = view.findViewById(R.id.expandableListViewResistor);
        expandableListDetailResistor = ExpandableListViewData.getResistorData();
        expandableListTitleResistor = new ArrayList<>(expandableListDetailResistor.keySet());
        customExpandableListviewResistor = new CustomExpandableListviewResistor(getActivity().getApplicationContext(), expandableListTitleResistor,
                expandableListDetailResistor);
        expandableListViewResistor.setAdapter(customExpandableListviewResistor);
        expandableListViewResistor.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                parent.smoothScrollToPosition(groupPosition);
                if (parent.isGroupExpanded(groupPosition)) {
                    ImageView imageView = v.findViewById(R.id.img_expanded);
                    imageView.setImageResource(R.drawable.ic_expand_less);

                } else {
                    ImageView imageView = v.findViewById(R.id.img_expanded);
                    imageView.setImageResource(R.drawable.ic_expand_more);

                }

                return false;
            }
        });

        expandableListViewResistor.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListViewTransistor = view.findViewById(R.id.expandableListViewTransistors);
        expandableListDetailTransistor = ExpandableListViewData.getTransistorData();
        expandableListTitleTransistor = new ArrayList<>(expandableListDetailTransistor.keySet());
        customExpandableListviewTransistor = new CustomExpandableListviewTransistor(getActivity().getApplicationContext(), expandableListTitleTransistor,
                expandableListDetailTransistor);
        expandableListViewTransistor.setAdapter(customExpandableListviewTransistor);
        expandableListViewTransistor.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(getActivity().getApplicationContext(), "skds", Toast.LENGTH_SHORT).show();
                parent.smoothScrollToPosition(groupPosition);
                if (parent.isGroupExpanded(groupPosition)) {
                    ImageView imageView = v.findViewById(R.id.img_expanded);
                    imageView.setImageResource(R.drawable.ic_expand_less);

                } else {
                    ImageView imageView = v.findViewById(R.id.img_expanded);
                    imageView.setImageResource(R.drawable.ic_expand_more);

                }

                return false;
            }
        });

        expandableListViewCapacitor = view.findViewById(R.id.expandableListViewCapacitors);
        expandableListDetailCapacitor = ExpandableListViewData.getCapacitorData();
        expandableListTitleCapacitor = new ArrayList<>(expandableListDetailCapacitor.keySet());
        customExpandableListviewCapacitor = new CustomExpandableListviewCapacitor(getActivity().getApplicationContext(), expandableListTitleCapacitor,
                expandableListDetailCapacitor);
        expandableListViewCapacitor.setAdapter(customExpandableListviewCapacitor);
        expandableListViewCapacitor.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(getActivity().getApplicationContext(), "skds", Toast.LENGTH_SHORT).show();
                parent.smoothScrollToPosition(groupPosition);
                if (parent.isGroupExpanded(groupPosition)) {
                    ImageView imageView = v.findViewById(R.id.img_expanded);
                    imageView.setImageResource(R.drawable.ic_expand_less);

                } else {
                    ImageView imageView = v.findViewById(R.id.img_expanded);
                    imageView.setImageResource(R.drawable.ic_expand_more);

                }

                return false;
            }
        });

        return view;
    }

    public static class ExpandableListViewData {
        public static HashMap<String, String> number;

        public static HashMap<String, List<String>> getResistorData() {

            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List<String> items = new ArrayList<String>();
//            DatabaseReference resistor = FirebaseDatabase.getInstance().getReference("Resistor");
//            resistor.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        String itemName = ds.getKey();
//                        number = (HashMap<String, String>) ds.getValue();
//
//                        Log.d(TAG, "onDataChange: ");
//
//
//
//                    }
//                }
//                Set<String> strings = number.keySet();
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
            Log.d(TAG, "getResistorData: " + number);

            items.add("1k Ohm");
            items.add("2k Ohm");
            items.add("3k Ohm");


            expandableListDetail.put("Resistors", items);
            return expandableListDetail;
        }


        public static HashMap<String, List<String>> getTransistorData() {
            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List<String> items = new ArrayList<String>();
            items.add("NPN Transistors");
            items.add("PNP Transistors");
            items.add("BJT Transistors");
            items.add("FET Transistors");


            expandableListDetail.put("Transistors", items);
            return expandableListDetail;
        }

        public static HashMap<String, List<String>> getCapacitorData() {
            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List<String> items = new ArrayList<String>();
            items.add("50\u00B5F");
            items.add("1\u00B5F");
            items.add("10nF");
            items.add("8nF");


            expandableListDetail.put("Capacitors", items);
            return expandableListDetail;
        }
    }
}
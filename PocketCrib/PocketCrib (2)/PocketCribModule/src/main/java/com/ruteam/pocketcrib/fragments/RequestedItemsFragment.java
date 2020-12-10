package com.ruteam.pocketcrib.fragments;
//RUteam
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruteam.pocketcrib.R;
import com.ruteam.pocketcrib.adapters.RecyclerViewRequestedItemAdapter;
import com.ruteam.pocketcrib.models.Item;
import com.ruteam.pocketcrib.utils.Common;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.paperdb.Paper;

public class RequestedItemsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewRequestedItemAdapter adapter;
    ArrayList<Item> listResistor;
    ArrayList<Item> listTransistor;
    ArrayList<Item> listCapacitor;
    ArrayList<Item> list;
    MaterialButton btnReturned;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_requested_items, container, false);
        btnReturned = view.findViewById(R.id.btn_return);
        listResistor = new ArrayList<>();
        listTransistor = new ArrayList<>();
        listCapacitor = new ArrayList<>();
        listResistor = Paper.book().read(Common.ITEM_REQ_FRAG);
        listTransistor = Paper.book().read(Common.ITEM_TRANSISTOR_REQ_FRAG);
        listCapacitor = Paper.book().read(Common.ITEM_CAPACITOR_REQ_FRAG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (listResistor != null && listTransistor != null && listCapacitor != null) {
                list = (ArrayList<Item>) Stream.of(listResistor, listTransistor, listCapacitor).flatMap(Collection::stream).collect(Collectors.toList());
            } else if (listResistor != null && listTransistor == null && listCapacitor == null) {
                list = (ArrayList<Item>) Stream.of(listResistor).flatMap(Collection::stream).collect(Collectors.toList());
            } else if (listResistor == null && listTransistor != null && listCapacitor == null) {
                list = (ArrayList<Item>) Stream.of(listTransistor).flatMap(Collection::stream).collect(Collectors.toList());
            } else if (listResistor == null && listTransistor == null && listCapacitor != null) {
                list = (ArrayList<Item>) Stream.of(listCapacitor).flatMap(Collection::stream).collect(Collectors.toList());
            } else if (listResistor != null && listTransistor != null && listCapacitor == null) {
                list = (ArrayList<Item>) Stream.of(listResistor,listTransistor).flatMap(Collection::stream).collect(Collectors.toList());
            } else if (listResistor != null && listTransistor == null && listCapacitor != null) {
                list = (ArrayList<Item>) Stream.of(listResistor,listCapacitor).flatMap(Collection::stream).collect(Collectors.toList());
            } else if (listResistor == null && listTransistor != null && listCapacitor != null) {
                list = (ArrayList<Item>) Stream.of(listTransistor,listCapacitor).flatMap(Collection::stream).collect(Collectors.toList());
            }
        }

        recyclerView = view.findViewById(R.id.req_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new RecyclerViewRequestedItemAdapter(list);
        recyclerView.setAdapter(adapter);


        btnReturned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase instance = FirebaseDatabase.getInstance();
                for (Item s : list) {
                    if (s.getItemType().equals("Resistor")) {
                        DatabaseReference resistor = instance.getReference("Resistor");
//                        DatabaseReference resistor = instance.getReference("Resistor").child(s.getKey());
                        resistor.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Item itemm = null;
                                ArrayList<Item> items = new ArrayList<>();

                                for (DataSnapshot data : snapshot.getChildren()) {
                                    itemm = data.getValue(Item.class);
                                    items.add(itemm);
                                }
                                HashMap<String, String> map = new HashMap<>();
                                map.put("itemName", s.getItemName());
                                map.put("itemQty", String.valueOf(Integer.valueOf(items.get(Integer.valueOf(s.getKey())).getItemQty()) + Integer.valueOf(s.getItemQty())));
                                map.put("itemType", s.getItemType());
                                resistor.child(s.getKey()).setValue(map);
                                Toast.makeText(getActivity().getApplicationContext(), "Returned", Toast.LENGTH_SHORT).show();
                                list.clear();
                                resistor.removeEventListener(this);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    } else if (s.getItemType().equals("Transistor")) {
                        DatabaseReference resistor = instance.getReference("Transistor");
//                        DatabaseReference resistor = instance.getReference("Resistor").child(s.getKey());
                        resistor.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Item> items = new ArrayList<>();
                                Item itemm = null;
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    itemm = data.getValue(Item.class);
                                    items.add(itemm);
                                }
                                HashMap<String, String> map = new HashMap<>();
                                map.put("itemName", s.getItemName());
//                                map.put("itemQty", String.valueOf(Integer.valueOf(s.getItemQty()) - Integer.valueOf(itemm.getItemQty())));
                                map.put("itemQty", String.valueOf(Integer.valueOf(items.get(Integer.valueOf(s.getKey())).getItemQty()) + Integer.valueOf(s.getItemQty())));
                                map.put("itemType", s.getItemType());
                                resistor.child(s.getKey()).setValue(map);

                                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                list.clear();
                                adapter.notifyDataSetChanged();
                                Paper.book().delete(Common.ITEM_TRANSISTOR_REQ_FRAG);
                                Paper.book().delete(Common.ITEM_REQ_FRAG);

                                resistor.removeEventListener(this);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });

                    }else if (s.getItemType().equals("Capacitor")) {
                        DatabaseReference resistor = instance.getReference("Capacitor");

                        resistor.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Item> items = new ArrayList<>();
                                Item itemm = null;
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    itemm = data.getValue(Item.class);
                                    items.add(itemm);
                                }
                                HashMap<String, String> map = new HashMap<>();
                                map.put("itemName", s.getItemName());
                                map.put("itemQty", String.valueOf(Integer.valueOf(items.get(Integer.valueOf(s.getKey())).getItemQty()) + Integer.valueOf(s.getItemQty())));
                                map.put("itemType", s.getItemType());
                                resistor.child(s.getKey()).setValue(map);

                                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                list.clear();
                                adapter.notifyDataSetChanged();
                                Paper.book().delete(Common.ITEM);
                                Paper.book().delete(Common.ITEM_TRANSISTOR);
                                Paper.book().delete(Common.ITEM_CAPACITOR);

                                resistor.removeEventListener(this);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });

                    }
                }
            }
        });
        return view;
    }
}
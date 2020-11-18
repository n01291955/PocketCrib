package com.example.pocketcrib.fragments;

import android.net.wifi.hotspot2.omadm.PpsMoParser;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.pocketcrib.R;
import com.example.pocketcrib.adapters.RecyclerViewSelectedItemAdapter;
import com.example.pocketcrib.models.Item;
import com.example.pocketcrib.utils.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.paperdb.Paper;

public class SelectedFragment extends Fragment {
    private static final String TAG = "SelectedFragment";
    RecyclerView recyclerView;
    RecyclerViewSelectedItemAdapter adapter;
    ArrayList<Item> listResistor;
    ArrayList<Item> listTransistor;
    ArrayList<Item> listCapacitor;
    ArrayList<Item> list;

    MaterialButton btnRequest;


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        list=new ArrayList<>();
//        list = Paper.book().read(Common.ITEM);//isko dekhna data hhai ya nhi is mee ookk
//       // recyclerView = view.findViewById(R.id.recycler_view);
//      ////  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//
//       /// recyclerView.setAdapter(new RecyclerViewSelectedItemAdapter(list));
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_selected, container, false);
        btnRequest = view.findViewById(R.id.btn_req);
//        list = new ArrayList<>();
        listResistor = new ArrayList<>();
        listTransistor = new ArrayList<>();
        listCapacitor = new ArrayList<>();
        listResistor = Paper.book().read(Common.ITEM);
        listTransistor = Paper.book().read(Common.ITEM_TRANSISTOR);
        listCapacitor = Paper.book().read(Common.ITEM_CAPACITOR);

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
//        Log.d(TAG, "onCreateView: " + list);
//        if (listTransistor != null && listCapacitor != null) {
//            listTransistor.addAll(listCapacitor);
//        }
//        if (listTransistor != null && listResistor != null) {
//            listResistor.addAll(listTransistor);
//        }
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new RecyclerViewSelectedItemAdapter(list);
        recyclerView.setAdapter(adapter);

        btnRequest.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<Item> items = new ArrayList<>();
                                Item itemm = null;
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    itemm = data.getValue(Item.class);
                                    items.add(itemm);
                                }
                                HashMap<String, String> map = new HashMap<>();
                                map.put("itemName", s.getItemName());
//                                map.put("itemQty", String.valueOf(Integer.valueOf(s.getItemQty()) - Integer.valueOf(itemm.getItemQty())));
                                map.put("itemQty", String.valueOf(Integer.valueOf(items.get(Integer.valueOf(s.getKey())).getItemQty()) - Integer.valueOf(s.getItemQty())));
                                map.put("itemType", s.getItemType());
                                resistor.child(s.getKey()).setValue(map);

                                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                list.clear();
                                adapter.notifyDataSetChanged();
//                                Paper.book().destroy();

                                resistor.removeEventListener(this);

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
                                map.put("itemQty", String.valueOf(Integer.valueOf(items.get(Integer.valueOf(s.getKey())).getItemQty()) - Integer.valueOf(s.getItemQty())));
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

                    } else if (s.getItemType().equals("Capacitor")) {
                        DatabaseReference resistor = instance.getReference("Capacitor");
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
                                map.put("itemQty", String.valueOf(Integer.valueOf(items.get(Integer.valueOf(s.getKey())).getItemQty()) - Integer.valueOf(s.getItemQty())));
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
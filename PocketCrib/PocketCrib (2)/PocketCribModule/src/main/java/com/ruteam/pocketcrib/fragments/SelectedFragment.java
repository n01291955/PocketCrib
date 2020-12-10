package com.ruteam.pocketcrib.fragments;
//RUteam
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruteam.pocketcrib.MainActivity;
import com.ruteam.pocketcrib.R;
import com.ruteam.pocketcrib.adapters.RecyclerViewSelectedItemAdapter;
import com.ruteam.pocketcrib.models.Item;
import com.ruteam.pocketcrib.utils.Common;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.paperdb.Paper;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class SelectedFragment extends Fragment {
    private static final String TAG = "SelectedFragment";
    RecyclerView recyclerView;
    RecyclerViewSelectedItemAdapter adapter;
    ArrayList<Item> listResistor;
    ArrayList<Item> listTransistor;
    ArrayList<Item> listCapacitor;
    ArrayList<Item> list;

    MaterialButton btnRequest;

    NotificationManager manager;
    Notification myNotication;

//    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "CHANNEL_ID")
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle("fg")
//            .setContentText("textContent")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



//    NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(getContext());
//(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);




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


        manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        btnRequest.setOnClickListener(new View.OnClickListener() {






            @Override
            public void onClick(View v) {


//                Intent intent = new Intent();
//
//                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, intent, 0);
//
//                Notification.Builder builder = new Notification.Builder(getContext());
//
//                builder.setAutoCancel(true);
//                builder.setTicker("this is ticker text");
//                builder.setContentTitle("WhatsApp Notification");
//                builder.setContentText("You have a new message");
//                builder.setSmallIcon(R.drawable.ic_notification);
//                builder.setContentIntent(pendingIntent);
//                builder.setOngoing(true);
//                builder.setSubText("This is subtext...");   //API level 16
//                builder.setNumber(100);
//                myNotication = builder.build();
//
//                 //= (Notification) builder;
//                manager.notify(11, myNotication);





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
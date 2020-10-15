package mykyta.nechyporenko.pocket_crib_v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Catalogue_Activity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listHeaders;
    HashMap<String, List<String>> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_);
        Intent intent = getIntent();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
       SetData();

        listAdapter = new ExpandableListAdapter(this, listHeaders, listItems);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,  int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listHeaders.get(groupPosition) + " : " + listItems.get(listHeaders.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                return false;
            }
        });



    }
    private void SetData() {


        listHeaders = new ArrayList<String>();
        listItems = new HashMap<String, List<String>>();

        // creating headers
        listHeaders.add("Resistors");
        listHeaders.add("Transistor");
        listHeaders.add("Diods");

        // Adding child data
        List<String> resistors = new ArrayList<String>();
        resistors.add("resistor 1");
        resistors.add("resistor 2");
        resistors.add("resistor 3");
        resistors.add("resistor 4");
        resistors.add("resistor 5");


        List<String> transistros = new ArrayList<String>();
        transistros.add("transistor 1");
        transistros.add("transistor 2");
        transistros.add("transistor 3");
        transistros.add("transistor 4");
        transistros.add("transistor 5");


        List<String> diods = new ArrayList<String>();
        diods.add("diod 1");
        diods.add("diod 2");
        diods.add("diod 3");
        diods.add("diod 4");
        diods.add("diod 5");

        listItems.put(listHeaders.get(0), resistors); // Header, Child data
        listItems.put(listHeaders.get(1), transistros);
        listItems.put(listHeaders.get(2), diods);

    }

}
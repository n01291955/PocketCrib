package com.example.pocketcrib;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pocketcrib.fragments.ItemFragment;
import com.example.pocketcrib.fragments.RequestedItemsFragment;
import com.example.pocketcrib.fragments.SelectedFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TabLayout tabLayout;
     boolean itemfrag;
     boolean requesteditemfrag;
    boolean selecteditemfrag;
    Fragment frag=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.cont);
        tabLayout = findViewById(R.id.tab_layout);

        ItemFragment fragment = new ItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cont, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        frag = new ItemFragment();

                        break;
                    case 1:
                        frag = new SelectedFragment();

                        break;
                    case 2:

                        frag = new RequestedItemsFragment();

                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.cont, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }



    boolean isFragmentVisible(){
            return frag.isVisible();
    }

}
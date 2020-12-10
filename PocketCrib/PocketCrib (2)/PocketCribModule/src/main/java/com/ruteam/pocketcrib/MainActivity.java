package com.ruteam.pocketcrib;
//RUteam
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.ruteam.pocketcrib.fragments.ItemFragment;
import com.ruteam.pocketcrib.fragments.RequestedItemsFragment;
import com.ruteam.pocketcrib.fragments.SelectedFragment;
import com.ruteam.pocketcrib.utils.SettingsActivity;
import com.ruteam.pocketcrib.utils.SettingsFragment;
import com.ruteam.pocketcrib.utils.Splash;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TabLayout tabLayout;
    Toolbar toolbar;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    Fragment fragment;
        switch(item.getItemId()) {
            case R.id.item1:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.cont, new SettingsFragment())
//                        .commit();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);


//                fragment = new SettingsFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.cont, fragment);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=MbU4fPYRvcY&t=1s"));
//                startActivity(browserIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
      boolean isFragmentVisible(){
        return frag.isVisible();
    }
}
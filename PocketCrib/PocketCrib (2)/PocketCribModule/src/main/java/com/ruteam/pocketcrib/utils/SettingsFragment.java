package com.ruteam.pocketcrib.utils;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;

import com.ruteam.pocketcrib.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SeekBar VolumeSeekbar = null;
    private SeekBar BrightnessSeekbar = null;
    private AudioManager audioManager = null;
    private float BackLightValue;
    //Seek bar object
    SeekBarPreference seekBar;
    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    TextView txtPerc;

    public SwitchPreference testPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        seekBar = (SeekBarPreference) findPreference("seek");
        String s = seekBar.toString();
        Log.d("seek is", s);
        //txtPerc = (TextView) findViewById(R.id.txtPercentage);
        //Get the content resolver
        cResolver =  getActivity().getContentResolver();
        //Get the current window
        window = getActivity().getWindow();

        //BrightnessControl();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        ListPreference listPref = (ListPreference) findPreference("listPref");
        String value = listPref.getValue().toString();
        Log.d("value is", value);

        switch(value){
            case "1":
                view.setBackgroundColor(Color.rgb(52, 168, 235));
                break;
            case "2":
                view.setBackgroundColor(Color.GREEN);
                break;
            case "3":
                view.setBackgroundColor(Color.WHITE);
                break;
//                    default:
//                        vIew.setBackgroundColor(Color.WHITE);
        }


        Preference button = findPreference(getString(R.string.myCoolButton));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                ListPreference listPref = (ListPreference) findPreference("listPref");
                String value = listPref.getValue().toString();
                switch(value){
                    case "1":
                        view.setBackgroundColor(Color.rgb(52, 168, 235));
                        break;
                    case "2":
                        view.setBackgroundColor(Color.GREEN);
                        break;
                    case "3":
                        view.setBackgroundColor(Color.WHITE);
                        break;
                    //default:
                    //    vIew.setBackgroundColor(Color.WHITE);
                }

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String b = "";
                if (preferences.getBoolean("portrait", false)) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    Log.d("key is ON", b);
                }else {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    Log.d("key is OFF", b);
                }

                return true;
            }
        });

        return view;
    }

    //@Override
    public void onProgressChanged(SeekBarPreference arg0, int arg1, boolean arg2) {
        // TODO Auto-generated method stub
        BackLightValue = (float)arg1/100;

        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.screenBrightness = BackLightValue;
        getActivity().getWindow().setAttributes(layoutParams);
    }
    //@Override
    public void onStartTrackingTouch(SeekBarPreference arg0) {
    }
    //@Override
    public void onStopTrackingTouch(SeekBarPreference arg0) {

        int SysBackLightValue = (int)(BackLightValue * 255);
        android.provider.Settings.System.putInt(getActivity().getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                SysBackLightValue);
    }


    public void writePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getContext().getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getContext().getPackageName()));
                startActivityForResult(intent, 200);

            }
        }
    }
}
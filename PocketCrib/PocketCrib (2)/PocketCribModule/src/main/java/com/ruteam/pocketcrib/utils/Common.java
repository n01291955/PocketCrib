package com.ruteam.pocketcrib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {
    public static final String NUMBER = "NUMBER";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM = "ITEM";
    public static final String ITEM_REQ_FRAG = "ITEM_REQ_FRAG";
    public static final String ITEM_TRANSISTOR = "ITEM_TRANSISTOR";
    public static final String ITEM_CAPACITOR = "ITEM_CAPACITOR";
    public static final String ITEM_TRANSISTOR_REQ_FRAG = "ITEM_TRANSISTOR_REQ_FRAG";
    public static final String ITEM_CAPACITOR_REQ_FRAG = "ITEM_CAPACITOR_REQ_FRAG";

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();


    }

}

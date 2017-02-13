package com.nicrob64.bitcoinaddressmonitorwidget.model;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by Admin on 31/01/2017.
 */

public class BitcoinAddressMonitorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this);
    }
}

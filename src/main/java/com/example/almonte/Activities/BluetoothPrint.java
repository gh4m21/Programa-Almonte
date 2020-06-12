package com.example.almonte.Activities;

import android.app.Application;

import com.mazenrashed.printooth.Printooth;

public class BluetoothPrint extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Printooth.INSTANCE.init(this);
    }
}

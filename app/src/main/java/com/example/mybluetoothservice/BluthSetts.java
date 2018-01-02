package com.example.mybluetoothservice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BluthSetts extends AppCompatActivity {
    String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluth_setts);
        action = getIntent().getStringExtra("action");
        sendBroadcast(new Intent().setAction(action).putExtra("BluthSetts", 111));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent().setAction(action).putExtra("BluthSetts", 222));
    }
}

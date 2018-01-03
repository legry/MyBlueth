package com.example.mybluetoothservice;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class BluthSetts extends AppCompatActivity {

    String action;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluth_setts);
        recyclerView = (RecyclerView) findViewById(R.id.recDevices);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        action = getIntent().getStringExtra("action");
        recyclerView.setAdapter(new DeviceRecyclerAdapter(getSupportFragmentManager(), getIntent().getStringArrayExtra("devices")));
        // sendBroadcast(new Intent().setAction(action).putExtra("BluthSetts", 111));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // sendBroadcast(new Intent().setAction(action).putExtra("BluthSetts", 222));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
    }
}

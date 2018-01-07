package com.example.mybluetoothservice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BluthSetts extends AppCompatActivity {

    String action;
    RecyclerView recyclerView;
    String[] myPermissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean read_ok = false, write_ok = false;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluth_setts);
        recyclerView = (RecyclerView) findViewById(R.id.recDevices);
        if ((ContextCompat.checkSelfPermission(this, myPermissions[0]) == PackageManager.PERMISSION_GRANTED) &&
                ((ContextCompat.checkSelfPermission(this, myPermissions[1]) == PackageManager.PERMISSION_GRANTED))) {
            openBluthSetts();
        } else {
            ActivityCompat.requestPermissions(this, myPermissions, 111);
        }

        // sendBroadcast(new Intent().setAction(action).putExtra("BluthSetts", 111));

    }

    private void writeFile() {
        DeviceRecyclerAdapter dra = new DeviceRecyclerAdapter(getSupportFragmentManager(), getIntent().getStringArrayExtra("devices"));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("myAdapter", dra);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openBluthSetts() {
        action = getIntent().getStringExtra("action");
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyBluthJSONFiles/" + action);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        //recyclerView.setAdapter();
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            for (int i = 0; i < grantResults.length; i++) {
                if (permissions[i].equals(myPermissions[0]) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    read_ok = true;
                } else if (permissions[i].equals(myPermissions[1]) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    write_ok = true;
                }
            }
            if (read_ok && write_ok) {
                openBluthSetts();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

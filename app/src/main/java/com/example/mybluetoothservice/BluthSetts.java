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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BluthSetts extends AppCompatActivity {

    String action;
    RecyclerView recyclerView;
    String[] myPermissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean read_ok = false, write_ok = false;
    private File file;
    private DeviceRecyclerAdapter dra;

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

    public void writeFile(List<Device> devices) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(gson.toJson(devices));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        dra = new DeviceRecyclerAdapter(getSupportFragmentManager(),file);
        recyclerView.setAdapter(dra);
    }

    private void creatFile() {
        file = new File(file, action + ".txt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    String[] devs = getIntent().getStringArrayExtra("devices");
                    List<Device> devices = new ArrayList<>();

                    for (String dev : devs) {
                        devices.add(new Device(dev));
                    }
                    writeFile(devices);
                    readFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            readFile();
        }
    }

    private void openBluthSetts() {
        action = getIntent().getStringExtra("action");
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyBluthJSONFiles");
        if (!file.exists()) {
            if (file.mkdir()) {
                creatFile();
            }
        } else {
            creatFile();
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
        writeFile(dra.getDevices());
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

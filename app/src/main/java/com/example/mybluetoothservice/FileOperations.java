package com.example.mybluetoothservice;

import android.content.Intent;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.mybluetoothservice.BluthSetts.file;

class FileOperations {


    static void writeFile(List<Device> devices) {
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

    static List<Device> readFile(File file) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<Device> tpOut = null;
        try {
            tpOut = gson.fromJson(new FileReader(file), new TypeToken<List<Device>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tpOut;
    }

    static void createFile(Intent intent) {
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyBluthJSONFiles");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(file, intent.getStringExtra("action") + ".txt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    String[] devs = intent.getStringArrayExtra("devices");
                    List<Device> devices = new ArrayList<>();
                    for (String dev : devs) {
                        devices.add(new Device(dev));
                    }
                    writeFile(devices);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

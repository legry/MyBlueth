package com.example.mybluetoothservice;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.MyHolder> {

    private FragmentManager fragmentManager;
    private List<Device> devices;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device, parent, false));
    }

    DeviceRecyclerAdapter(FragmentManager fragmentManager, File file) {
        this.fragmentManager = fragmentManager;
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try {
            this.devices = gson.fromJson(new FileReader(file), new TypeToken<List<Device>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    List<Device> getDevices() {
        return devices;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.devId.setText(devices.get(position).getDevId());
        holder.devName.setText(devices.get(position).getDevName());
        holder.devName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new DialogDevices(new DialogDevices.DeviceChoiceListener() {
                    @Override
                    public void choiceListener(String device) {
                        devices.get(position).setDevName(device);
                        holder.devName.setText(device);
                    }
                })).show(fragmentManager, "DialogDevices");
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView devId, devName;
        MyHolder(View itemView) {
            super(itemView);
            devId = itemView.findViewById(R.id.devId);
            devName = itemView.findViewById(R.id.devName);
        }
    }
}

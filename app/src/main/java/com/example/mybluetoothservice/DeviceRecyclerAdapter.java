package com.example.mybluetoothservice;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import static com.example.mybluetoothservice.FileOperations.readFile;

class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.MyHolder> {

    private FragmentManager fragmentManager;
    private List<Device> devices;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device, parent, false));
    }

    DeviceRecyclerAdapter(FragmentManager fragmentManager, File file) {
        this.fragmentManager = fragmentManager;
        devices = readFile(file);
    }

    List<Device> getDevices() {
        return devices;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.devId.setText(devices.get(position).getDevId());
        holder.devName.setText(devices.get(position).getDevName());
        holder.devName.setOnClickListener(view -> (new DialogDevices(device -> {
            devices.get(position).setDevName(device);
            holder.devName.setText(device);
        })).show(fragmentManager, "DialogDevices"));
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

package com.example.mybluetoothservice;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.MyHolder> {

    private FragmentManager fragmentManager;

    private List<Device> devices = new ArrayList<>();
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device, parent, false));
    }

    public DeviceRecyclerAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.devId.setText(devices.get(position).getDevId());
        holder.devName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new DialogDevices(new DialogDevices.DeviceChoiceListener() {
                    @Override
                    public void choiceListener(CharSequence device) {
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

    void  addDevice(Device device) {
        devices.add(device);
        this.notifyDataSetChanged();
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

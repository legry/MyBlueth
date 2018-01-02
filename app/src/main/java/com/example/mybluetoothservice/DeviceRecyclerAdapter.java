package com.example.mybluetoothservice;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.MyHolder> {
    private List<Device> devices;
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }



    class MyHolder extends RecyclerView.ViewHolder {
        TextView devId, devName, status;
        Switch connect;
        MyHolder(View itemView) {
            super(itemView);
            devId = itemView.findViewById(R.id.devId);
            devName = itemView.findViewById(R.id.devName);
            status = itemView.findViewById(R.id.status);
            connect = itemView.findViewById(R.id.connect);
        }
    }
}

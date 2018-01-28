package com.example.mybluetoothservice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Set;

public class DialogDevices extends DialogFragment {

    private interface BluetoothEnableListener {
        void Listener(Set<BluetoothDevice> bluetoothDevices);
    }

    interface DeviceChoiceListener {
        void choiceListener(String deviceName, String deviceAddress);
    }

    private DeviceChoiceListener choiceListener;

    private BluetoothEnableListener listener;

    {
        listener = new BluetoothEnableListener() {
            @Override
            public void Listener(Set<BluetoothDevice> bluetoothDevices) {
                String[] arrDevices = new String[bluetoothDevices.size()];
                String[] devNames = new String[bluetoothDevices.size()];
                String[] devAddrs = new String[bluetoothDevices.size()];
                int i = 0;
                for (BluetoothDevice btDevice : bluetoothDevices) {
                    devNames[i] = btDevice.getName();
                    devAddrs[i] = btDevice.getAddress();
                    arrDevices[i] = devNames[i] + "\n" + devAddrs[i];
                    i++;
                }
                builder.setItems(arrDevices, (dialogInterface, i1) -> {
                    choiceListener.choiceListener(devNames[i1], devAddrs[i1]);
                    DialogDevices.this.dismiss();
                });
            }
        };
    }

    public DialogDevices(DeviceChoiceListener choiceListener) {
        this.choiceListener = choiceListener;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                listener.Listener(btAdapter.getBondedDevices());
                getContext().unregisterReceiver(receiver);
            }
        }
    };

    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private AlertDialog.Builder builder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Список спаренных устройств");
        if (!btAdapter.isEnabled()) {
            btAdapter.enable();
            getContext().registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        } else {
            listener.Listener(btAdapter.getBondedDevices());
        }
        return builder.create();
    }
}

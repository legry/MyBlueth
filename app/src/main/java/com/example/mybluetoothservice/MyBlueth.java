package com.example.mybluetoothservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.ArduinoAIDL.IArduino;

import java.util.HashMap;

public class MyBlueth extends Service {

    String action;
    HashMap<String, BluetoothConnect> bluthMap = new HashMap<>();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) iArduino;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent().setAction(action).putExtra("mybluth", 1255));
    }

    IArduino iArduino = new IArduino.Stub() {

        @Override
        public String comandBridge(String comand) throws RemoteException {
            return null;
        }
    };
}

package com.example.mybluetoothservice;

class Device {
    private String devId, devName;

    Device(String devId) {
        this.devId = devId;
    }

    String getDevId() {
        return devId;
    }

    String getDevName() {
        return devName;
    }

    void setDevName(String devName) {
        this.devName = devName;
    }
}

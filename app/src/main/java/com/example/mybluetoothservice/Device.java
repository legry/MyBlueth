package com.example.mybluetoothservice;

class Device {
    private String devId, devName, devAddr;

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

    String getDevAddr() {
        return devAddr;
    }

    void setDevAddr(String devAddr) {
        this.devAddr = devAddr;
    }
}

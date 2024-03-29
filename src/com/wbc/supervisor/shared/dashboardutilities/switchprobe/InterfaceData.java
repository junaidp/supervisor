package com.wbc.supervisor.shared.dashboardutilities.switchprobe;


import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InterfaceData extends BaseGrid implements IsSerializable
{

    private String key;
    private int ifID;
    private String description;
    private  int type;
    private  long speedBps;
    private  long speedMps;
    private String mac;

    public InterfaceData() {
        ifID = 0;
        description="";
        type=0;
        speedBps =0;
        speedMps=0;
        mac="";
    }

    public int getIfID() {
        return ifID;
    }

    public void setIfID(int ifID) {
        this.ifID = ifID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSpeedBps() {
        return speedBps;
    }

    public void setSpeedBps(long speedBps) {
        this.speedBps = speedBps;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public long getSpeedMps() {
        return speedMps;
    }

    public void setSpeedMps(long speedMps) {
        this.speedMps = speedMps;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key) {
    }
}

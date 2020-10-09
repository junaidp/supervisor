package com.wbc.supervisor.shared.dashboardutilities.switchprobe;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArpData implements IsSerializable
{
    private int ifNum;
    private String ip;
    private String mac;

    public ArpData() {
    }

    public int getIfNum() {
        return ifNum;
    }

    public void setIfNum(int ifNum) {
        this.ifNum = ifNum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}

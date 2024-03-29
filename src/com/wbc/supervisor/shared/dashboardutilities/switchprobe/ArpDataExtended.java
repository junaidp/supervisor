package com.wbc.supervisor.shared.dashboardutilities.switchprobe;



import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArpDataExtended extends BaseGrid implements IsSerializable
{
    private String key;

    private String mac;
    private int descid;
    private String ip;
    private String name;
    private int ifNum;
    private String ifDescription;
    private String vendor;

    public ArpDataExtended() {

    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIfNum() {
        return ifNum;
    }

    public void setIfNum(int ifNum) {
        this.ifNum = ifNum;
    }

    public String getIfDescription() {
        return ifDescription;
    }

    public void setIfDescription(String ifDescription) {
        this.ifDescription = ifDescription;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getKey()
    {
        return key;
    }


    public void setKey(String key) {
    }
}

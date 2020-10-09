package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

public class ConnectionInfoData implements IsSerializable
{
    private String key;

    public String getKey()
    {
        return key;
    }

    private int descid;
    private String ipaddress;
    private String network;
    private int networkid;    // for when there are duplicate agents, not used for now, always 0;
    private String vendor;
    private Date joinDate;
    private String joinDays;
    private String state;
    private Date lastDate;
    private String lastDays;
    private boolean isConnected = false;
    private Date joinTime;
    private Date lastTime;
    private String deviceName;
    private int criticalState;


    public ConnectionInfoData() {
        networkid=0;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public int getNetworkid() {
        return networkid;
    }

    public void setNetworkid(int networkid) {
        this.networkid = networkid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }


    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getJoinDays() {
        return joinDays;
    }

    public void setJoinDays(String joinDays) {
        this.joinDays = joinDays;
    }

    public String getLastDays() {
        return lastDays;
    }

    public void setLastDays(String lastDays) {
        this.lastDays = lastDays;
    }

    public int getCriticalState() {
        return criticalState;
    }

    public void setCriticalState(int criticalState) {
        this.criticalState = criticalState;
    }

    public void setKey(String s) {
    }
}

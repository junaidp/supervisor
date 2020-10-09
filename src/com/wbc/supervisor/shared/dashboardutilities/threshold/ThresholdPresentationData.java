package com.wbc.supervisor.shared.dashboardutilities.threshold;


import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThresholdPresentationData extends BaseGrid implements IsSerializable
{
    private String key;

    public String getKey()
    {
        return key;
    }
    private String ipaddress;
    private String devicename;
    private int threshType;
    private StatData statData;
    private double cur_threshold;
    private double recommend_threshold;
    private int descid;
    private int threshid;
    private String network;
    private int countOverRec ;
    private ThresholdSetData setData;

    // moved to constants public final int THRESHOLD_TYPE_PING = 1;
    // moved to constants public final int THRESHOLD_TYPE_RECV = 2;
    // moved to constants public final int THRESHOLD_TYPE_XMIT = 3;

    public ThresholdPresentationData() {
    }

    public int getCountOverRec() {
        return countOverRec;
    }

    public void setCountOverRec(int countOverRec) {
        this.countOverRec = countOverRec;
    }


    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public int getThreshType() {
        return threshType;
    }

    public void setThreshType(int threshType) {
        this.threshType = threshType;
    }

    public StatData getStatData() {
        return statData;
    }

    public void setStatData(StatData statData) {
        this.statData = statData;
    }

    public double getCur_threshold() {
        return cur_threshold;
    }

    public void setCur_threshold(double cur_threshold) {
        this.cur_threshold = cur_threshold;
    }

    public double getRecommend_threshold() {
        return recommend_threshold;
    }

    public void setRecommend_threshold(double recommend_threshold) {
        this.recommend_threshold = recommend_threshold;
    }

    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public int getThreshid() {
        return threshid;
    }

    public void setThreshid(int threshid) {
        this.threshid = threshid;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setKey(String key) {
    }

    public void setSetData(ThresholdSetData setData) {
    }

    public ThresholdSetData getSetData() {
        return setData;
    }
}

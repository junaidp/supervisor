package com.wbc.supervisor.shared.dashboardutilities.threshold;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThresholdSetData implements IsSerializable
{
    private int threshid;
    private int threshType;
    private int newSetting;

    public ThresholdSetData() {
    }

    public int getThreshid() {
        return threshid;
    }

    public void setThreshid(int threshid) {
        this.threshid = threshid;
    }

    public int getNewSetting() {
        return newSetting;
    }

    public void setNewSetting(int newSetting) {
        this.newSetting = newSetting;
    }

    public int getThreshType() {
        return threshType;
    }

    public void setThreshType(int threshType) {
        this.threshType = threshType;
    }
}

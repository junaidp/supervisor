package com.wbc.supervisor.shared.dashboardutilities.switchprobe;


import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CiscoVlanData extends BaseGrid implements IsSerializable
{
    int mgt;
    int vlan;
    int type;

    private String key;

    public CiscoVlanData() {
    }

    public int getVlan() {
        return vlan;
    }

    public void setVlan(int vlan) {
        this.vlan = vlan;
    }

    public int getMgt() {
        return mgt;
    }

    public void setMgt(int mgt) {
        this.mgt = mgt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key) {
    }
}

package com.wbc.supervisor.shared.dashboardutilities.switchprobe;


import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IpData extends BaseGrid implements IsSerializable
{
    private String ip;
    private int interfaceNum;
    private String netMask;
    private String key;

    public IpData() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getInterfaceNum() {
        return interfaceNum;
    }

    public void setInterfaceNum(int interfaceNum) {
        this.interfaceNum = interfaceNum;
    }

    public String getNetMask() {
        return netMask;
    }

    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key) {
    }
}



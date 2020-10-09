package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IntravueSwitchInfoData implements IsSerializable {
    String ip="";
    String community = "";
    String name ="";
    String nwName="";
    String key = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNwName() {
        return nwName;
    }

    public void setNwName(String nwName) {
        this.nwName = nwName;
    }
}

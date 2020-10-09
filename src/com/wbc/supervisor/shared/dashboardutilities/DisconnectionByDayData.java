package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

public class DisconnectionByDayData implements IsSerializable
{
    private int descid;
    ArrayList<Integer> dayTotals;
    private String name = "not done yet";
    private String ip;
    private String network = "not done yet";

    public DisconnectionByDayData() {
        dayTotals = new ArrayList<>();
        for (int i=0; i< 8; i++) {
            dayTotals.add(i, 0);
        }
    }

    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public ArrayList<Integer> getDayTotals() {
        return dayTotals;
    }

    public void setDayTotals(ArrayList<Integer> dayTotals) {
        this.dayTotals = dayTotals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
}

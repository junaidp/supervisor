package com.wbc.supervisor.shared.dashboardutilities.switchprobe;


import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PortDataExtended extends BaseGrid implements IsSerializable
{
    private int portNumber;
    private int portIf;
    private String description;
    private  int type;
    private  long speed;

    private String key;


    public PortDataExtended() {
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
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

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public int getPortIf() {
        return portIf;
    }

    public void setPortIf(int portIf) {
        this.portIf = portIf;
    }

    public String getKey()
    {
        return key;
    }


    public void setKey(String key) {
    }
}

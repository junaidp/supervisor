package com.wbc.supervisor.shared.dashboardutilities.switchprobe;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PortData implements IsSerializable
{
    private int portNum;
    private int portIf;

    public PortData() {
    }

    public int getPortNum() {
        return portNum;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }

    public int getPortIf() {
        return portIf;
    }

    public void setPortIf(int portIf) {
        this.portIf = portIf;
    }


}

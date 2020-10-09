package com.wbc.supervisor.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Junaid on 5/5/2015.
 */
public class LocalDetailsDTO implements IsSerializable {
    private String ipAddress;
    private String dateFormat;
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }


    public LocalDetailsDTO(){

    }
}

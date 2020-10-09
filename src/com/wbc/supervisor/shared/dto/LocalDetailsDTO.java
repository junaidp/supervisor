package com.wbc.supervisor.shared.dto;

import java.io.Serializable;

/**
 * Created by Junaid on 5/5/2015.
 */
public class LocalDetailsDTO implements Serializable {
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

package com.wbc.supervisor.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by JIM on 12/31/2014.
 */
public class DeviceinfoNamesDTO implements IsSerializable {
    int deviceid;
    String name;
    String ipAddress;
    String  location;
    String  ud1name;
    String  ud2name;
    String  ud3name;
    String  ud4name;
    String  ud5name;
    String  ud6name;
    String notes;


    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUd1name() {
        return ud1name;
    }

    public void setUd1name(String ud1name) {
        this.ud1name = ud1name;
    }

    public String getUd2name() {
        return ud2name;
    }

    public void setUd2name(String ud2name) {
        this.ud2name = ud2name;
    }

    public String getUd3name() {
        return ud3name;
    }

    public void setUd3name(String ud3name) {
        this.ud3name = ud3name;
    }

    public String getUd4name() {
        return ud4name;
    }

    public void setUd4name(String ud4name) {
        this.ud4name = ud4name;
    }

    public String getUd5name() {
        return ud5name;
    }

    public void setUd5name(String ud5name) {
        this.ud5name = ud5name;
    }

    public String getUd6name() {
        return ud6name;
    }

    public void setUd6name(String ud6name) {
        this.ud6name = ud6name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return "DeviceinfoNamesDTO{" +
                "deviceid=" + deviceid +
                ", name='" + name + '\'' +
                ", ipAddress=" + ipAddress +
                ", location='" + location + '\'' +
                ", ud1name='" + ud1name + '\'' +
                ", ud2name='" + ud2name + '\'' +
                ", ud3name='" + ud3name + '\'' +
                ", ud4name='" + ud4name + '\'' +
                ", ud5name='" + ud5name + '\'' +
                ", ud6name='" + ud6name + '\'' +
                '}';
    }
}

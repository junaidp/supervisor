package com.wbc.supervisor.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by JIM on 12/31/2014.
 */
public class DeviceinfoDataDTO implements IsSerializable {
    String name;
    String ipAddress;
    String macAddress;
    String location;
    String ud1Name;
    String vendor;
    String category;
    String description;
    boolean isVerified;
    boolean isWireless;
    boolean isSwitch;
    boolean hasRedbox;
    int     criticalType;
    int descid;

    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public DeviceinfoDataDTO() {
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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUd1Name() {
        return ud1Name;
    }

    public void setUd1Name(String ud1Name) {
        this.ud1Name = ud1Name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isWireless() {
        return isWireless;
    }

    public void setWireless(boolean isWireless) {
        this.isWireless = isWireless;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean isSwitch) {
        this.isSwitch = isSwitch;
    }

    public boolean isHasRedbox() {
        return hasRedbox;
    }

    public void setHasRedbox(boolean hasRedbox) {
        this.hasRedbox = hasRedbox;
    }

    public int getCriticalType() {
        return criticalType;
    }

    public void setCriticalType(int criticalType) {
        this.criticalType = criticalType;
    }
}

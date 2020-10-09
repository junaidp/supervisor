package com.wbc.supervisor.shared.dashboard2dto;

import java.io.Serializable;

/**
 * Created by JIM on 5/14/2015.
 */
public class IntravueEventDTO implements Serializable {
    int eventid;
    int descid;
    String occurred;
    String ipaddress;
    String description;
    int type;
    int classid;

    public IntravueEventDTO() {
    }

    public IntravueEventDTO(int eventid, int descid, String occurred, String ipaddress, String description, int type, int classid) {
        this.eventid = eventid;
        this.descid = descid;
        this.occurred = occurred;
        this.ipaddress = ipaddress;
        this.description = description;
        this.type = type;
        this.classid = classid;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public String getOccurred() {
        return occurred;
    }

    public void setOccurred(String occurred) {
        this.occurred = occurred;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
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

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }
}

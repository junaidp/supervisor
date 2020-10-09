package com.wbc.supervisor.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by JIM on 2/8/2015.
 */
public class NamesDTO implements IsSerializable {
    int descid;
    String type;  // name, loc, ud1, ud2, ud3, ud4, ud5, ud6
    String name;

    public NamesDTO() {
        this.descid = 0;
        this.type = "";
        this.name = "";
    }

    public NamesDTO(int descid, String type, String name) {
        this.descid = descid;
        this.type = type;
        this.name = name;
    }

    public int getDescid() {
        return descid;
    }

    public void setDescid(int descid) {
        this.descid = descid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

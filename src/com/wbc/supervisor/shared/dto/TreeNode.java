package com.wbc.supervisor.shared.dto;

import java.io.Serializable;

/**
 * Created by Junaid on 11/9/2014.
 */
public class TreeNode implements Serializable {
    // This Class is optional , If we like we can simple send values from impl class
    private String name;
    private int id;
    private boolean open; // if the node will be open or close , we can decide here


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


}

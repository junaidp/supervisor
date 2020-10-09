package com.wbc.supervisor.shared.dashboard2dto;

import java.io.Serializable;

/**
 * Created by JIM on 9/13/2014.
 */
public class WbcNamesDTO implements Serializable{
    private String name1;
    private String name2;
    private int id;
    private int rank;

    public WbcNamesDTO(String name1, String name2, int id, int rank) {
        this.name1 = name1;
        this.name2 = name2;
        this.id = id;
        this.rank = rank;
    }

    public WbcNamesDTO() {
        this.name1 = "name1";
        this.name2 = "name2";
        this.id = 0;
        this.rank = 0;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "WbcNamesDTO{" +
                "name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", id=" + id +
                ", rank=" + rank +
                '}';
    }
}

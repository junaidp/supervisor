package com.wbc.supervisor.shared.dashboard2dto;

import java.io.Serializable;

/**
 * Created by JIM on 3/26/2015.
 */
public class KpiStatsRow implements Serializable {
    String nameToDisplay="";
    String ipaddress ="";
    // int type=0;  this should have been isSwitch
    int clevel=0;
    boolean isSwitch = false;
    int disc=0;
    int pt=0;   // ping over
    int pf=0;   // ping failure
    int bw=0;
    int link=0;
    int move=0;
    int newd=0;
    int ipc=0;  // ip change
    int macc = 0; // mac change
    int total=0;
    String upTime="0";

    public KpiStatsRow() {
    }


    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public KpiStatsRow(String nameToDisplay, String ip) {
        this.nameToDisplay = nameToDisplay;
        this.ipaddress = ip;
    }

    public String getNameToDisplay() {
        return nameToDisplay;
    }

    public void setNameToDisplay(String nameToDisplay) {
        this.nameToDisplay = nameToDisplay;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }


    public int getClevel() {
        return clevel;
    }

    public void setClevel(int clevel) {
        this.clevel = clevel;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean isSwitch) {
        this.isSwitch = isSwitch;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    public int getBw() {
        return bw;
    }

    public void setBw(int bw) {
        this.bw = bw;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getNewd() {
        return newd;
    }

    public void setNewd(int newd) {
        this.newd = newd;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIpc() {
        return ipc;
    }

    public void setIpc(int ipc) {
        this.ipc = ipc;
    }

    public int getMacc() {
        return macc;
    }

    public void setMacc(int macc) {
        this.macc = macc;
    }

    @Override
    public String toString() {
        return "KpiStatsRow{" +
                "nameToDisplay='" + nameToDisplay + '\'' +
                ", ip='" + ipaddress + '\'' +
                ", clevel=" + clevel +
                ", isSwitch=" + isSwitch +
                ", disc=" + disc +
                ", pt=" + pt +
                ", pf=" + pf +
                ", bw=" + bw +
                ", link=" + link +
                ", move=" + move +
                ", newd=" + newd +
                '}';
    }
}

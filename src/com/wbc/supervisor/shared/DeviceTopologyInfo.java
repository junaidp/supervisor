package com.wbc.supervisor.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by JIM on 11/24/2014.
 */
public class DeviceTopologyInfo implements IsSerializable {
     Integer parent;
     Integer child;
     String ipaddress;
     Integer portno;
     String  portname;
     String  name;
     String mac = "";
     boolean isConnected = false;
     Integer upPort;
     String upPortName;
    Integer networkid;
    Integer level;
    String macaddress;


    public DeviceTopologyInfo(Integer parent, Integer child, String ipaddress, String name) {
        this.parent = parent;
        this.child = child;
        this.ipaddress = ipaddress;
        this.name = name;
        this.portno = -1;
        this.portname = "";
        this.upPort = 0;
        this.upPortName = "";
        this.networkid =0;
    }

    public DeviceTopologyInfo() {
        parent = 0;
        child = 0;
        ipaddress = "0.0.0.0";
        portno = -1;
        portname = "no port name";
        name = "no name";
        upPort = 0;
        upPortName = "no up port name";
        this.networkid =0;
    }

    public Integer getNetworkid() {
        return networkid;
    }

    public void setNetworkid(Integer networkid) {
        this.networkid = networkid;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Integer getPortno() {
        return portno;
    }

    public void setPortno(Integer portno) {
        this.portno = portno;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUpPort() {
        return upPort;
    }

    public void setUpPort(Integer upPort) {
        this.upPort = upPort;
    }

    public String getUpPortName() {
        return upPortName;
    }

    public void setUpPortName(String upPortName) {
        this.upPortName = upPortName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String toShortString() {
        return "DeviceTopologyInfo{" +
                "parent=" + parent +
                ", child=" + child +
                ", ipaddress='" + ipaddress + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "DeviceTopologyInfo{" +
                "parent=" + parent +
                ", child=" + child +
                ", ipaddress='" + ipaddress + '\'' +
                ", macaddress='" + macaddress + '\'' +
                ", portno=" + portno +
                ", portname='" + portname + '\'' +
                ", name='" + name + '\'' +
                ", upPort=" + upPort +
                ", upPortName='" + upPortName + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}

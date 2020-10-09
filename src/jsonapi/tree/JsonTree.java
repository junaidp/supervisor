package jsonapi.tree;

import java.util.ArrayList;

/*
  "tree": {
    "isGhost": "0",
    "critical": "0",
    "displayName": "Scanner",
    "networkName": "",
    "ifinerrorsalert": 0,
    "deviceChildrenURI": "/iv2/api/devices/1/children",
    "deviceDetailsURI": "/iv2/api/devices/1",
    "additionalInterfaces": [],
    "crcerrorsalert": 0,
    "pinging": "1",
    "children": [    ],
    "isSwitch": "0",
    "root": "1",
    "networkId": "0",
    "uplinkPort": 0,
    "id": 1,
    "locY2": "28705.236481954078",
    "deviceEventsURI": "/iv2/api/devices/1/events",
    "thumbnailName": "",
    "isportopen": "0",
    "verified": "0",
    "locX": "0",
    "locstatus": "true",
    "locY": "0",
    "linkColor": "green",
    "thrAlarm": "0",
    "linkPortName": "",
    "deviceThresholdsURI": "/iv2/api/devices/1/thresholds",
    "isWireless": "0",
    "linkPort": "0",
    "name": "<database-root>",
    "uplinkPortName": "",
    "containerID": "",
    "locX2": "40807.46523026517"
  },


 */
public class JsonTree {

    private String sGhost;   // "0",
    private String critical;   // "0",
    private String displayName;   // "Scanner",
    private String networkName;   // "",
    private int ifinerrorsalert;   // 0,
    private String deviceChildrenURI;   // "/iv2/api/devices/1/children",
    private String deviceDetailsURI;   // "/iv2/api/devices/1",
    private ArrayList<JsonAdditionInterfaces> additionalInterfaces;   // [],
    private int crcerrorsalert;   // 0,
    private String pinging;   // "1",
    private ArrayList<JsonChildren> children;   // [    ],
    private String isSwitch;   // "0",
    private String root;   // "1",
    private String networkId;   // "0",
    private int uplinkPort;   // 0,
    private int id;   // 1,
    private String locY2;   // "28705.236481954078",
    private String deviceEventsURI;   // "/iv2/api/devices/1/events",
    private String thumbnailName;   // "",
    private String isportopen;   // "0",
    private String verified;   // "0",
    private String locX;   // "0",
    private String locstatus;   // "true",
    private String locY;   // "0",
    private String linkColor;   // "green",
    private String thrAlarm;   // "0",
    private String linkPortName;   // "",
    private String deviceThresholdsURI;   // "/iv2/api/devices/1/thresholds",
    private String isWireless;   // "0",
    private String linkPort;   // "0",
    private String name;   // "<database-root>",
    private String uplinkPortName;   // "",
    private String containerID;   // "",
    private String locX2;   // "40807.46523026517"

    public JsonTree() {
        additionalInterfaces = new ArrayList<JsonAdditionInterfaces>();
        children = new ArrayList<JsonChildren>();

    }

    public String getsGhost() {
        return sGhost;
    }

    public void setsGhost(String sGhost) {
        this.sGhost = sGhost;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public int getIfinerrorsalert() {
        return ifinerrorsalert;
    }

    public void setIfinerrorsalert(int ifinerrorsalert) {
        this.ifinerrorsalert = ifinerrorsalert;
    }

    public String getDeviceChildrenURI() {
        return deviceChildrenURI;
    }

    public void setDeviceChildrenURI(String deviceChildrenURI) {
        this.deviceChildrenURI = deviceChildrenURI;
    }

    public String getDeviceDetailsURI() {
        return deviceDetailsURI;
    }

    public void setDeviceDetailsURI(String deviceDetailsURI) {
        this.deviceDetailsURI = deviceDetailsURI;
    }

    public ArrayList<JsonAdditionInterfaces> getAdditionalInterfaces() {
        return additionalInterfaces;
    }

    public void setAdditionalInterfaces(ArrayList<JsonAdditionInterfaces> additionalInterfaces) {
        this.additionalInterfaces = additionalInterfaces;
    }

    public int getCrcerrorsalert() {
        return crcerrorsalert;
    }

    public void setCrcerrorsalert(int crcerrorsalert) {
        this.crcerrorsalert = crcerrorsalert;
    }

    public String getPinging() {
        return pinging;
    }

    public void setPinging(String pinging) {
        this.pinging = pinging;
    }

    public ArrayList<JsonChildren> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<JsonChildren> children) {
        this.children = children;
    }

    public String getIsSwitch() {
        return isSwitch;
    }

    public void setIsSwitch(String isSwitch) {
        this.isSwitch = isSwitch;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public int getUplinkPort() {
        return uplinkPort;
    }

    public void setUplinkPort(int uplinkPort) {
        this.uplinkPort = uplinkPort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocY2() {
        return locY2;
    }

    public void setLocY2(String locY2) {
        this.locY2 = locY2;
    }

    public String getDeviceEventsURI() {
        return deviceEventsURI;
    }

    public void setDeviceEventsURI(String deviceEventsURI) {
        this.deviceEventsURI = deviceEventsURI;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getIsportopen() {
        return isportopen;
    }

    public void setIsportopen(String isportopen) {
        this.isportopen = isportopen;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getLocX() {
        return locX;
    }

    public void setLocX(String locX) {
        this.locX = locX;
    }

    public String getLocstatus() {
        return locstatus;
    }

    public void setLocstatus(String locstatus) {
        this.locstatus = locstatus;
    }

    public String getLocY() {
        return locY;
    }

    public void setLocY(String locY) {
        this.locY = locY;
    }

    public String getLinkColor() {
        return linkColor;
    }

    public void setLinkColor(String linkColor) {
        this.linkColor = linkColor;
    }

    public String getThrAlarm() {
        return thrAlarm;
    }

    public void setThrAlarm(String thrAlarm) {
        this.thrAlarm = thrAlarm;
    }

    public String getLinkPortName() {
        return linkPortName;
    }

    public void setLinkPortName(String linkPortName) {
        this.linkPortName = linkPortName;
    }

    public String getDeviceThresholdsURI() {
        return deviceThresholdsURI;
    }

    public void setDeviceThresholdsURI(String deviceThresholdsURI) {
        this.deviceThresholdsURI = deviceThresholdsURI;
    }

    public String getIsWireless() {
        return isWireless;
    }

    public void setIsWireless(String isWireless) {
        this.isWireless = isWireless;
    }

    public String getLinkPort() {
        return linkPort;
    }

    public void setLinkPort(String linkPort) {
        this.linkPort = linkPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUplinkPortName() {
        return uplinkPortName;
    }

    public void setUplinkPortName(String uplinkPortName) {
        this.uplinkPortName = uplinkPortName;
    }

    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public String getLocX2() {
        return locX2;
    }

    public void setLocX2(String locX2) {
        this.locX2 = locX2;
    }

    @Override
    public String toString() {
        return "JsonTree{" +
                "sGhost='" + sGhost + '\'' +
                ", critical='" + critical + '\'' +
                ", displayName='" + displayName + '\'' +
                ", networkName='" + networkName + '\'' +
                ", ifinerrorsalert=" + ifinerrorsalert +
                ", deviceChildrenURI='" + deviceChildrenURI + '\'' +
                ", deviceDetailsURI='" + deviceDetailsURI + '\'' +
                ", additionalInterfaces=" + additionalInterfaces +
                ", crcerrorsalert=" + crcerrorsalert +
                ", pinging='" + pinging + '\'' +
                ", children=" + children +
                ", isSwitch='" + isSwitch + '\'' +
                ", root='" + root + '\'' +
                ", networkId='" + networkId + '\'' +
                ", uplinkPort=" + uplinkPort +
                ", id=" + id +
                ", locY2='" + locY2 + '\'' +
                ", deviceEventsURI='" + deviceEventsURI + '\'' +
                ", thumbnailName='" + thumbnailName + '\'' +
                ", isportopen='" + isportopen + '\'' +
                ", verified='" + verified + '\'' +
                ", locX='" + locX + '\'' +
                ", locstatus='" + locstatus + '\'' +
                ", locY='" + locY + '\'' +
                ", linkColor='" + linkColor + '\'' +
                ", thrAlarm='" + thrAlarm + '\'' +
                ", linkPortName='" + linkPortName + '\'' +
                ", deviceThresholdsURI='" + deviceThresholdsURI + '\'' +
                ", isWireless='" + isWireless + '\'' +
                ", linkPort='" + linkPort + '\'' +
                ", name='" + name + '\'' +
                ", uplinkPortName='" + uplinkPortName + '\'' +
                ", containerID='" + containerID + '\'' +
                ", locX2='" + locX2 + '\'' +
                '}';
    }
}

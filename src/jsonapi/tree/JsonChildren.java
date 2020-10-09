package jsonapi.tree;

/*
    "children": [
      {
        "isGhost": "0",
        "critical": "3",
        "displayName": "192.168.58.1",
        "networkName": "Network-58",
        "ifinerrorsalert": 0,
        "deviceChildrenURI": "/iv2/api/devices/91188/children",
        "deviceDetailsURI": "/iv2/api/devices/91188",
        "additionalInterfaces": [
          {
            "ip": "192.168.41.1",
            "mac": "00:23:33:6e:31:3f"
          },
          {
            "ip": "192.168.56.1",
            "mac": "00:23:33:6e:31:3f"
          },
          {
            "ip": "192.168.57.1",
            "mac": "00:23:33:6e:31:3f"
          },
          {
            "ip": "192.168.59.1",
            "mac": "00:23:33:6e:31:3f"
          },
          {
            "ip": "192.168.253.126",
            "mac": "00:23:33:6e:31:3f"
          }
        ],
        "netmask": "255.255.255.0",
        "crcerrorsalert": 0,
        "pinging": "1",
        "children": [
        ],
        "isSwitch": "0",
        "primaryIP": "192.168.58.170",
        "networkId": "18",
        "uplinkPort": 0,
        "id": 186249,
        "locY2": "38304.337913978445",
        "deviceEventsURI": "/iv2/api/devices/186249/events",
        "thumbnailName": "",
        "viewName7": "Jetway Information Co., Ltd.",
        "viewName1": "192.168.58.170",
        "isportopen": "0",
        "verified": "1",
        "locX": "377.79081553382116",
        "locstatus": "",
        "locY": "-399.7175248826229",
        "linkColor": "green",
        "thrAlarm": "0",
        "parentDetailsURI": "/iv2/api/devices/1",
        "primaryMAC": "00:30:18:c7:de:6d",
        "linkPortName": "",
        "deviceThresholdsURI": "/iv2/api/devices/186249/thresholds",
        "isWireless": "0",
        "linkPort": "0",
        "name": "no name",
        "uplinkPortName": "",
        "containerID": "",
        "locX2": "46434.26646717024"
      }

 */

import java.util.ArrayList;

public class JsonChildren {

    private String isGhost;   // "0",
    private String critical;   // "3",
    private String displayName;   // "192.168.58.1",
    private String networkName;   // "Network-58",
    private int ifinerrorsalert;   // 0,
    private String deviceChildrenURI;   // "/iv2/api/devices/91188/children",
    private String deviceDetailsURI;   // "/iv2/api/devices/91188",
    private ArrayList<JsonAdditionInterfaces> additionalInterfaces;   // [
    private String netmask;   // "255.255.255.0",
    private int crcerrorsalert;   // 0,
    private String pinging;   // "1",
    private ArrayList<JsonChildren> children;   // []
    private String isSwitch;   // "0",
    private String primaryIP;   // "192.168.58.170",
    private String networkId;   // "18",
    private int  uplinkPort;   // 0,
    private int id;   // 186249,
    private String locY2;   // "38304.337913978445",
    private String deviceEventsURI;   // "/iv2/api/devices/186249/events",
    private String thumbnailName;   // "",
    private String viewName7;   // "Jetway Information Co., Ltd.",
    private String viewName1;   // "192.168.58.170",
    private String isportopen;   // "0",
    private String verified;   // "1",
    private String locX;   // "377.79081553382116",
    private String locstatus;   // "",
    private String locY;   // "-399.7175248826229",
    private String linkColor;   // "green",
    private String thrAlarm;   // "0",
    private String parentDetailsURI;   // "/iv2/api/devices/1",
    private String primaryMAC;   // "00:30:18:c7:de:6d",
    private String linkPortName;   // "",
    private String deviceThresholdsURI;   // "/iv2/api/devices/186249/thresholds",
    private String isWireless;   // "0",
    private String linkPort;   // "0",
    private String name;   // "no name",
    private String uplinkPortName;   // "",
    private String containerID;   // "",
    private String locX2;   // "46434.26646717024"

}

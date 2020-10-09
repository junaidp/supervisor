package jsonapi.event;

public class JsonEvent {

    /*
        events": [
            {
              "date": "2018-08-29 22:23:03",
              "critical": 3,
              "descid": 95250,
              "ip": "192.168.57.16",
              "deviceChildrenURI": "/iv2/api/devices/95250/children",
              "type": "3",
              "deviceDetailsURI": "/iv2/api/devices/95250",
              "deviceThresholdsURI": "/iv2/api/devices/95250/thresholds",
              "networkname": "Network-57",
              "networkid": 10,
              "devicename": "DEMO-LAB-hirsch-B",
              "id": 2639522,
              "deviceEventsURI": "/iv2/api/devices/95250/events",
              "class": "114",
              "timestamp": "1535606583000",
              "desc": "crcAlignErrors counter for IP 192.168.57.16 Port: \"Module: 1 Port: 1 - 10/100 Mbit TX\" has increased by 3 since 2018-08-29 21:23:03 GMT-07:00 Current value: 389"
            },
     */

    private String date;
    private String critical;
    private String descid;
    private String ip;
    private String deviceChildrenURI;
    private String type;
    private String deviceDetailsURI;
    private String networkname;
    private String networkid;
    private String devicename;
    private String id;
    private String deviceEventsURI;
    private String eventClass;
    private String timestamp;
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getDescid() {
        return descid;
    }

    public void setDescid(String descid) {
        this.descid = descid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceChildrenURI() {
        return deviceChildrenURI;
    }

    public void setDeviceChildrenURI(String deviceChildrenURI) {
        this.deviceChildrenURI = deviceChildrenURI;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceDetailsURI() {
        return deviceDetailsURI;
    }

    public void setDeviceDetailsURI(String deviceDetailsURI) {
        this.deviceDetailsURI = deviceDetailsURI;
    }

    public String getNetworkname() {
        return networkname;
    }

    public void setNetworkname(String networkname) {
        this.networkname = networkname;
    }

    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceEventsURI() {
        return deviceEventsURI;
    }

    public void setDeviceEventsURI(String deviceEventsURI) {
        this.deviceEventsURI = deviceEventsURI;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }




}

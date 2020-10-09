package jsonapi.network;

/*
"networkInfo":
        {"foundIps":"72",
        "networks":[
            {
            "top":"192.168.58.1",
            "name":"Network-58",
            "ivagent":"",
            "range":[
                {"top":"192.168.58.1",
                "from":"192.168.58.1",
                "to":"192.168.58.254"}
                ],
            "id":"8",
            "netgroup":"0",
            "community":""
          },
             {"top":"192.168.57.1","name":"Network-57","ivagent":"","range":[{"top":"192.168.57.1","from":"192.168.57.1","to":"192.168.57.254"}],"id":"10","netgroup":"0","community":""},
             {"top":"192.168.58.170","name":"Network-192","ivagent":"192.168.58.170","range":[{"top":"192.168.58.170","from":"192.168.1.1","to":"192.168.1.254"},
             {"top":"192.168.58.170","from":"192.168.58.170","to":"192.168.58.170"}],"id":"18","netgroup":"1","community":""}
         ]
         },
 */

import java.util.ArrayList;
import java.util.Map;

public class JsonNetworkInfo {
    private String foundIps;
    private ArrayList<JsonNetworks> networks;

    public String getFoundIps() {
        return foundIps;
    }

    public void setFoundIps(String foundIps) {
        this.foundIps = foundIps;
    }

    public ArrayList<JsonNetworks> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<JsonNetworks> networks) {
        this.networks = networks;
    }

    public static class JsonNetworks {
        private String top;
        private String name;
        private String ivagent;
        private ArrayList<JsonRange> range;
        private String id;
        private String netgroup;
        private String community;

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIvagent() {
            return ivagent;
        }

        public void setIvagent(String ivagent) {
            this.ivagent = ivagent;
        }

        public ArrayList<JsonRange> getRange() {
            return range;
        }

        public void setRange(ArrayList<JsonRange> range) {
            this.range = range;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNetgroup() {
            return netgroup;
        }

        public void setNetgroup(String netgroup) {
            this.netgroup = netgroup;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }
    }

    public static class JsonRange {
        private String top;
        private String from;
        private String to;

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }

    /*
        {"systemInfo":
            {"hostIp":
                {"NIC 1":"10.1.1.173","NIC 2":"192.168.56.1"},
             "windows":"1",
             "hostNetMask":
                {"NIC 1":"255.255.255.0","NIC 2":"255.255.255.0"},
            "version":"2.0",
            "properties":
                {"scannerSpeedIndex":"0","readCommunity":"public"}
        },
         */
    public static class JsonSystemInfo {
        //private ArrayList<JsonHostIp> hostIp;
        private Map hostIp;
        private String windows;
        private Map hostNetMask;
        private String version;
        private Map properties;

        public void setHostNetMask(Map hostNetMask) {
            this.hostNetMask = hostNetMask;
        }

        public Map getHostIp() {
            return hostIp;
        }

        public void setHostIp(Map hostIp) {
            this.hostIp = hostIp;
        }

        public String getWindows() {
            return windows;
        }

        public void setWindows(String windows) {
            this.windows = windows;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Map getHostNetMask() {
            return hostNetMask;
        }

        public Map getProperties() {
            return properties;
        }

        public void setProperties(Map properties) {
            this.properties = properties;
        }
    }
}

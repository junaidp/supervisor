package jsonapi.network;

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
"isError":0,
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
"snmpSettings":[
    {"name":"v2Public","key":"1"},
    {"name":"v1Public","key":"2"}
    ]
}
 */

import java.util.ArrayList;

public class JsonNetworkConfiguration {
    private JsonNetworkInfo.JsonSystemInfo systemInfo;
    private int isError;
    private JsonNetworkInfo networkInfo;
    private ArrayList<JsonSnmpSettings> snmpSettings;

    public JsonNetworkInfo.JsonSystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(JsonNetworkInfo.JsonSystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    public int getIsError() {
        return isError;
    }

    public void setIsError(int isError) {
        this.isError = isError;
    }

    public JsonNetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    public void setNetworkInfo(JsonNetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }

    public ArrayList<JsonSnmpSettings> getSnmpSettings() {
        return snmpSettings;
    }

    public void setSnmpSettings(ArrayList<JsonSnmpSettings> snmpSettings) {
        this.snmpSettings = snmpSettings;
    }

 /*
    snmpSettings":[
    {"name":"v2Public","key":"1"},
    {"name":"v1Public","key":"2"}
    ]

     */

    static class JsonSnmpSettings {
        private String name;
        private String key;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}

package jsonapi.tree;

/*
   {
               "ip;   // "192.168.41.1",
               "mac;   // "00:23:33:6e:31:3f"
    },
 */
public class JsonAdditionInterfaces {
    private String ip;
    private String mac;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "JsonAdditionInterfaces{" +
                "ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}

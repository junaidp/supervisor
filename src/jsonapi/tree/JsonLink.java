package jsonapi.tree;

/*
  "link": {
    "parent": "192.168.57.13:20 (bandwidth data source) Link speed 100 Mbps",
    "device": "192.168.57.14"
  },
 */
public class JsonLink {
    private String parent;
    private String device;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "JsonLink{" +
                "parent='" + parent + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}

package jsonapi.tree;

/*
  {"svcStatus": {
    "expired": 1,
    "verifies": 1,
    "version": "3.2.0 5/13/19",
    "expirationDate": "06/2020"
  }}
 */
public class JsonSvcStatus {
    private int expired;
    private int verifies;
    private String version;
    private String expirationDate;

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public int getVerifies() {
        return verifies;
    }

    public void setVerifies(int verifies) {
        this.verifies = verifies;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "JsonSvcStatus{" +
                "expired=" + expired +
                ", verifies=" + verifies +
                ", version='" + version + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}

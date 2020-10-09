package jsonapi.threshold;

/*
{
    "pingenabled": "1",
    "pingfailure": "0",
    "pingthreshid": "447",
    "pingresponse": "1018.3",
    "pingfailurethreshold": "20",
    "pingthreshold": "30000",
    "id": 95238,
    "parentid": "117439"
  }
 */
public class JsonPingInfo {

     private int    pingenabled;   // "1",
     private double     pingfailure;   // "0",
     private int        pingthreshid;   // "447",
     private double     pingresponse;   // "1018.3",
     private int pingfailurethreshold;   // "20",
     private int pingthreshold;   // "30000",
     private int id;   // 95238,
     private int parentid;   // "117439"

    public int getPingenabled() {
        return pingenabled;
    }

    public void setPingenabled(int pingenabled) {
        this.pingenabled = pingenabled;
    }

    public double getPingfailure() {
        return pingfailure;
    }

    public void setPingfailure(double pingfailure) {
        this.pingfailure = pingfailure;
    }

    public int getPingthreshid() {
        return pingthreshid;
    }

    public void setPingthreshid(int pingthreshid) {
        this.pingthreshid = pingthreshid;
    }

    public double getPingresponse() {
        return pingresponse;
    }

    public void setPingresponse(double pingresponse) {
        this.pingresponse = pingresponse;
    }

    public int getPingfailurethreshold() {
        return pingfailurethreshold;
    }

    public void setPingfailurethreshold(int pingfailurethreshold) {
        this.pingfailurethreshold = pingfailurethreshold;
    }

    public int getPingthreshold() {
        return pingthreshold;
    }

    public void setPingthreshold(int pingthreshold) {
        this.pingthreshold = pingthreshold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "JsonPingInfo{" +
                "pingenabled=" + pingenabled +
                ", pingfailure=" + pingfailure +
                ", pingthreshid=" + pingthreshid +
                ", pingresponse=" + pingresponse +
                ", pingfailurethreshold=" + pingfailurethreshold +
                ", pingthreshold=" + pingthreshold +
                ", id=" + id +
                ", parentid=" + parentid +
                '}';
    }
}

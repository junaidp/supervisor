package jsonapi.threshold;

/*
 {
    "bwenabled": "1",
    "bwenabled": "117439",
    "uid": "95238",
    "transmitbandwidth": "0.0185771",
    "bwthreshid": "554",
    "receivethreshold": "30",
    "transmitthreshold": "30",
    "speed": "100000000",
    "receivebandwidth": "0.0171075"
  },
 */
public class JsonBandwidthInfo {
    private int bwenabled;   // "117439",
    private int uid;   // "95238",
    private double transmitbandwidth;   // "0.0185771",
    private int bwthreshid;   // "554",
    private int receivethreshold;   // "30",
    private int transmitthreshold;   // "30",
    private int speed;   // "100000000",
    private double receivebandwidth;   // "0.0171075"

    public int getBwenabled() {
        return bwenabled;
    }

    public void setBwenabled(int bwenabled) {
        this.bwenabled = bwenabled;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getTransmitbandwidth() {
        return transmitbandwidth;
    }

    public void setTransmitbandwidth(double transmitbandwidth) {
        this.transmitbandwidth = transmitbandwidth;
    }

    public int getBwthreshid() {
        return bwthreshid;
    }

    public void setBwthreshid(int bwthreshid) {
        this.bwthreshid = bwthreshid;
    }

    public int getReceivethreshold() {
        return receivethreshold;
    }

    public void setReceivethreshold(int receivethreshold) {
        this.receivethreshold = receivethreshold;
    }

    public int getTransmitthreshold() {
        return transmitthreshold;
    }

    public void setTransmitthreshold(int transmitthreshold) {
        this.transmitthreshold = transmitthreshold;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getReceivebandwidth() {
        return receivebandwidth;
    }

    public void setReceivebandwidth(double receivebandwidth) {
        this.receivebandwidth = receivebandwidth;
    }

    @Override
    public String toString() {
        return "JsonBandwidthInfo{" +
                "bwenabled=" + bwenabled +
                ", uid=" + uid +
                ", transmitbandwidth=" + transmitbandwidth +
                ", bwthreshid=" + bwthreshid +
                ", receivethreshold=" + receivethreshold +
                ", transmitthreshold=" + transmitthreshold +
                ", speed=" + speed +
                ", receivebandwidth=" + receivebandwidth +
                '}';
    }
}

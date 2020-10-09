package jsonapi.threshold;

/*
{
      "receiveBandwidthPeak": "NaN",
      "sampleno": 25621382,
      "transmitBandwidthPeak": "NaN"
    },
 */


public class JsonBandwidthSample {
    private double receiveBandwidthPeak;
    private int sampleno;
    private double transmitBandwidthPeak;

    public double getReceiveBandwidthPeak() {
        return receiveBandwidthPeak;
    }

    public void setReceiveBandwidthPeak(double receiveBandwidthPeak) {
        this.receiveBandwidthPeak = receiveBandwidthPeak;
    }

    public int getSampleno() {
        return sampleno;
    }

    public void setSampleno(int sampleno) {
        this.sampleno = sampleno;
    }

    public double getTransmitBandwidthPeak() {
        return transmitBandwidthPeak;
    }

    public void setTransmitBandwidthPeak(double transmitBandwidthPeak) {
        this.transmitBandwidthPeak = transmitBandwidthPeak;
    }

    @Override
    public String toString() {
        return "JsonBandwidthSample{" +
                "receiveBandwidthPeak=" + receiveBandwidthPeak +
                ", sampleno=" + sampleno +
                ", transmitBandwidthPeak=" + transmitBandwidthPeak +
                '}';
    }
}

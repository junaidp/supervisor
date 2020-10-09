package jsonapi.threshold;

/*
{
    "maxreceiveBandwidthPeak": 0.0198914,
    "maxtransmitBandwidthPeak": 0.0425271
  },
 */
public class JsonMaxbandwidthSample {
    private double maxreceiveBandwidthPeak;
    private double maxtransmitBandwidthPeak;

    public double getMaxreceiveBandwidthPeak() {
        return maxreceiveBandwidthPeak;
    }

    public void setMaxreceiveBandwidthPeak(double maxreceiveBandwidthPeak) {
        this.maxreceiveBandwidthPeak = maxreceiveBandwidthPeak;
    }

    public double getMaxtransmitBandwidthPeak() {
        return maxtransmitBandwidthPeak;
    }

    public void setMaxtransmitBandwidthPeak(double maxtransmitBandwidthPeak) {
        this.maxtransmitBandwidthPeak = maxtransmitBandwidthPeak;
    }

    @Override
    public String toString() {
        return "JsonMaxbandwidthSample{" +
                "maxreceiveBandwidthPeak=" + maxreceiveBandwidthPeak +
                ", maxtransmitBandwidthPeak=" + maxtransmitBandwidthPeak +
                '}';
    }
}

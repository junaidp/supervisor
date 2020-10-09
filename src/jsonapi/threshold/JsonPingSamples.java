package jsonapi.threshold;

/*
{
      "pingResponsePeak": 20045,
      "pingFailurePeak": 0,
      "sampleno": 25621383
    },
 */

public class JsonPingSamples {
    private String pingResponsePeak;
    private String pingFailurePeak;
    private int sampleno;

    public String getPingResponsePeak() {
        return pingResponsePeak;
    }

    public void setPingResponsePeak(String pingResponsePeak) {
        this.pingResponsePeak = pingResponsePeak;
    }

    public String getPingFailurePeak() {
        return pingFailurePeak;
    }

    public void setPingFailurePeak(String pingFailurePeak) {
        this.pingFailurePeak = pingFailurePeak;
    }

    public int getSampleno() {
        return sampleno;
    }

    public void setSampleno(int sampleno) {
        this.sampleno = sampleno;
    }

    @Override
    public String toString() {
        return "JasonPingSamples{" +
                "pingResponsePeak='" + pingResponsePeak + '\'' +
                ", pingFailurePeak='" + pingFailurePeak + '\'' +
                ", sampleno=" + sampleno +
                '}';
    }
}

package jsonapi.threshold;

/*
{
    "maxpingFailurePeak": 0,
    "maxpingResponsePeak": 167638
  },
 */
public class JsonMaxPingSample {
    private int maxpingFailurePeak;
    private int maxpingResponsePeak;

    public int getMaxpingFailurePeak() {
        return maxpingFailurePeak;
    }

    public void setMaxpingFailurePeak(int maxpingFailurePeak) {
        this.maxpingFailurePeak = maxpingFailurePeak;
    }

    public int getMaxpingResponsePeak() {
        return maxpingResponsePeak;
    }

    public void setMaxpingResponsePeak(int maxpingResponsePeak) {
        this.maxpingResponsePeak = maxpingResponsePeak;
    }

    @Override
    public String toString() {
        return "JsonMaxPingSample{" +
                "maxpingFailurePeak=" + maxpingFailurePeak +
                ", maxpingResponsePeak=" + maxpingResponsePeak +
                '}';
    }
}

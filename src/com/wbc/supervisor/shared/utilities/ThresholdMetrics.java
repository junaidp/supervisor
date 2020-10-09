package com.wbc.supervisor.shared.utilities;

/**
 * Created by JIM on 1/25/2015.
 */
public class ThresholdMetrics {
    int     size=0;
    int     numZero=0;
    double  sAvg=0;
    double  nonZeroSavg=0;
    double  mAvg=0;
    double  min=0;
    double  max=0;
    double  minOverZero=0;
    double  stdDev=0;

    public int getSize() {
        return size;
    }

    public int getNumZero() {
        return numZero;
    }

    public double getsAvg() {
        return sAvg;
    }

    public double getmAvg() {
        return mAvg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMinOverZero() {
        return minOverZero;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNumZero(int numZero) {
        this.numZero = numZero;
    }

    public void setsAvg(double sAvg) {
        this.sAvg = sAvg;
    }

    public void setmAvg(double mAvg) {
        this.mAvg = mAvg;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMinOverZero(double minOverZero) {
        this.minOverZero = minOverZero;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public double getNonZeroSavg() {
        return nonZeroSavg;
    }

    public void setNonZeroSavg(double nonZeroSavg) {
        this.nonZeroSavg = nonZeroSavg;
    }

    @Override
    public String toString() {
        return "ThresholdMetrics{" +
                "size=" + size +
                ", numZero=" + numZero +
                ", sAvg=" + sAvg +
                ", nonZeroSavg=" + nonZeroSavg +
                ", mAvg=" + mAvg +
                ", min=" + min +
                ", max=" + max +
                ", minOverZero=" + minOverZero +
                ", stdDev=" + stdDev +
                '}';
    }

    public static String getCsvHeader() {
        return "size, zeros,  sAvg,  n0sAvg,   min,    max,   minOver0,  stdDev";
    }

    public String getFormattedData() {
        StringBuilder sb = new StringBuilder();
        sb.append( size);
        sb.append(" , ");
        sb.append( numZero );
        sb.append(" , ");
        sb.append( sAvg );
        sb.append(" , ");
        sb.append( nonZeroSavg );
        sb.append(" , ");
        sb.append( min );
        sb.append(" , ");
        sb.append( max );
        sb.append(" , ");
        sb.append( minOverZero );
        sb.append(" , ");
        sb.append( stdDev );
        return sb.toString();
    }
}

package com.wbc.supervisor.shared.dashboardutilities.threshold;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

public class StatData implements IsSerializable
{
    public int minimumCount = 300;
    private int count;
    private double avg;
    private double min;
    private double max;
    private double stddev;
    private double cur_threshold=0;
    private double recommended=0;
    private ArrayList<Integer> stdOverCounts;
    private int threshid;



    public StatData() {
        stdOverCounts = new ArrayList<>();
        for (int x=0; x<6; x++) stdOverCounts.add(0);
    }

    public double getCur_threshold() {
        return cur_threshold;
    }

    public void setCur_threshold(double cur_threshold) {
        this.cur_threshold = cur_threshold;
    }

    public ArrayList<Integer> getStdOverCounts() {
        return stdOverCounts;
    }

    public void setStdOverCounts(ArrayList<Integer> stdOverCounts) {
        this.stdOverCounts = stdOverCounts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStddev() {
        return stddev;
    }

    public void setStddev(double stddev) {
        this.stddev = stddev;
    }

    public double getRecommended() {
        return recommended;
    }

    public void setRecommended(double recommended) {
        this.recommended = recommended;
    }

    public int getThreshid() {
        return threshid;
    }

    public void setThreshid(int threshid) {
        this.threshid = threshid;
    }

    public String getStdOverToString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "<1=");
        sb.append( stdOverCounts.get(0));
        sb.append( ",>1=");
        sb.append( stdOverCounts.get(1));
        sb.append( ",>2=");
        sb.append( stdOverCounts.get(2));
        sb.append( ",>3=");
        sb.append( stdOverCounts.get(3));
        sb.append( ",>5=");
        sb.append( stdOverCounts.get(4));
        sb.append( ",>10=");
        sb.append( stdOverCounts.get(5));
        return sb.toString();
    }

}

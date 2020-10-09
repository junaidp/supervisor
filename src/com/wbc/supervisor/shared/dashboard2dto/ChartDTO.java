package com.wbc.supervisor.shared.dashboard2dto;

import java.io.Serializable;

public class ChartDTO implements Serializable{

    String sampleDate;
    int yData;

    public ChartDTO() {
        sampleDate="";
        yData=0;
    }

    public ChartDTO(String sampleDate, int yData) {
        this.sampleDate = sampleDate;
        this.yData = yData;
    }

    public String getSampleDate() {
        return sampleDate;
    }
    public void setSampleDate(String sampleDate) {
        this.sampleDate = sampleDate;
    }
    public int getYData() {
        return yData;
    }
    public void setYData(int yData) {
        this.yData = yData;
    }

}

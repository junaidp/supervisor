package com.wbc.supervisor.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChartDTO implements IsSerializable{

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

package com.wbc.supervisor.shared.dto;


import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JIM on 1/29/2015.
 */
public class DataBasedChartSeriesDTO implements Serializable {
    WbcSeriesInfo wbcSeriesInfo;
    ArrayList<Number> dataList;

    public DataBasedChartSeriesDTO() {
        dataList = new ArrayList<Number>();
        wbcSeriesInfo = new WbcSeriesInfo();
    }

    public WbcSeriesInfo getWbcSeriesInfo() {
        return wbcSeriesInfo;
    }

    public void setWbcSeriesInfo(WbcSeriesInfo wbcSeriesInfo) {
        this.wbcSeriesInfo = wbcSeriesInfo;
    }

    public ArrayList<Number> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Number> dataList) {
        this.dataList = dataList;
    }
}

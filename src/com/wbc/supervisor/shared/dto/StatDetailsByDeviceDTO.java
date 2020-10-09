package com.wbc.supervisor.shared.dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JIM on 2/7/2015.
 */
public class StatDetailsByDeviceDTO {
    ArrayList<String> dates;
    HashMap<Integer,DeviceStatDetails> deviceDataMap;
    int numValues;
    String chartTitle ="unknown";

    public StatDetailsByDeviceDTO( int numberOfValues ) {
        numValues = numberOfValues;
        dates = new ArrayList<String>(numValues);
        deviceDataMap = new HashMap<Integer,DeviceStatDetails>();
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public HashMap<Integer, DeviceStatDetails> getDeviceDataMap() {
        return deviceDataMap;
    }

    public void setDeviceDataMap(HashMap<Integer, DeviceStatDetails> deviceDataMap) {
        this.deviceDataMap = deviceDataMap;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StatDetailsByDeviceDTO--------\r\n" );
        sb.append("Dates -> ");
        for (String s : dates) {
            sb.append(s + "\r\n");
        }
        sb.append("\r\n");
        sb.append("Values -----------------\r\n");
        for (Integer descid : deviceDataMap.keySet()) {
            DeviceStatDetails detail = deviceDataMap.get(descid);
            sb.append("deviceid " + descid);
            sb.append( detail.toString());
            sb.append("\r\n");
        }
        return sb.toString();
    }
}

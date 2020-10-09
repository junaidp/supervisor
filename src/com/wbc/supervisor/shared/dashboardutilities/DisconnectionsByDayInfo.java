package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DisconnectionsByDayInfo extends UtilGrid implements IsSerializable
{
    private ErrorInfo errorInfo;
    private HashMap<Integer, DisconnectionByDayData> disconnectionByDayDataHashMap;
    private ArrayList<Date> dates;


    public DisconnectionsByDayInfo() {
        errorInfo = new ErrorInfo();
        disconnectionByDayDataHashMap = new HashMap<>();
        dates = new ArrayList<>();
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public HashMap<Integer, DisconnectionByDayData> getDisconnectionByDayDataHashMap() {
        return disconnectionByDayDataHashMap;
    }

    public void setDisconnectionByDayDataHashMap(HashMap<Integer, DisconnectionByDayData> disconnectionByDayDataHashMap) {
        this.disconnectionByDayDataHashMap = disconnectionByDayDataHashMap;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }
}

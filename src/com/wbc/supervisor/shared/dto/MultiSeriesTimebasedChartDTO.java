package com.wbc.supervisor.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class MultiSeriesTimebasedChartDTO implements IsSerializable
{

    /*
        Each element of the ArrayList is a series
         */
    LinkedHashMap<String,ArrayList<Number>> data ;  // String is a date as long, array are series values for that date


    public MultiSeriesTimebasedChartDTO() {
        data = new LinkedHashMap<String, ArrayList<Number>>();
    }

    /*public ArrayList<Number> getDataForTime( String datetime ) {
        return data.get(datetime);
    }
    */


    public LinkedHashMap<String, ArrayList<Number>> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, ArrayList<Number>> data) {
        this.data = data;
    }


    //TODO Add clear() method
    //TODO add update() method
}

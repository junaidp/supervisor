package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;

import java.util.ArrayList;

public class GettingDetailedPingDataEvent extends GwtEvent<GettingDetailedPingDataEventHandler> {

    private ArrayList<DataBasedChartSeriesDTO> dataBasedChartSeriesDTO;
    private CoreChart coreChart;
    private AbstractDataTable dt;



    public static Type<GettingDetailedPingDataEventHandler> TYPE = new Type<GettingDetailedPingDataEventHandler>();



    public GettingDetailedPingDataEvent(ArrayList<DataBasedChartSeriesDTO> dataBasedChartSeriesDTO, CoreChart coreChart, AbstractDataTable dt) {
        this.dataBasedChartSeriesDTO = dataBasedChartSeriesDTO;
        this.coreChart = coreChart;
        this.dt = dt;

    }


    @Override
    public Type<GettingDetailedPingDataEventHandler> getAssociatedType() {
    return TYPE;
}

@Override
    protected void dispatch(GettingDetailedPingDataEventHandler handler) {
    handler.onBrowse(this);

}

    public ArrayList<DataBasedChartSeriesDTO> getDataBasedChartSeriesDTO() {
        return dataBasedChartSeriesDTO;
    }

    public void setDataBasedChartSeriesDTO(ArrayList<DataBasedChartSeriesDTO> dataBasedChartSeriesDTO) {
        this.dataBasedChartSeriesDTO = dataBasedChartSeriesDTO;
    }



    public AbstractDataTable getDt() {
        return dt;
    }

    public void setDt(AbstractDataTable dt) {
        this.dt = dt;
    }

    public CoreChart getCoreChart() {
        return coreChart;
    }

    public void setCoreChart(CoreChart coreChart) {
        this.coreChart = coreChart;
    }

}



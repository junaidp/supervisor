package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.east;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.events.TimerEvent;
import com.wbc.supervisor.client.dashboard2.events.TimerEventHandler;
import com.wbc.supervisor.client.dashboard2.graphics.gwtchart.WbcGwtTimeseriesChart;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;
import com.wbc.supervisor.shared.utilities.GraphTimeCalculator;


import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Junaid on 6/27/2015.
 */
public class ReceiveBandwidthGraph extends FlowPanel {

    private int  hoursToGraph = 8;
    long lastThresholdMinute = 0;
    WbcNameWidget currentNetwork=null;
    private static Logger logger = Logger.getLogger("ReceiveBandwidthGraph.class");
    WbcGwtTimeseriesChart chart;
    VerticalPanel vpnlChart = new VerticalPanel();
    private ArrayList<DataBasedChartSeriesDTO> dataBasedChartSeriesDTOs = null;

    public ReceiveBandwidthGraph(){
        add(new HTML("Receive Bandwidth Graph"));
//        addStyleName("stopped");
        layout();
    }

    public void layout(){
       add(vpnlChart);
       chart = new WbcGwtTimeseriesChart();
        updateGraphSize();
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                updateGraphSize();
            }
        });

        supervisor.eventBus.addHandler(TimerEvent.TYPE,
                new TimerEventHandler() {
                    public void onTimer(TimerEvent event) {
                        if ( event.getPurpose().equals("new_sample")) {
                            long newSample = event.getMillis();
                            if ( newSample > lastThresholdMinute) {
                                lastThresholdMinute = newSample;

                                supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
                                    public void onNetworkChange(SelectedNetworkChangeEvent event) {
                                        WbcNameWidget newNetwork = event.getWbcNameWidget();
                                        if (newNetwork != currentNetwork ) {
                                            logger.info("PingDetailsWestPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                                            currentNetwork = newNetwork;
                                            if (lastThresholdMinute != 0) {
                                                fetchData(currentNetwork.getId(), lastThresholdMinute);
                                            } else {
                                                logger.info("PingDetailsEastPanel got network change event but last sample is 0  " );
                                            }
                                        } else {
                                            logger.info("PingDetailsEastPanel got network change event but network not changed? " + newNetwork.getName1());
                                        }
                                    }
                                });
                            } else {
                                logger.info("PingDetailsEastPanel: timer: NOT NEW sample " + newSample);
                            }
                        }
                    }
                });

    }

    private void updateGraphSize() {

        try {
            int browserWidth = Window.getClientWidth();
            int westPanelWidth = Window.getClientWidth() / 5;
            int browserHalfWidth = browserWidth - 178 - westPanelWidth;
            int halfHeight = Window.getClientHeight() / 2;
            int browserHalfHeight = halfHeight - 110;
            if(browserHalfWidth >= com.wbc.supervisor.client.dashboard2.DashboardConstants.TAB_SIZE) {
//                chart.setWidth(browserHalfWidth);
            }else{
//                chart.setWidth(DashboardConstants.TAB_SIZE);
            }
            chart.setWidth(Window.getClientWidth() - 450);
            chart.setHeight(browserHalfHeight);
            drawChart();
             }catch(Exception ex){}
    }

    private void fetchData( int networkId, long endTime  ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        try {
            GraphTimeCalculator calculator = new GraphTimeCalculator( hoursToGraph, 240 );
            final long startTime = calculator.setEndtime(endTime );
            int samplesPerPoint = calculator.getMinuteSamplesPerGraphPoint();

            dashboard2Service.getDetailedPingData( networkId, "savg", 2, startTime, endTime, samplesPerPoint, new AsyncCallback<ArrayList<DataBasedChartSeriesDTO>>(){

                @Override
                public void onFailure(Throwable caught) {

                    logger.info("stats not fetched!");
                }

                @Override
                public void onSuccess(ArrayList<DataBasedChartSeriesDTO> dto) {
                        dataBasedChartSeriesDTOs = dto;
                        drawChart();

                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void drawChart() {

        vpnlChart.clear();
        chart.drawDatabasedChart("LINE", dataBasedChartSeriesDTOs, vpnlChart, false );  // true = show debug info in drawing chart
        logger.info("OnSuccess after call to drawMultiseriesChart, series size " + dataBasedChartSeriesDTOs.size());
        if (false) {
            for (int i = 0; i < dataBasedChartSeriesDTOs.size(); i++) {
                DataBasedChartSeriesDTO seriesDTO = dataBasedChartSeriesDTOs.get(i);
                logger.info("Series " + i + " size " + seriesDTO.getDataList().size() + "  " + seriesDTO.getWbcSeriesInfo().getName());
            }
        }


    }

}

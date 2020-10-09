package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.summaryThresholds;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.events.*;
import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;
import com.wbc.supervisor.client.dashboard2.graphics.gwtchart.WbcGwtTimeseriesChart;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;
import com.wbc.supervisor.shared.utilities.GraphTimeCalculator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by JIM on 9/24/2014.
 */
public class SummaryPingResponses  extends VerticalPanel {

    WbcGwtTimeseriesChart pingChart = null;
    WbcNameWidget currentNetwork=null;
    long lastThresholdMinute = 0;
    int  hoursToGraph = 8;
    private static Logger logger = Logger.getLogger("SummaryPingResponses.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "SummaryPingResponses", "Network Change" );
    private VerticalPanel chartContainer  = new VerticalPanel();
    private ArrayList<WbcSeriesInfo> seriesList = new ArrayList<WbcSeriesInfo>();
    boolean debug = false;
    private MultiSeriesTimebasedChartDTO multiSeriesTimebasedChartDTO = null;


    public SummaryPingResponses() {
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        WbcSeriesInfo seriesInfo = new WbcSeriesInfo("ping", "line", 255, 0, 0, 0);
        seriesList.add( seriesInfo );
        pingChart = new WbcGwtTimeseriesChart( seriesList );

        addStyleName("summaryPingResponses");
        pingChart.setChartTitle("Worst Case Ping Responses");
        pingChart.setXAxisTitle("msec");
        pingChart.setYAxisTitle("Time");
        pingChart.setHeight(0);  // 0 = 100%
        pingChart.setHeight(com.wbc.supervisor.client.dashboard2.DashboardConstants.GRAPHS_HEIGHT);
        int browserWidth = Window.getClientWidth()-200;
        pingChart.setWidth( browserWidth/2  );
        pingChart.setBackground(com.wbc.supervisor.client.dashboard2.DashboardConstants.HTML_COLOR_INTRAVUE_MEDIUM);
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                int browserWidth = Window.getClientWidth()-200;
                pingChart.setWidth(browserWidth / 2);
//                fetchData(currentNetwork.getId(), lastThresholdMinute);
                if(multiSeriesTimebasedChartDTO!=null) {
                    drawPingSummaryChart(multiSeriesTimebasedChartDTO); //Instead of Calling the Whole RPC on every window Size changed, Just redrawing the chart with new size.
                }
            }
        });



        //////////////////////////////////////////Commented for GWT Charts/////////////
        // System.out.println("Before create chart");
        add(chartContainer);
        // System.out.println("After create chart");
        //////////////////////////////////////////Commented for GWT Charts/////////////
        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if ( newNetwork != currentNetwork ) {
                    if ( lastThresholdMinute == 0) {
                        logger.info("SummaryPingResponses got network change event but Last sample time is 0, so Not changing network until endTime > 0 " );
                    } else {
                        logger.log(Level.INFO, "SummaryPingResponses: onNetworkChange: NEW network " + newNetwork.getName1());
                        currentNetwork = event.getWbcNameWidget();
                        if (!networkChangeRpcInfo.isReady()) {
                            logger.info(networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed.");
                            // test, show the times
                            logger.info("TEST " + networkChangeRpcInfo.showLongTimes());
                        }
                        networkChangeRpcInfo.setEventReceivedTime(System.currentTimeMillis());
                        fetchData(currentNetwork.getId(), lastThresholdMinute);
                    }
                } else {
                    logger.log(Level.INFO, "SummaryPing got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });

        supervisor.eventBus.addHandler(TimerEvent.TYPE,
                new TimerEventHandler() {
                    public void onTimer(TimerEvent event) {
                        if ( event.getPurpose().equals("new_sample")) {
                            long newSample = event.getMillis();
                            if ( newSample > lastThresholdMinute) {
                                logger.info("SummaryPingResponses: timer: NEW sample " + newSample);
                                lastThresholdMinute = newSample;
                                if (currentNetwork != null) {
                                    fetchData(currentNetwork.getId(), newSample);
                                }
                            } else {
                                logger.info("SummaryPingResponses: timer: NOT NEW sample " + newSample);
                            }
                        }
                    }
         });

        supervisor.eventBus.addHandler(WbcGeneralEvent.TYPE,
                new WbcGeneralEventHandler() {
                    public void onValueChange(WbcGeneralEvent event) {
                        if (event.getName().equals("TimePeriodChange")) {
                            int hours = 8;
                            try {
                                hours = Integer.valueOf( event.getData());
                                hoursToGraph = hours;
                                if (currentNetwork != null) {
                                    fetchData(currentNetwork.getId(), lastThresholdMinute );
                               }
                            } catch (NumberFormatException e) {
                                logger.info("format exception getting TimePeriodChange ");
                            }
                        }
                    }
                });


    }

    private void fetchNewData( int networkId ) {
        //TODO get the time from the NewDataAvailable event, calculate the oldest sample from the 'time period' button change event in the ActionPanel

    }
    private void fetchData( int networkId, long endTime ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        try {
            String maxAverage = "max";
            if ( endTime == 0) {
                logger.log(Level.INFO, "SummaryPingResponses: not handling 0 as endTime");  // startup
                return;
            }
            //TODO : get setting for current time scale, assume 24 hours for now
//            final long startTime = endTime - ( hoursToGraph * 60 * 60 * 1000 );
//            int samplesPerPoint = 1;
//            long millisPerPoint = 60000;

            GraphTimeCalculator calculator = new GraphTimeCalculator( hoursToGraph, 240 );
            final long startTime = calculator.setEndtime(endTime );
            int samplesPerPoint = calculator.getMinuteSamplesPerGraphPoint();
            long millisPerPoint = calculator.getMillisPerPoint(samplesPerPoint);

            if ( hoursToGraph == 8) {
                // 8 hours , 480 min,
                // for 240 points
                samplesPerPoint = 2;
            } else if ( hoursToGraph == 24) {
                samplesPerPoint = 5;
            } else if ( hoursToGraph == 48) {
                samplesPerPoint = 10;
            }
            millisPerPoint = samplesPerPoint * 60000;

            int nwid = networkId;                      //   move this
            final long pointInterval = millisPerPoint;

            //fire event after 1min 30 sec
            /*
            *       current sample is in the event, divide by 60000 to get sample number
            *       oldestSample is number of hours in time period event * 60, subtracted from current sample
            *       numGraphPoints is calculated base on number of samples max / numGraphPoints
            *
             */
            networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
            dashboard2Service.getPingSummaryData(nwid, maxAverage, startTime, endTime, samplesPerPoint, new AsyncCallback<MultiSeriesTimebasedChartDTO>(){

                @Override
                public void onFailure(Throwable caught) {
                    logger.log(Level.INFO, "stats not fetched!");
                }

                @Override
                public void onSuccess(MultiSeriesTimebasedChartDTO  dto) {
                    if (debug) logger.info("SummaryPings: before drawPingSummaryChart");
                    multiSeriesTimebasedChartDTO = dto;
                    drawPingSummaryChart(dto );
                    if (debug) logger.info("SummaryPings: after  drawPingSummaryChart");
                }});
        } catch (Exception e) {
            logger.log(Level.INFO, "SummaryPings: fetchData Exception > " + e.getMessage(), e);
        }
    }

    private void drawPingSummaryChart(final MultiSeriesTimebasedChartDTO dto ) {
        networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
        networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
        chartContainer.clear();
        if (debug) logger.info("SummaryPings: before datatable create");
        try {
            pingChart.drawMultiseriesChart("Line", dto, chartContainer, false );
        } catch (Exception ex ) {
            logger.log( Level.SEVERE, "drawPingSummaryChart exception " + ex.getMessage() , ex);
        }
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
    }


}
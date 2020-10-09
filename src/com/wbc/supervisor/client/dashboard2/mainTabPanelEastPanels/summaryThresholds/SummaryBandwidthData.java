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
public class SummaryBandwidthData extends VerticalPanel {
    WbcGwtTimeseriesChart bwChart =null;
    WbcNameWidget currentNetwork=null;
    long lastThresholdMinute = 0;
    int  hoursToGraph = 8;
    private boolean loadingFirstTime = true;
    private static Logger logger = Logger.getLogger("SummaryBandwidthData.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "SummaryBandwidthData", "Network Change" );
    private VerticalPanel chartContainer = new VerticalPanel();
    private boolean showFiring = true;
    private MultiSeriesTimebasedChartDTO multiSeriesTimebasedChartDTO = null;


    public SummaryBandwidthData() {
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        WbcSeriesInfo seriesInfoR = new WbcSeriesInfo("RECV", "line", 255, 0, 0, 0);
        WbcSeriesInfo seriesInfoT = new WbcSeriesInfo("XMIT", "line", 0,   0, 255, 1);
        final ArrayList<WbcSeriesInfo> seriesList = new ArrayList<WbcSeriesInfo>();
        seriesList.add( seriesInfoR );
        seriesList.add( seriesInfoT );
        bwChart = new WbcGwtTimeseriesChart(seriesList);
        addStyleName("summaryBandwidthData");
        bwChart.setChartTitle("Worst Case Time Responses");
        bwChart.setXAxisTitle("msec");
        bwChart.setYAxisTitle("Time");
        bwChart.setHeight(com.wbc.supervisor.client.dashboard2.DashboardConstants.GRAPHS_HEIGHT);
        bwChart.setBackground(com.wbc.supervisor.client.dashboard2.DashboardConstants.HTML_COLOR_INTRAVUE_MEDIUM);
        int browserWidth = Window.getClientWidth()-200;
        bwChart.setWidth(browserWidth / 2);
        add(chartContainer);

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                int browserWidth = Window.getClientWidth()-200;
                bwChart.setWidth(browserWidth / 2);
                if(multiSeriesTimebasedChartDTO!=null) {
                    drawBandwidthChart(multiSeriesTimebasedChartDTO);//Instead of Calling the Whole RPC on every window Size changed, Just redrawing the chart with new size.
                }
//                fetchData(currentNetwork.getId(), lastThresholdMinute );
//                add(chartContainer);

            }
        });

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if ( newNetwork != currentNetwork ) {
                    currentNetwork = event.getWbcNameWidget();// moved here from line: 64 below ..
                    if ( lastThresholdMinute == 0) {
                        logger.info("SummaryBandwidthData got network change event but Last sample time is 0, so Not changing network until endTime > 0 " );
                    } else {
                        supervisor.logger.log(Level.INFO, "SummaryBandwidthData: onNetworkChange: NEW network " + newNetwork.getName1());
//                        currentNetwork = event.getWbcNameWidget();
                        if (!networkChangeRpcInfo.isReady()) {
                            logger.info(networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed.");
                            // test, show the times
                            logger.info("TEST " + networkChangeRpcInfo.showLongTimes());
                        }
                        networkChangeRpcInfo.setEventReceivedTime(System.currentTimeMillis());
                        fetchData(currentNetwork.getId(), lastThresholdMinute);
                    }
                } else {
                    supervisor.logger.log(Level.INFO, "SummaryBandwidthData got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });

        supervisor.eventBus.addHandler(TimerEvent.TYPE,
                new TimerEventHandler() {
                    public void onTimer(TimerEvent event) {
                        if (event.getPurpose().equals("new_sample")) {
                            long newSample = event.getMillis();
                            if (newSample > lastThresholdMinute) {
                                // System.out.println("SummaryBandwidthData: timer: NEW sample " + newSample);
                                lastThresholdMinute = newSample;
                                if (currentNetwork != null) {
                                    fetchData(currentNetwork.getId(), newSample);
                                }
                            } else {
                                supervisor.logger.log(Level.INFO, "SummaryBandwidthData: timer: NOT NEW sample " + newSample);
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
                                supervisor.logger.log(Level.INFO, "format exception getting TimePeriodChange ");
                            }
                        }
                    }
                });

    }

    private void fetchData( int networkId, long endTime  ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        try {
            if ( endTime == 0) {
                supervisor.logger.log(Level.INFO, "SummaryBandwidthData: not handling 0 as endTime time"); // startup
                return;
            }

            String maxAverage = "max";
            //TODO : get setting for current time scale, assume 24 hours for now
            GraphTimeCalculator calculator = new GraphTimeCalculator( hoursToGraph, 240 );
            final long startTime = calculator.setEndtime(endTime );
            int samplesPerPoint = calculator.getMinuteSamplesPerGraphPoint();
            long millisPerPoint = calculator.getMillisPerPoint(samplesPerPoint);

//            long millisPerPoint = 60000;
//            long startTime = endTime - ( hoursToGraph * 60 * 60 * 1000 );
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
            dashboard2Service.getBandwidthSummaryData(nwid, "both", maxAverage, startTime, endTime, samplesPerPoint, new AsyncCallback<MultiSeriesTimebasedChartDTO>() {

                @Override
                public void onFailure(Throwable caught) {

                    Window.alert("stats not fetched!" + caught.getMessage());
                    supervisor.logger.log(Level.INFO, "stats not fetched!");
                }

                @Override
                public void onSuccess(MultiSeriesTimebasedChartDTO dto) {
                    multiSeriesTimebasedChartDTO = dto;
                    drawBandwidthChart(dto);

                    if (loadingFirstTime) { // this is the last rpc called on mainTab, so here we fire MainPanelLoadEvent, But code comes here also when network change event fired.So whenever AFTER loading application time code comes here we dont want to fire MainPanelLoad event
                        if (showFiring) logger.log(Level.INFO,"SummaryBandwidthData: textBox clickHandler FIRING MainPanelLoadedEvent.");
                        supervisor.eventBus.fireEvent(new MainPanelLoadedEvent());
                        if (showFiring) logger.log(Level.INFO,"SummaryBandwidthData: textBox clickHandler FIRING network changed event after loadingFirstTime.");
                        supervisor.eventBus.fireEvent(new SelectedNetworkChangeEvent(currentNetwork));
                        loadingFirstTime = false;
                    }
                    supervisor.eventBus.fireEvent(new AllMainPanelsLoadedEvent());
                }});

        } catch (Exception e) {
            logger.log(Level.INFO, "SummaryBandwidthData: fetchData Exception > " + e.getMessage(), e );
        }
    }

    private void drawBandwidthChart( final MultiSeriesTimebasedChartDTO dto ) {
        networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
        networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
        chartContainer.clear();
//        boolean runInRunFixed = true;
//        if (runInRunFixed) {
            bwChart.drawMultiseriesChart("Line", dto, chartContainer, false );
//        } else {
//            Runnable onLoadCallback = new Runnable() {
//                public void run() {
//                    // NOTE SAME AS COMMENTED OUT CODE ABOVE
//                    bwChart.drawMultiseriesChart("Line", dto, chartContainer, false );
//                }
//            };
//            VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
//
//        }
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
    }


}

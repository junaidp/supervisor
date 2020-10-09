package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.TimeBasedPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.DashboardConstants;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.*;
import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;
import com.wbc.supervisor.client.dashboard2.graphics.gwtchart.WbcGwtTimeseriesChart;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.StatsConstants;
import com.wbc.supervisor.shared.utilities.GraphTimeCalculator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 9/16/14.
 */
public class TimebasedStatsBarchartPanel extends VerticalPanel {


    WbcGwtTimeseriesChart statsBarChart = null;
    WbcNameWidget currentNetwork=null;
    long lastThresholdMinute = 0;
    protected Logger logger = Logger.getLogger( "TimebasedStatsBarchartPanel" );
    ArrayList<WbcSeriesInfo> seriesList;
    StatsBarChartControlPanel controlPanel;
    int  hoursToGraph = 8;
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "TimeBasedStatsBarChart", "Network Change" );
    private VerticalPanel chartContainer  = new VerticalPanel();
    private MultiSeriesTimebasedChartDTO[] multiSeriesTimebasedChartDTO = null;
    private long pointInterval = 0;


    public TimebasedStatsBarchartPanel( StatsBarChartControlPanel controlPanel ){
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        this.controlPanel = controlPanel;
        seriesList = new ArrayList<WbcSeriesInfo>();
        WbcSeriesInfo seriesInfo = new WbcSeriesInfo("Ping Over", "Vbar", StatsConstants.pingOverColor, StatsConstants.SERIES_INDEX_PT );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("Ping Fail", "VBar",  StatsConstants.pingFailColor, StatsConstants.SERIES_INDEX_PF );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("Bandwidth", "VBar",  StatsConstants.bwOverColor, StatsConstants.SERIES_INDEX_BW );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("IP", "VBar",  StatsConstants.ipChangeColor, StatsConstants.SERIES_INDEX_IP );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("MAC", "VBar",  StatsConstants.macChangeColor, StatsConstants.SERIES_INDEX_MAC );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("Move", "VBar",  StatsConstants.moveColor, StatsConstants.SERIES_INDEX_MOVE );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("Speed", "VBar",  StatsConstants.linkSpeedColor, StatsConstants.SERIES_INDEX_LINK );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("Disconnect", "VBar",  StatsConstants.disconnectColor, StatsConstants.SERIES_INDEX_DISC );
        seriesList.add( seriesInfo );
        seriesInfo = new WbcSeriesInfo("Enet/IP", "VBar",  StatsConstants.enetipColor, StatsConstants.SERIES_INDEX_ENET );
        seriesList.add( seriesInfo );

        statsBarChart = new WbcGwtTimeseriesChart( seriesList );

        addStyleName("statBarChart");
        statsBarChart.setChartTitle("Incidents Over Time");
        statsBarChart.setXAxisTitle("quantity");
        statsBarChart.setYAxisTitle("Time");
        statsBarChart.setHeight(com.wbc.supervisor.client.dashboard2.DashboardConstants.GRAPHS_HEIGHT+55);  // 0 = 100%
        int browserWidth = Window.getClientWidth()-420;
        statsBarChart.setWidth(browserWidth );
        setStyleName("kpiDetailsSouth");
        statsBarChart.setBackground(com.wbc.supervisor.client.dashboard2.DashboardConstants.HTML_COLOR_INTRAVUE_MEDIUM);

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
//                fetchData(currentNetwork.getId(), lastThresholdMinute);
                int browserWidth = Window.getClientWidth()-420;
                statsBarChart.setWidth(browserWidth );
                if(multiSeriesTimebasedChartDTO!=null){
                    drawChart(multiSeriesTimebasedChartDTO, pointInterval);
                }

            }
        });

        /*
        TODO implement for gwt charts
        statsBarChart.setDateTimeFormatXAxis(
                new DateTimeLabelFormats()
                        .setHour("%I %p")
                        .setMinute("%I:%M %p")
        );
         */
        add(chartContainer);
        // ------------------ HANDLERS -------------------------------------------------
        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if ( newNetwork != currentNetwork ) {
                    if ( lastThresholdMinute == 0) {
                        logger.info("TimebasedStatsBarchartPanel got network change event but Last sample time is 0, so Not changing network until endTime > 0 " );
                    } else {
                        logger.info("TimebasedStatsBarchartPanel: onNetworkChange: NEW network " + newNetwork.getName1());
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
                    logger.info("TimebasedStatsBarchartPanel got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });

        supervisor.eventBus.addHandler(TimerEvent.TYPE,
                new TimerEventHandler() {
                    public void onTimer(TimerEvent event) {
                        if (event.getPurpose().equals("new_sample")) {
                            long newSample = event.getMillis();
                            if (newSample > lastThresholdMinute) {
                                logger.info("TimebasedStatsBarchartPanel: timer: NEW sample " + newSample);
                                lastThresholdMinute = newSample;
                                if (currentNetwork != null ) {
                                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                                }
                            } else {
                                logger.info("TimebasedStatsBarchartPanel: timer: NOT NEW sample " + newSample);
                            }
                        }
                    }
                });

        supervisor.eventBus.addHandler(WbcGeneralEvent.TYPE,
                new WbcGeneralEventHandler() {
                    public void onValueChange(WbcGeneralEvent event) {
                        Window.alert("test alert from  TimebasedStatsBarchartPanel class : " + event.getName() + " to " + event.getData());

                        if (event.getName().equals("TimePeriodChange")) {
                            int hours = 8;
                            try {
                                hours = Integer.valueOf(event.getData());
                                hoursToGraph = hours;
                                if (currentNetwork != null ) {
                                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                                }
                            } catch (NumberFormatException e) {
                                logger.info("format exception getting TimePeriodChange ");
                            }
                        }
                    }
                });
        // ------------------ EMD HANDLERS -------------------------------------------------


    }

    private void fetchData( int networkId, long endTime ) {
        supervisorServiceAsync dashboard2Service = GWT.create(com.wbc.supervisor.client.supervisorService.class);

        try {
            if (endTime == 0) {
                logger.info("TimebasedStatsBarchartPanel: not handling 0 as endTime");  // startup
                return;
            }

            int nwid = networkId;
//            long startTime = endTime - (hoursToGraph * 60 * 60 * 1000);
//            int samplesPerPoint = 30;
//            long millisPerPoint = 60000;
            GraphTimeCalculator calculator = new GraphTimeCalculator( hoursToGraph, 240 );
            final long startTime = calculator.setEndtime(endTime );
            int samplesPerPoint = calculator.getMinuteSamplesPerGraphPoint();
            long millisPerPoint = calculator.getMillisPerPoint(samplesPerPoint);
            if (hoursToGraph == 8) {
                // 8 hours , 480 min,
                // for 240 points
                samplesPerPoint = 30;  // half hour
            } else if (hoursToGraph == 24) {
                samplesPerPoint = 90;
            } else if (hoursToGraph == 48) {
                samplesPerPoint = 180;
            }
            millisPerPoint = samplesPerPoint * 60000;

            pointInterval = millisPerPoint;

            networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
            // false means not critical only
            dashboard2Service.getStatsBarGraphData(nwid,false, startTime, endTime, samplesPerPoint, new AsyncCallback<MultiSeriesTimebasedChartDTO[]>() {

                @Override
                public void onFailure(Throwable caught) {
                    logger.info("TimebasedStatsBarchartPanel  stats not fetched!");
                }

                @Override
                public void onSuccess(MultiSeriesTimebasedChartDTO[] dto) {
                    networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                    boolean useCulling = false;
                    if (useCulling) {
//                        cullSeries(dto[0]);
                        controlPanel.updateCountsForTypes(dto[1]);
                    } else {
                        multiSeriesTimebasedChartDTO = dto;
                        drawChart(dto, pointInterval);
                    }
                    networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
                    logger.log(Level.INFO, networkChangeRpcInfo.getData());
                    networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY

                }
            });
        } catch (Exception e) {
//            // TODO ADD LOGGING FOR EXCEPTIONS WITH NEW LOGGER
//            e.printStackTrace();
        }
    }

            /**
                 *  This is not used, but is the start of only updating the last, new data.  Not the whole chart.  To be done when/if responsiveness is issue
                 * @param dto
                 */
//                private void cullSeries( MultiSeriesTimebasedChartDTO dto ) {
//                    networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
//                    ArrayList<Series> seriesArrayList = new ArrayList<Series>();
//                    LinkedHashMap<String, ArrayList<Number>> dtoMap = dto.getData();
//                    try {
//                        for (WbcSeriesInfo wbcSeriesInfo : seriesList) {
//                            Series series = statsBarChart.getBaseChart().createSeries().setName(wbcSeriesInfo.getName());
//                            series.setType(Series.Type.COLUMN);
//                            Color color = new Color(wbcSeriesInfo.getR(), wbcSeriesInfo.getG(), wbcSeriesInfo.getB());
//                            series.setPlotOptions( new ColumnPlotOptions()
//                                    .setColor(color)
//                                    .setShadow(false)
//                                    .setBorderColor(color)
//                            );
//                            // ...setLineColor(color));
//                            int index = wbcSeriesInfo.getArrayIndex();
//                            boolean hasData = false;
//                            for (String sDate : dtoMap.keySet()) {
//                                ArrayList<Number> xvalues = dtoMap.get(sDate);
//                                Long lDate = Long.valueOf(sDate);
//                                Number xval = xvalues.get(index);
//                                // logger.log(Level.INFO, "sDate " + sDate + " index " + index + "  x= " + xval);
////                                System.out.println("sDate " + sDate + " index " + index + "  x= " + xval);
//                                series.addPoint(lDate, xval);
//                                if ((Integer) xval > 0) hasData = true;
//                            }
//                            boolean test = true;
//                            if (test) {
//                                if (hasData)  seriesArrayList.add(series);
//                            } else {
//                                if (!hasData) series.hide();
//                                seriesArrayList.add(series);
//                            }
//                        }
//                    } catch (Exception ex) {
//                        System.out.println( "Exception in cullSeries: " + ex.getClass().getName() + "  " + ex.getMessage()) ;
//                    }
//                    Series[] finalSeries = seriesArrayList.toArray(new Series[seriesArrayList.size()]);
////                    statsBarChart.drawMultiseriesChart( finalSeries );
//                    networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
//                    logger.log(Level.INFO, networkChangeRpcInfo.getData());
//                    networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
//                }
//
//
//            });
//        } catch (Exception e) {
//            // TODO ADD LOGGING FOR EXCEPTIONS WITH NEW LOGGER
//            e.printStackTrace();
//        }
//    }

    private void drawChart( final MultiSeriesTimebasedChartDTO[] dto, long pointInterval) {
        logger.info("TimebasedStatsBarchartPanel: onSuccess  ");
        chartContainer.clear();
        statsBarChart.drawMultiseriesChart("bar", dto[0], chartContainer, false );  // set true for debugging
        controlPanel.updateCountsForTypes(dto[1]);
    }


}

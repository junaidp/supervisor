package com.wbc.supervisor.client.dashboard2.pingDetails;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.DashboardConstants;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.*;
import com.wbc.supervisor.client.dashboard2.graphics.gwtchart.WbcGwtTimeseriesChart;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;
import com.wbc.supervisor.shared.utilities.GraphTimeCalculator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 1/21/2015.
 */
public class PingDetailsEastPanel extends VerticalPanel {

    WbcGwtTimeseriesChart chart =null;
    WbcNameWidget currentNetwork=null;
    long lastThresholdMinute = 0;
    int  hoursToGraph = 8;
    private boolean loadingFirstTime = true;
    private static Logger logger = Logger.getLogger("PingDetailsEastPanel.class");
    int maxDevicesToGraph = 2;
    private String   localDateFormat="";
    private String selectType;
    VerticalPanel vpnlChart = new VerticalPanel();
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "PingDetailsEastPanel", "Network Change" );
    private PingGraphingControlPanel numberOfDevicesControl;
    private ArrayList<DataBasedChartSeriesDTO> dataBasedChartSeriesDTO;

    public PingDetailsEastPanel(){
        if (!networkChangeRpcInfo.isReady())logger.info( "PingDetailsEastPanel At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        chart = new WbcGwtTimeseriesChart();
        numberOfDevicesControl = new PingGraphingControlPanel();
        setSpacing(3);
        add(numberOfDevicesControl);
        selectType = "savg";  // types will be savg, mavg, max, stddev, .........

        // The following commneted out by JWM June 7, 2015
        // ping details should not be getting last sample on a 1 second basis
        // it should handle the new sample available event
        // added back the handler for new samples
        /*
        Timer minuteTimer = new Timer() {
            public void run() {
                getLastSampleno(); // Added this to get the lastthreshold value ,as I removed the new_sample/timer thing , which were threshold value

            }
        };
        minuteTimer.schedule(DashboardConstants.TIMER_FETCHSAMEPLNO_AND_SCANNERSTATE);
        */

        setHandlers(numberOfDevicesControl);
        chart.setChartTitle(maxDevicesToGraph + " devices with highest ping response averages");
        chart.setYAxisTitle("Time (msec)");
        updateChartSize();
        chart.setBackground(com.wbc.supervisor.client.dashboard2.DashboardConstants.HTML_COLOR_INTRAVUE_MEDIUM);
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                if(dataBasedChartSeriesDTO!=null) {
                    updateChartSize();
                    drawChart(dataBasedChartSeriesDTO);

                }
            }
        });
        /* TODO
        chart.setDateTimeFormatXAxis(
                new DateTimeLabelFormats()
                        .setHour("%I %p")
                        .setMinute("%I:%M %p")
        );
        */
        add(vpnlChart);
        setStyleName("pingDetailsEast");
      }

    private void updateChartSize() {
        try {
            chart.setHeight(Window.getClientHeight() - 217);
            chart.setWidth(Window.getClientWidth() - 415);
        }catch(Exception ex){
            // will come here if the browser size becomes smaller than the accepted size , or in any exception.and the charts will remain
            // the same size , size will not change.
        }
    }


    private void setHandlers(final PingGraphingControlPanel numberOfDevicesControl) {

        supervisor.eventBus.addHandler(WbcGeneralEvent.TYPE,
                new WbcGeneralEventHandler() {
                    public void onValueChange(WbcGeneralEvent event) {

                        if (event.getName().equals("TimePeriodChange")) {
                            int hours = 8;
                            try {
                                hours = Integer.valueOf( event.getData());
                                hoursToGraph = hours;
                                if (currentNetwork != null) {
                                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                                }
                            } catch (NumberFormatException e) {
                                logger.info("PingDetailsEastPanel format exception getting TimePeriodChange ");
                            }
                        }
                    }
                });

        numberOfDevicesControl.getUpdateNow().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (currentNetwork != null) {
                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                }
            }
        });

        numberOfDevicesControl.getListMaxNumbers().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                maxDevicesToGraph= Integer.parseInt(numberOfDevicesControl.getListMaxNumbers().getValue(numberOfDevicesControl.getListMaxNumbers().getSelectedIndex()));
                if(currentNetwork!=null) {
                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                    chart.setChartTitle(maxDevicesToGraph + " devices with highest ping response averages");
                }
            }
        });

        numberOfDevicesControl.getListSelectType().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                  selectType =  numberOfDevicesControl.getListSelectType().getValue(numberOfDevicesControl.getListSelectType().getSelectedIndex());
                if(currentNetwork!=null) {
                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                }
            }
        });


        supervisor.eventBus.addHandler(TimerEvent.TYPE,
                new TimerEventHandler() {
                    public void onTimer(TimerEvent event) {
                        if ( event.getPurpose().equals("new_sample")) {
                            long newSample = event.getMillis();
                            if ( newSample > lastThresholdMinute) {
                                lastThresholdMinute = newSample;
                            } else {
                                logger.info("PingDetailsEastPanel: timer: NOT NEW sample " + newSample);
                            }
                        }
                    }
                });

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if (newNetwork != currentNetwork ) {
                    logger.info("PingDetailsWestPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                    currentNetwork = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info(networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed.");
                        // test, show the times
                        logger.info("TEST " + networkChangeRpcInfo.showLongTimes());
                    }
                    networkChangeRpcInfo.setEventReceivedTime(System.currentTimeMillis());
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


    }

    private void fetchData( int networkId, long endTime  ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        logger.info("PingDetailsEastPanel: TEMP DEBUG, DELETE WHEN NOT NEEDED. fetchData start. (logger)");
        supervisor.logger.info("PingDetailsEastPanel: TEMP DEBUG, DELETE WHEN NOT NEEDED. fetchData start. (dashboard2.logger)");
        try {
            if ( endTime == 0) {
                logger.info("PingDetailsEastPanel: not handling 0 as endTime time"); // startup
                return;
            }
//            String selectType = "savg";  // types will be savg, mavg, max, stddev, .........

//            GraphTimeCalculator calculator = new GraphTimeCalculator( hoursToGraph, 240 );
//            final long startTime = calculator.setEndtime(endTime );
//            int samplesPerPoint = calculator.getMinuteSamplesPerGraphPoint();

            GraphTimeCalculator calculator = new GraphTimeCalculator( hoursToGraph, 240 );
            final long startTime = calculator.setEndtime(endTime );
            int samplesPerPoint = calculator.getMinuteSamplesPerGraphPoint();
            long millisPerPoint = calculator.getMillisPerPoint(samplesPerPoint);
            //fire event after 1min 30 sec
            /*
            *       current sample is in the event, divide by 60000 to get sample number
            *       oldestSample is number of hours in time period event * 60, subtracted from current sample
            *       numGraphPoints is calculated base on number of samples max / numGraphPoints
            *
             */

            //TODO get the number of devices from the UI
            numberOfDevicesControl.getLoading().setVisible(true);
            logger.info( "PingDetailsEastPanel: Before call to RPC, maxDevicesToGraph " + maxDevicesToGraph );

            networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());

            dashboard2Service.getDetailedPingData( networkId, selectType, maxDevicesToGraph, startTime, endTime, samplesPerPoint, new AsyncCallback<ArrayList<DataBasedChartSeriesDTO>>(){

                @Override
                public void onFailure(Throwable caught) {
                    logger.info("stats not fetched!");
                }

                @Override
                public void onSuccess(ArrayList<DataBasedChartSeriesDTO> dto) {
                    if(localDateFormat.equals("")){
                        getLocalDetails(dto);
                    }else {
                        dataBasedChartSeriesDTO  = dto;
                        drawChart(dto);
                    }
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void drawChart(final ArrayList<DataBasedChartSeriesDTO> dto) {
        networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
        networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
        vpnlChart.clear();
        chart.drawDatabasedChart("LINE", dto, vpnlChart, false );  // true = show debug info in drawing chart
        logger.info("OnSuccess after call to drawMultiseriesChart, series size " + dto.size());
        if (false) {
            for (int i = 0; i < dto.size(); i++) {
                DataBasedChartSeriesDTO seriesDTO = dto.get(i);
                logger.info("Series " + i + " size " + seriesDTO.getDataList().size() + "  " + seriesDTO.getWbcSeriesInfo().getName());
            }
        }
        numberOfDevicesControl.getLblLoading().setVisible(false);
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        numberOfDevicesControl.getTimeOfLastUpdate().setText(getEndTime());
//        startTimer();
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY

    }

    public void fetchDataIfDataNotFetchedForLast2Minutes(){
                Date lastTimeDataFetched = new Date(lastThresholdMinute);
                Date currentTime = new Date();
                long difference = currentTime.getTime()- lastTimeDataFetched.getTime();
                int minutes = (int) (((difference / 1000) / 60) % 60);
                if(minutes>=2){
                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                }
    }


    private String getEndTime(){
        Date date = new Date(lastThresholdMinute);
        DateTimeFormat fmt = DateTimeFormat.getFormat(localDateFormat);
        return  fmt.format(date);

    }

    // NOTE: THIS IS NOW NOT CALLED< CODE COMMENTED OUT IN CTOR,
    /*
    private void getLastSampleno() {
        dashboard2ServiceAsync dashboard2Service = GWT
                .create(com.wbc.dashboard2.client.dashboard2Service.class);

        dashboard2Service.getLastSampleno(new AsyncCallback<Long>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.info("fail: getLastSampleNo!");
                Window.alert("fail: PingDetailPanel  getLastSampleNo" + caught.getMessage() + " cause " + caught.getCause());
            }

            @Override
            public void onSuccess( Long last ) {

                lastThresholdMinute = last;
                checkNetworkChange();

            }
        });
    }
    */

    public void checkNetworkChange(){
        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if (newNetwork != currentNetwork) {
                    logger.info("PingDetailsEastPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                    currentNetwork = event.getWbcNameWidget();
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info(networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed.");
                        // test, show the times
                        logger.info("TEST " + networkChangeRpcInfo.showLongTimes());
                    }
                    networkChangeRpcInfo.setEventReceivedTime(System.currentTimeMillis());
                    fetchData(currentNetwork.getId(), lastThresholdMinute);
                } else {
                    logger.info("PingDetailsEastPanel got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });
    }

    public void getLocalDetails(final ArrayList<DataBasedChartSeriesDTO> dto){
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);



        dashboard2Service.getLocalDetails(new AsyncCallback<HashMap>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.ALL, "onModuleLoad: getLocalDetails failed" );
            }

            @Override
            public void onSuccess(HashMap localDetails) {
               localDateFormat = localDetails.get("localDf").toString();
                dataBasedChartSeriesDTO  = dto;
               drawChart(dto);
            }
        });

    }

}

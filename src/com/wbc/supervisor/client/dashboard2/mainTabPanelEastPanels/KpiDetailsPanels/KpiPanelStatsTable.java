package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.KpiDetailsPanels;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.LoadingPopup;
import com.wbc.supervisor.client.dashboard2.events.*;
import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.dashboard2dto.KpiStatsRow;
import com.wbc.supervisor.shared.utilities.GraphTimeCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 3/30/2015.
 */

public class KpiPanelStatsTable extends VerticalPanel {

    CellTable<KpiStatsRow> table = new CellTable<KpiStatsRow>();;
    Column<KpiStatsRow, String> columnIpAddress;
    Column<KpiStatsRow, String> columnNameToDisplay;
    Column<KpiStatsRow, String> columnIsSwitch;
    Column<KpiStatsRow, String> columnClevel;
    Column<KpiStatsRow, String> columnUpTime;

    Column<KpiStatsRow, String> columnPingOver;
    Column<KpiStatsRow, String> columnBw;
    Column<KpiStatsRow, String> columnIpChange;
    Column<KpiStatsRow, String> columnMacChange;
    Column<KpiStatsRow, String> columnMove;
    Column<KpiStatsRow, String> columnLink;
    Column<KpiStatsRow, String> columnDisc;

    // Column<KpiStatsRow, String> columnNewd;   // not implemented
    // Column<KpiStatsRow, String> columnPingFail;   // not implemented
    // Column<KpiStatsRow, String> columnEnet;   // not implemented
    Column<KpiStatsRow, String> columnTotal;
    WbcNameWidget currentNetwork=null;
    int currentNetworkId;
    private ArrayList<WbcSeriesInfo> wbcSeriesInfo;
    long endTime = 0;
    int hoursToGraph =8;
    private final int USE_UI_HOURS = 1;
    private final int USE_YESTERDAY = 2;
    int kpiTimeperiodType = USE_UI_HOURS;
    private static Logger logger = Logger.getLogger("KpiPanelStatsTable.class");
    boolean debug = true;


    public KpiPanelStatsTable() {
        add(table);
        setSpacing(3);
        addColumns();

        // new  handler for hours to graph

        supervisor.eventBus.addHandler(TimerEvent.TYPE,
                new TimerEventHandler() {
                    public void onTimer(TimerEvent event) {
                        if (event.getPurpose().equals("new_sample")) {
                            long newSample = event.getMillis();
                            if (newSample > endTime) {
                                supervisor.logger.log(Level.INFO, "KpiPanelStatsTable: timer: NEW sample " + newSample);
                                endTime = newSample;
                            } else {
                                supervisor.logger.log(Level.INFO, "KpiPanelStatsTable: timer: NOT NEW sample " + newSample);
                            }
                        }
                    }
                });

        supervisor.eventBus.addHandler(WbcGeneralEvent.TYPE,
                new WbcGeneralEventHandler() {
                    public void onValueChange(WbcGeneralEvent event) {
                        if (event.getName().equals("TimePeriodChange")) {
                            hoursToGraph = Integer.parseInt(event.getData());
                            if (currentNetwork == null ) {
                                logger.info("KpiPanelStatsTable: TimePeriodChange but network not set yet.");
                            } else {
                                calculateNewDataAndFetchStats();
                            }
                        } else if (event.getName().equals("KpiPanelTimePeriodChange")) {
                            String periodType = event.getData();
                            if (periodType.toLowerCase().equals("yesterday")) {
                                kpiTimeperiodType = USE_YESTERDAY ;
                            } else {
                                kpiTimeperiodType = USE_UI_HOURS ;
                            }
                        }
                    }
                });


        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if ( newNetwork != currentNetwork ) {
                    if ( endTime == 0) {
                        logger.info("KpiPanelStatsTable got network change event but Last sample time is 0, so Not changing network until endTime > 0 " );
                    } else {
                        if (debug) logger.info("KpiPanelStatsTable: onNetworkChange: NEW network " + newNetwork.getName1());
                        currentNetwork = event.getWbcNameWidget();
                        calculateNewDataAndFetchStats();
                    }
                } else {
                    if (endTime == 0) {
                        logger.info("KpiPanelStatsTable got network change event but network not changed. AND !!!!! Last sample time is 0 ");
                    }
                }
            }
        });
    }


    private void calculateNewDataAndFetchStats() {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        if ( currentNetwork == null) {
            supervisor.logger.log(Level.INFO, "KpiPanelStatsTable: currentNetwork is null, not fetching stats " );
            return;
        }
        currentNetworkId = currentNetwork.getId();
        long startTime = 0;
        long kpiEndTime = endTime;
        if ( kpiTimeperiodType == USE_UI_HOURS ) {
            GraphTimeCalculator calculator = new GraphTimeCalculator(hoursToGraph, 240);
            boolean debugCalculator = false;
            startTime = calculator.setEndtime(endTime, debugCalculator, "calculateNewDataAndFetchStats xx1 ", logger);
        } else {
            // YESTERDAY
            GraphTimeCalculator calculator = new GraphTimeCalculator(24, 240);
            kpiEndTime = calculator.getEndtimeForYesterday( endTime );
            startTime = kpiEndTime - ( 24 * 60 * 60 * 1000L );
        }

        final LoadingPopup loading = new LoadingPopup();
        loading.display();
        if (debug) logger.info( "KpiPanelStatsTable before getKpiStatsForPeriod   startTime " + startTime + " endTime  " + endTime);
        dashboard2Service.getKpiStatsForPeriod(currentNetworkId, startTime, kpiEndTime, new AsyncCallback<ArrayList<KpiStatsRow>>() {

            @Override
            public void onFailure(Throwable caught) {
                loading.remove();
            }

            @Override
            public void onSuccess(ArrayList<KpiStatsRow> statsRows) {
                if (debug) logger.info( "KpiPanelStatsTable success getKpiStatsForPeriod   statsRows size " + statsRows.size() );
                // int cLevelSum=0;  no sum for Clevel
                int pingOverSum=0;
                int bwSum=0;
                int ipChangeSum=0;
                int macChangeSum=0;
                int moveSum=0;
                int linkSum=0;
                int discSum=0;
                /*
                    the following are not implemented
                int newdSum=0;
                int enetSum=0;
                int pingFailSum = 0;
                */
                int totalSum=0;
                // int upTimeSum=0;


                for(int i=0; i< statsRows.size(); i++){
                    KpiStatsRow statsRow = statsRows.get(i);
                    if (debug) logger.info( "KpiPanelStatsTable got statsRow " + statsRow.toString() );
                    pingOverSum = pingOverSum + statsRow.getPt();
                    bwSum = bwSum+ statsRow.getBw();
                    ipChangeSum = ipChangeSum + statsRow.getIpc();
                    macChangeSum = macChangeSum + statsRow.getMacc();
                    moveSum = moveSum+ statsRow.getMove();
                    linkSum = linkSum+ statsRow.getLink();
                    discSum = discSum+ statsRow.getDisc();
                    totalSum= totalSum+statsRow.getTotal();
                    // upTimeSum = fetchUpTimeLastRowValue(upTimeSum, statsRow.getUpTime());
                }
                KpiStatsRow kpiStatsRowSum = new KpiStatsRow();
                kpiStatsRowSum.setPt(pingOverSum);
                kpiStatsRowSum.setBw(bwSum);
                kpiStatsRowSum.setIpc(ipChangeSum);
                kpiStatsRowSum.setMacc(macChangeSum);
                kpiStatsRowSum.setMove(moveSum);
                kpiStatsRowSum.setLink(linkSum);
                kpiStatsRowSum.setDisc(discSum);
                kpiStatsRowSum.setTotal(totalSum);
                kpiStatsRowSum.setNameToDisplay("TOTALs");


                statsRows.add(kpiStatsRowSum);
                displayCellTable(statsRows);
                loading.remove();
            }
        });
    }

    private int fetchUpTimeLastRowValue(int upTimeSum, int upTime) {
        // FOR SPECIAL UPTIME CALCULATION .. We can do whatever calculation we want for the last row of uptime here ..
        // At the moment its just a Sum.
        return upTimeSum+upTime;
    }

    private void addColumns(){

        columnBw = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getBw()+"";
            }
        };

        columnClevel = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {
                if(object.getNameToDisplay().equals("TOTALs")){
                    return "";
                }
                if(object.getClevel()==2) {
                    return "Not Always";
                }else if (object.getClevel() == 3){
                    return "Always On";
                }else {
                    return object.getClevel() + "";
                }
            }
        };

        columnPingOver = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getPt()+"";
            }
        };

        columnIpChange = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getIpc()+"";
            }
        };

        columnMacChange = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getMacc()+"";
            }
        };
        columnDisc = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getDisc()+"";
            }
        };

        columnIpAddress = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getIpaddress()+"";
            }
        };

        columnLink = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getLink()+"";
            }
        };

        columnNameToDisplay = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getNameToDisplay()+"";
            }
        };

        columnMove = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getMove()+"";
            }
        };

        /*
        columnNewd = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {

                return object.getNewd()+"";
            }
        };
        */

        columnIsSwitch = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {
                String response = "N";
                if ( object.isSwitch()) {
                    response = "Y";
                }
                if(object.getNameToDisplay().equals("TOTALs")){
                    response="";
                }
                return response;
            }
        };

        columnTotal = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {
                return object.getTotal()+"";
            }
        };

        table.setRowStyles(new RowStyles<KpiStatsRow>() {
            @Override
            public String getStyleNames(KpiStatsRow row, int rowIndex) {
                if(row.getNameToDisplay().equals("TOTALs")){
                    return "boldText";
                }
                return "normalText";
            }
           });

        columnUpTime = new Column<KpiStatsRow, String>(new ClickableTextCell()) {
            @Override
            public String getValue(KpiStatsRow object) {
                if(object.getClevel()!=3 && !object.getNameToDisplay().equals("TOTALs")){// empty check to be sure this is Not the Last/Sum Row.
                    return 0+"";
                }else {
                    if(object.getNameToDisplay().equals("TOTALs")){
                        return "";
                    }
                    return object.getUpTime() + "";
                }
            }


        };



        columnsOrder();
     }

    private void columnsOrder() {

        table.addColumn(columnIpAddress,"Ip");
        table.addColumn(columnNameToDisplay,"Device Name");
        table.addColumn(columnIsSwitch,"Switch");
        table.addColumn(columnClevel,"CLevel");
        table.addColumn(columnUpTime,"Up time");

        table.addColumn(columnPingOver,"Ping");
        table.addColumn(columnBw,"Bw");
        table.addColumn(columnIpChange,"IP");
        table.addColumn(columnMacChange,"MAC");
        table.addColumn(columnMove,"Move");
        table.addColumn(columnLink,"Link");
        table.addColumn(columnDisc, "Disc");
        // table.addColumn(columnNewd,"Newd");
        table.addColumn(columnTotal, "Total");

        columnBw.setSortable(true);
        columnClevel.setSortable(true);
        columnDisc.setSortable(true);
        columnIpAddress.setSortable(true);
        columnLink.setSortable(true);
        columnNameToDisplay.setSortable(true);
        columnMove.setSortable(true);
        //columnNewd.setSortable(true);
        columnIsSwitch.setSortable(true);
        columnTotal.setSortable(true);
        columnUpTime.setSortable(true);
        columnPingOver.setSortable(true);
        columnMacChange.setSortable(true);
        columnIpChange.setSortable(true);

        columnPingOver.setCellStyleNames("pingOverColor");
//        column.setCellStyleNames("pingFailColor");
        columnBw.setCellStyleNames("bwOverColor");
        columnIpChange.setCellStyleNames("ipChangeColor");
        columnMacChange.setCellStyleNames("macChangeColor");
        columnMove.setCellStyleNames("moveColor");
        columnLink.setCellStyleNames("linkSpeedColor");
        columnDisc.setCellStyleNames("disconnectColor");
//        columnClevel.setCellStyleNames("enetipColor");

        table.addColumnStyleName(5,"pingOverBackground");
        table.addColumnStyleName(6,"bwOverBackground");
        table.addColumnStyleName(7,"ipChangeBackground");
        table.addColumnStyleName(8,"macChangeBackground");
        table.addColumnStyleName(9,"moveBackground");
        table.addColumnStyleName(10,"linkSpeedBackground");
        table.addColumnStyleName(11, "disconnectedBackground");


           }

    private void sortTable(List<KpiStatsRow> groupList, CellTable<KpiStatsRow> table){
        ListDataProvider<KpiStatsRow> dataProvider = new ListDataProvider<KpiStatsRow>();
        dataProvider.addDataDisplay(table);

        List<KpiStatsRow> list = dataProvider.getList();

        for (KpiStatsRow group : groupList) {
            list.add(group);
        }
        final ColumnSortEvent.ListHandler<KpiStatsRow> columnSortHandler = new ColumnSortEvent.ListHandler<KpiStatsRow>(list);

        columnSortHandler.setComparator(columnBw,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {
                    if( o1.getBw()> o2.getBw())
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnClevel,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {
                    if( o1.getClevel()> o2.getClevel())
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnDisc, new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {
                    if( o1.getDisc()> o2.getDisc())
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnIpAddress, new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getIpaddress().compareTo(o2.getIpaddress()) : 1;
                }
                return -1;
            }
        });

        //
        columnSortHandler.setComparator(columnLink,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getLink()> o2.getLink())
                        return 1;
                }
                return -1;
            }
        });
        //

        columnSortHandler.setComparator(columnNameToDisplay,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getNameToDisplay().compareTo(o2.getNameToDisplay()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnMove,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getMove()> o2.getMove())
                        return 1;
                }
                return -1;
            }
        });

        /*
        columnSortHandler.setComparator(columnNewd,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getNewd()> o2.getNewd())
                        return 1;
                }
                return -1;
            }
        });
        */

        columnSortHandler.setComparator(columnIsSwitch,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                int o1int = 0;
                int o2int = 0;
                if (o1.isSwitch()) o1int = 1;
                if (o2.isSwitch()) o2int = 1;

                if (o1int == o2int) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1int > o2int)
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnTotal,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getTotal()> o2.getTotal())
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnUpTime,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    double o1value = Double.parseDouble(o1.getUpTime());
                    double o2value = Double.parseDouble(o2.getUpTime());
                    if( o1value > o2value)
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnPingOver,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getPt()> o2.getPt())
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnIpChange,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getIpc()> o2.getIpc())
                        return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnMacChange,new Comparator<KpiStatsRow>() {

            public int compare(KpiStatsRow o1,KpiStatsRow o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getMacc()> o2.getMacc())
                        return 1;
                }
                return -1;
            }
        });

        table.addColumnSortHandler(columnSortHandler);
        table.getColumnSortList().push(columnPingOver);
        table.getColumnSortList().push(columnIpChange);
        table.getColumnSortList().push(columnMacChange);
        table.getColumnSortList().push(columnClevel);
        table.getColumnSortList().push(columnBw);
        table.getColumnSortList().push(columnDisc);
        table.getColumnSortList().push(columnIpAddress);
        table.getColumnSortList().push(columnLink);
        table.getColumnSortList().push(columnMove);
        // table.getColumnSortList().push(columnNewd);
        table.getColumnSortList().push(columnIsSwitch);
        table.getColumnSortList().push(columnTotal);
        table.getColumnSortList().push(columnNameToDisplay);
        table.getColumnSortList().push(columnUpTime);




    }

    private void displayCellTable(ArrayList<KpiStatsRow> dataList){
        table.setRowData(0, dataList);
        table.setRowCount(dataList.size());

        table.setPageSize(dataList.size());
        sortTable(dataList, table);

    }

}


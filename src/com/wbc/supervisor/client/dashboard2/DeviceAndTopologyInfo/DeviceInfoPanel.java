package com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEvent;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEventHandler;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.dto.DeviceinfoDataDTO;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 12/16/2014.
 */
public class DeviceInfoPanel extends VerticalPanel implements WbcCsvHtmlTableInterface {
    private WbcNameWidget currentNetwork=null;
    int newNetworkId=0;

    CellTable<DeviceinfoDataDTO> table = new CellTable<DeviceinfoDataDTO>();;
    Column<DeviceinfoDataDTO, String> columnName;
    Column<DeviceinfoDataDTO, String> columnIpAddress;
    Column<DeviceinfoDataDTO, String> columnMacAddress;
    Column<DeviceinfoDataDTO, String> columnVendor;
    Column<DeviceinfoDataDTO, String> columnLocation;
    Column<DeviceinfoDataDTO, String> colunUd1Name;
    Column<DeviceinfoDataDTO, String> columnCriticalType;
    Column<DeviceinfoDataDTO, String> columnCategory;
    Column<DeviceinfoDataDTO, String> columnDescription;
    private boolean maintabLoaded = false;
    private static Logger logger = Logger.getLogger("DeviceInfoPanel.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "DeviceInfoPanel", "Network Change" );


    /*
    new column names
     */

    public DeviceInfoPanel() {
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        HorizontalPanel hpnlControl = new HorizontalPanel();
        hpnlControl.add(sortByLayout());
        add(table);
        setSpacing(3);
        addColumns();
        setStyleName("deviceInfoDeviceData");
        table.addStyleName("deviceInfoDeviceData");
    }

    public void displayDeviceInfoPanel(){

        supervisor.eventBus.addHandler(MainPanelLoadedEvent.TYPE, new MainPanelLoadedEventHandler() {
            @Override
            public void onMainPanelLoaded(MainPanelLoadedEvent event) {
                maintabLoaded = true;
            }
        });

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if (newNetwork != currentNetwork && maintabLoaded) {
                    logger.log(Level.INFO, "DeviceInfoPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                    newNetworkId = newNetwork.getId();
                    currentNetwork = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info( networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed." );
                        // test, show the times
                        logger.info( "TEST " + networkChangeRpcInfo.showLongTimes() );
                    }
                    networkChangeRpcInfo.setEventReceivedTime( System.currentTimeMillis());

                    getDeviceData(newNetworkId);
                } else {
                    logger.log(Level.INFO, "DeviceInfoPanel got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });
//        getDeviceData(currentNetworkId);
       }

    private void getDeviceData(int nwid) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);
        table.setRowCount(0);
        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getNetworkDeviceData(nwid, new AsyncCallback<ArrayList<DeviceinfoDataDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("fail getting device list" + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<DeviceinfoDataDTO> list) {
                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                deviceCellTable(list);
            }
        });
    }

      private void deviceCellTable(ArrayList<DeviceinfoDataDTO> dataList){
          networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
          table.setRowData(0, dataList);
          table.setRowCount(dataList.size());

          table.setPageSize(dataList.size());
          sortTable(dataList, table);
          networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
          logger.log(Level.INFO, networkChangeRpcInfo.getData());
          networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
    }

    private void addColumns() {
        columnMacAddress = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {

                return object.getMacAddress()+"";
            }
        };


        columnIpAddress = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {

                return object.getIpAddress()+"";
            }
        };


        columnVendor = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getVendor()+"";
            }
        };


        columnLocation = new Column<DeviceinfoDataDTO , String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getLocation()+"";
            }
        };


        colunUd1Name = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getUd1Name()+"";
            }
        };


        columnName = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getName()+"";
            }
        };


        columnCriticalType = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getCriticalType()+"";
            }
        };


        columnCategory = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getCategory()+"";
            }
        };

        columnDescription = new Column<DeviceinfoDataDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoDataDTO object) {
                return object.getDescription()+"";
            }
        };

        columnsOrder();
        setColumnHandlers();
    }

    private void setColumnHandlers() {
        columnIpAddress.setFieldUpdater(new FieldUpdater<DeviceinfoDataDTO, String>() {
            @Override
            public void update(int index, DeviceinfoDataDTO object, String value) {

//                Window.open("http://127.0.0.1:8765/dsbsvr/DashboardServer?report=devicedetails&id="+object.getDescid()+"", "_blank", "");
                callDeviceInfoAndStatDetailsPage(object.getDescid());
            }
        });
    }

    private void callDeviceInfoAndStatDetailsPage(int deviceid){
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.callDeviceInfoAndStatDetailsPage(deviceid, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.INFO, "callDeviceInfoAndStatDetailsPage failed: "+ caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                //Here we can send the DTO, which we will receive from this above rpc onSuccess back from server..
                Window.open("DeviceInfoAndDetails.jsp","","");
            }
        });
    }

    private void columnsOrder() {

        table.addColumn(columnName,"Name");
        table.addColumn(columnIpAddress,"Ip Address");
        table.addColumn(columnMacAddress,"Mac Address");
        table.addColumn(columnVendor,"Vendor");
        table.addColumn(columnLocation,"Location");
        table.addColumn(colunUd1Name,"Ud1 Name");
        table.addColumn(columnCriticalType,"Critical type");
        table.addColumn(columnCategory,"Category");
        table.addColumn(columnDescription,"Description");

        columnMacAddress.setSortable(true);
        columnLocation.setSortable(true);
        colunUd1Name.setSortable(true);
        columnName.setSortable(true);
        columnCriticalType.setSortable(true);
        columnCategory.setSortable(true);
        columnVendor.setSortable(true);
        columnIpAddress.setSortable(true);

        table.setColumnWidth(columnMacAddress, "    160px");
        table.getColumn(1).setCellStyleNames("webLink");

    }

    private void sortTable(List<DeviceinfoDataDTO> groupList, CellTable<DeviceinfoDataDTO> table){
        ListDataProvider<DeviceinfoDataDTO> dataProvider = new ListDataProvider<DeviceinfoDataDTO>();
        dataProvider.addDataDisplay(table);

        List<DeviceinfoDataDTO> list = dataProvider.getList();

        for (DeviceinfoDataDTO group : groupList) {
            list.add(group);
        }
        final ColumnSortEvent.ListHandler<DeviceinfoDataDTO> columnSortHandler = new ColumnSortEvent.ListHandler<DeviceinfoDataDTO>(list);

        columnSortHandler.setComparator(columnMacAddress,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {

                    return (o2 != null) ? o1.getMacAddress().toLowerCase().compareTo(o2.getMacAddress().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnIpAddress,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {

                    return (o2 != null) ? o1.getIpAddress().toLowerCase().compareTo(o2.getIpAddress().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnVendor, new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                   if (o1 != null) {

                    return (o2 != null) ? o1.getVendor().toLowerCase().compareTo(o2.getVendor().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnLocation, new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                 if (o1 != null) {

                    return (o2 != null) ? o1.getLocation().toLowerCase().compareTo(o2.getLocation().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        //
        columnSortHandler.setComparator(colunUd1Name,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                 if (o1 != null) {

                    return (o2 != null) ? o1.getUd1Name().toLowerCase().compareTo(o2.getUd1Name().toLowerCase()) : 1;
                }
                return -1;
            }
        });
        //

        columnSortHandler.setComparator(columnName,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                 if (o1 != null) {

                    return (o2 != null) ? o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnCriticalType,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {
                    if( o1.getCriticalType()> o2.getCriticalType())
                    return 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnCategory,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getCategory().toLowerCase().compareTo(o2.getCategory().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnDescription,new Comparator<DeviceinfoDataDTO>() {

            public int compare(DeviceinfoDataDTO o1,DeviceinfoDataDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        table.addColumnSortHandler(columnSortHandler);
        table.getColumnSortList().push(columnMacAddress);
        table.getColumnSortList().push(columnVendor);
        table.getColumnSortList().push(columnIpAddress);
        table.getColumnSortList().push(columnLocation);
        table.getColumnSortList().push(columnCriticalType);
        table.getColumnSortList().push(columnCategory);
        table.getColumnSortList().push(colunUd1Name);
        table.getColumnSortList().push(columnName);

    }

    private HorizontalPanel sortByLayout() {
        HorizontalPanel hpnlSortBy = new HorizontalPanel();
        Label lblSortBy = new Label("Sort By");
        ListBox listSortOptions = new ListBox();
        hpnlSortBy.add(lblSortBy);
        hpnlSortBy.add(listSortOptions);
        listSortOptions.addItem("topology");
        listSortOptions.addItem("device name");
        listSortOptions.addItem("ip address");
        hpnlSortBy.setSpacing(3);
        return hpnlSortBy;
    }
}

package com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.wbc.supervisor.client.dashboard2.AppConstants;

import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEvent;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEventHandler;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;

import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.dto.DeviceinfoNamesDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Junaid on 12/31/2014.
 */
public class NamesPanel extends VerticalPanel {
    private WbcNameWidget currentNetwork=null;
    int newNetworkId=0;

    CellTable<DeviceinfoNamesDTO> table = new CellTable<DeviceinfoNamesDTO>();
    Column<DeviceinfoNamesDTO, String> columnName;
    Column<DeviceinfoNamesDTO, String> columnIpAddress;
    Column<DeviceinfoNamesDTO, String> columnLocation;
    Column<DeviceinfoNamesDTO, String> columnUd1name;
    Column<DeviceinfoNamesDTO, String> columnUd2name;
    Column<DeviceinfoNamesDTO, String> columnUd3name;
    Column<DeviceinfoNamesDTO, String> columnUd4name;
    Column<DeviceinfoNamesDTO, String> columnUd5name;
    Column<DeviceinfoNamesDTO, String> columnUd6name;
    Column<DeviceinfoNamesDTO, String> columnNotes;
    private Button btnUpdate = new Button("update");
    private Button btnPreview = new Button(com.wbc.supervisor.client.dashboard2.DashboardConstants.PREVIEW);
    private ArrayList<DeviceinfoNamesDTO> savedDeviceNamesList;
    private ArrayList<DeviceinfoNamesDTO> deviceNamesListFromServer;
    private ArrayList<Integer> changedIndexes = new ArrayList<Integer>();
    private boolean maintabLoaded = false;
    private static Logger logger = Logger.getLogger("NamesPanel.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "NamesPanel", "Network Change" );
    private  ArrayList<String> userDefinedColumnNames;

    public NamesPanel() {
        final com.wbc.supervisor.client.dashboard2.AppConstants constantsI8n=(com.wbc.supervisor.client.dashboard2.AppConstants) com.google.gwt.core.shared.GWT.create(com.wbc.supervisor.client.dashboard2.AppConstants.class);
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        HorizontalPanel buttonsContainer = new HorizontalPanel();
        Label lblSorting = new Label(constantsI8n.EDITABLE_FIELDS());
        lblSorting.setStyleName("sortingNamesPanel");
        buttonsContainer.add(btnPreview);
        buttonsContainer.add(btnUpdate);
        buttonsContainer.add(lblSorting);
        btnPreview.setWidth("100px");
        btnUpdate.setWidth("100px");
        buttonsContainer.setSpacing(2);
        add(buttonsContainer);
        add(table);
        setSpacing(3);
        //TODO JUNAID, the column names for ud1 to ud6 need to be retrieved from server by calling ArrayList<String> getIntravueProperties() // Done
        getIntravueProperties();

        btnUpdate.setEnabled(false);
        table.setWidth("100%");
        table.setTableLayoutFixed(true);
        setStyleName("deviceInfoNames");
        table.addStyleName("deviceInfoNames");
    }

    public void getIntravueProperties(){
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.getIntravueProperties(new AsyncCallback<ArrayList<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.INFO, "getIntravueProperties failed: "+ caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<String> result) {
                userDefinedColumnNames = result;
                createColumns();
                setHandlers();
            }
        });
    }

    public void displayNamesPanel(){

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
                    logger.log(Level.INFO, "NamesPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                    newNetworkId = newNetwork.getId();
                    currentNetwork = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info( networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed." );
                        logger.info( "TEST " + networkChangeRpcInfo.showLongTimes() );
                    }
                    networkChangeRpcInfo.setEventReceivedTime( System.currentTimeMillis());

                    getNetworkDeviceNames(newNetworkId);
                } else {
                    logger.log(Level.INFO, "NamesPanel got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });
    }

    private void setHandlers() {

        btnUpdate.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                update();
               }
        });

        btnPreview.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(btnPreview.getText().equals(com.wbc.supervisor.client.dashboard2.DashboardConstants.PREVIEW)) {
                    preview();
                }else if(btnPreview.getText().equals(com.wbc.supervisor.client.dashboard2.DashboardConstants.CLOSE_PREVIEW)) {
                    closePreview();
                }
            }
        });

        columnIpAddress.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO, String>() {
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
//                     Window.open("http://127.0.0.1:8765/dsbsvr/DashboardServer?report=devicedetails&id="+object.getDeviceid()+"", "_blank", "");
                     callDeviceInfoAndStatDetailsPage(object.getDeviceid());
            }
        });
    }

    private void callDeviceInfoAndStatDetailsPage(final int deviceid){
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.callDeviceInfoAndStatDetailsPage(deviceid, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.INFO, "callDeviceInfoAndStatDetailsPage failed: "+ caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                Window.open("DeviceInfoAndDetails.jsp","","");
            }
        });
    }

    private void preview(){
        final ArrayList<DeviceinfoNamesDTO> updatedDeviceInfoNames = new ArrayList<DeviceinfoNamesDTO>();
        for(int i=0; i< changedIndexes.size(); i++){
            updatedDeviceInfoNames.add(savedDeviceNamesList.get(changedIndexes.get(i)));
        }
        table.setRowCount(0);
        deviceCellTable(updatedDeviceInfoNames);
        btnPreview.setText(com.wbc.supervisor.client.dashboard2.DashboardConstants.CLOSE_PREVIEW);
    }

    private void closePreview(){

        table.setRowCount(0);
        deviceCellTable(savedDeviceNamesList);
        btnPreview.setText(com.wbc.supervisor.client.dashboard2.DashboardConstants.PREVIEW);
    }

    private void update() {
       final ArrayList<DeviceinfoNamesDTO> updatedDeviceInfoNames = new ArrayList<DeviceinfoNamesDTO>();
        for(int i=0; i< changedIndexes.size(); i++){
            updatedDeviceInfoNames.add(savedDeviceNamesList.get(changedIndexes.get(i)));
        }

        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.updateNetworkDeviceNamesMap(updatedDeviceInfoNames, new AsyncCallback<String>(){
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fail: updateNetworkDeviceNamesMap");
            }

            @Override
            public void onSuccess(String result) {
                if(updatedDeviceInfoNames.size()>0) {
                    Window.alert("Record updated");
                }else{
                    Window.alert("No changes found");
                }
                updatedDeviceInfoNames.clear();
                changedIndexes.clear();
                btnUpdate.setEnabled(false);
            }
         });
    }

    private void createColumns() {

         columnName = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {

                return object.getName();
            }
        };

        SingleSelectionModel<DeviceinfoNamesDTO> selModel = new SingleSelectionModel<DeviceinfoNamesDTO>();
        table.setSelectionModel(selModel);

        columnName.setSortable(true);
        table.addColumn(columnName,"Name");
        //---------------------------------------------------
        columnIpAddress = new Column<DeviceinfoNamesDTO, String>(new ClickableTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {

                return object.getIpAddress().toString();
            }
        };
        columnIpAddress.setSortable(true);
        table.addColumn(columnIpAddress,"IP address");

        //---------------------------------------------------
        columnLocation = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {
                return object.getLocation();
            }
        };
        columnLocation.setSortable(true);
        table.addColumn(columnLocation,"Location");
        //---------------------------------------------------
        columnUd1name = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {
                return object.getUd1name();
            }
        };
        columnUd1name.setSortable(true);
            table.addColumn(columnUd1name, userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD1)==null? "Ud1": userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD1));


        //TODO JUNAID, each one of these should be in userDefinedColumnNames// Done
        // table.addColumn(columnUd1name, userDefinedColumnNames.get(0));
        //---------------------------------------------------
        columnUd2name = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {

                return object.getUd2name();
            }
        };
        columnUd2name.setSortable(true);
            table.addColumn(columnUd2name, userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD2)==null?"UD2": userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD2));

        //---------------------------------------------------
        columnUd3name = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {

                return object.getUd3name();
            }
        };
        columnUd3name.setSortable(true);
            table.addColumn(columnUd3name, userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD3) == null ? "UD3" : userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD3));

        //---------------------------------------------------
        columnUd4name = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {

                return object.getUd4name();
            }
        };
        columnUd4name.setSortable(true);
            table.addColumn(columnUd4name, userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD4) == null?"UD4": userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD4));

        //---------------------------------------------------
        columnUd5name = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {

                return object.getUd5name();
            }
        };
        columnUd5name.setSortable(true);
             table.addColumn(columnUd5name, userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD5) == null ?"UD 5":  userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD5));

        //---------------------------------------------------
        columnUd6name = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {
                return object.getUd6name();
            }
        };
        columnUd6name.setSortable(true);
            table.addColumn(columnUd6name, userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD6) ==null ? "UD6": userDefinedColumnNames.get(com.wbc.supervisor.client.dashboard2.DashboardConstants.UD6));


        table.getColumn(1).setCellStyleNames("webLink");

        //---------------------------------------------------
        columnNotes = new Column<DeviceinfoNamesDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(DeviceinfoNamesDTO object) {
                return object.getNotes();

            }
        };
        columnNotes.setSortable(true);
        table.addColumn(columnNotes,"Notes");

        //Moved to End of UI Display
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
    }


    private void getNetworkDeviceNames( int nwid ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        table.setRowCount(0);
        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getNetworkDeviceNamesMap(nwid, new AsyncCallback<ArrayList<DeviceinfoNamesDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("fail getNetworkDeviceNamesMap" + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<DeviceinfoNamesDTO> deviceNamesList) {
                //TODO JUNAID - should all these methods be called from the onSuccess, or should a new method consolidate the work //They all need to be here in onSuccess , the reason is , that they need the result coming from the server/RPC
                //saveOriginalListFromServer also needs deviceNamesList coming from RPC, so it need to be here .
                // which methods effect the display of data, ie, saveOriginalListFromServer is probably not part of what effects display of data

                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
                deviceCellTable(deviceNamesList);
                savedDeviceNamesList = deviceNamesList;
                saveOriginalListFromServer(deviceNamesList);
                editHandlers();
                // move the code below to the end of UI display (maybe new method) // Moved


            }
        });
    }

    private void saveOriginalListFromServer(ArrayList<DeviceinfoNamesDTO> deviceNamesList) {
        deviceNamesListFromServer = new ArrayList<DeviceinfoNamesDTO>();
        for(int i=0; i<deviceNamesList.size(); i++ ){
            DeviceinfoNamesDTO deviceinfoNamesDTO = new DeviceinfoNamesDTO();
            deviceinfoNamesDTO.setName(deviceNamesList.get(i).getName());
            deviceinfoNamesDTO.setLocation(deviceNamesList.get(i).getLocation());
            deviceinfoNamesDTO.setUd1name(deviceNamesList.get(i).getUd1name());
            deviceinfoNamesDTO.setUd2name(deviceNamesList.get(i).getUd2name());
            deviceinfoNamesDTO.setUd3name(deviceNamesList.get(i).getUd3name());
            deviceinfoNamesDTO.setUd4name(deviceNamesList.get(i).getUd4name());
            deviceinfoNamesDTO.setUd5name(deviceNamesList.get(i).getUd5name());
            deviceinfoNamesDTO.setUd6name(deviceNamesList.get(i).getUd6name());
            deviceinfoNamesDTO.setNotes(deviceNamesList.get(i).getNotes());
            deviceNamesListFromServer.add(deviceinfoNamesDTO) ;
        }
    }

    private void editHandlers() {
        columnName.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getName().equals(value)){ // If there is a change, Add this index in changedIndex List
                    savedDeviceNamesList.get(index).setName(value);
                    if(!changedIndexes.contains(index)){
                        changedIndexes.add(index);
                    }
                }  if(changedIndexes.contains(index) && !changeInRow(index)){
                        removeIndexFromChangedIndexesList(index);

                }
                updateButtonStatus();
            }
        });

        columnLocation.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getLocation().equals(value)) {
                    savedDeviceNamesList.get(index).setLocation(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                    if(changedIndexes.contains(index) && !changeInRow(index)){
                            removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });

        columnUd1name.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getUd1name().equals(value)) {
                    savedDeviceNamesList.get(index).setUd1name(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                        if(changedIndexes.contains(index) && !changeInRow(index)){
                            removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });

        columnUd2name.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getUd2name().equals(value)) {
                    savedDeviceNamesList.get(index).setUd2name(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }    if(changedIndexes.contains(index) && !changeInRow(index)){
                            removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });

        columnUd3name.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getUd3name().equals(value)) {
                    savedDeviceNamesList.get(index).setUd3name(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                if(changedIndexes.contains(index) && !changeInRow(index)){
                            removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });

        columnUd4name.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getUd4name().equals(value)) {
                    savedDeviceNamesList.get(index).setUd4name(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                        if(changedIndexes.contains(index) && !changeInRow(index)) {
                            removeIndexFromChangedIndexesList(index);
                        }


                updateButtonStatus();
            }
        });

        columnUd5name.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getUd5name().equals(value)) {
                    savedDeviceNamesList.get(index).setUd5name(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                        if(changedIndexes.contains(index) && !changeInRow(index)){
                            removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });

        columnUd6name.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getUd6name().equals(value)) {
                    savedDeviceNamesList.get(index).setUd6name(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                        if(changedIndexes.contains(index) && !changeInRow(index)){
                            removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });

        columnNotes.setFieldUpdater(new FieldUpdater<DeviceinfoNamesDTO,String>(){
            @Override
            public void update(int index, DeviceinfoNamesDTO object, String value) {
                if(!object.getNotes().equals(value)) {
                    savedDeviceNamesList.get(index).setNotes(value);
                    if (!changedIndexes.contains(index)) {
                        changedIndexes.add(index);
                    }
                }
                if(changedIndexes.contains(index) && !changeInRow(index)){
                    removeIndexFromChangedIndexesList(index);


                }
                updateButtonStatus();
            }
        });
    }

    private void removeIndexFromChangedIndexesList(int index) {
        for(int i=0; i< changedIndexes.size(); i++) {
            if(changedIndexes.get(i) == index) {
                changedIndexes.remove(i);
            }
        }
    }

    private boolean changeInRow(int index) {
        if(!savedDeviceNamesList.get(index).getName().trim().equals(deviceNamesListFromServer.get(index).getName().trim())){
            return true;
        }
        if(!savedDeviceNamesList.get(index).getUd4name().trim().equals(deviceNamesListFromServer.get(index).getUd4name().trim())){
            return true;
        }if(!savedDeviceNamesList.get(index).getUd6name().trim().equals(deviceNamesListFromServer.get(index).getUd6name().trim())){
            return true;
        }if(!savedDeviceNamesList.get(index).getUd1name().trim().equals(deviceNamesListFromServer.get(index).getUd1name().trim())){
            return true;
        }if(!savedDeviceNamesList.get(index).getUd2name().trim().equals(deviceNamesListFromServer.get(index).getUd2name().trim())){
            return true;
        }if(!savedDeviceNamesList.get(index).getUd3name().trim().equals(deviceNamesListFromServer.get(index).getUd3name().trim())){
            return true;
        }
        if(!savedDeviceNamesList.get(index).getUd5name().trim().equals(deviceNamesListFromServer.get(index).getUd5name().trim())){
            return true;
        }if(!savedDeviceNamesList.get(index).getLocation().trim().equals(deviceNamesListFromServer.get(index).getLocation().trim())){
            return true;
        }if(!savedDeviceNamesList.get(index).getNotes().trim().equals(deviceNamesListFromServer.get(index).getNotes().trim())){
            return true;
        }

        return false;
          }

    private void updateButtonStatus() {

        if(changedIndexes.size()>0){
            btnUpdate.setEnabled(true);
        }else{
            btnUpdate.setEnabled(false);
        }
    }

    private void deviceCellTable(ArrayList<DeviceinfoNamesDTO> deviceNamesList) {
        //TODO do we have to CLEAR data that was there ??,  No , we dont have to
        table.setRowData(0, deviceNamesList);
        table.setRowCount(deviceNamesList.size());
        table.setPageSize(deviceNamesList.size());


        //TODO implement the sort table, use String compaator for most..// Sort done , except of IPAddress
        sortTable(deviceNamesList, table);
    }

    private void sortTable(List<DeviceinfoNamesDTO> groupList, CellTable<DeviceinfoNamesDTO> table){
        ListDataProvider<DeviceinfoNamesDTO> dataProvider = new ListDataProvider<DeviceinfoNamesDTO>();
        dataProvider.addDataDisplay(table);

        List<DeviceinfoNamesDTO> list = dataProvider.getList();

        for (DeviceinfoNamesDTO group : groupList) {
            list.add(group);
        }
        final ColumnSortEvent.ListHandler<DeviceinfoNamesDTO> columnSortHandler = new ColumnSortEvent.ListHandler<DeviceinfoNamesDTO>(list);

        columnSortHandler.setComparator(columnIpAddress,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {

                    return (o2 != null) ? o1.getIpAddress().toLowerCase().compareTo(o2.getIpAddress().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnLocation,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }

                if (o1 != null) {

                    return (o2 != null) ? o1.getLocation().toLowerCase().compareTo(o2.getLocation().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnName, new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnUd1name, new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getUd1name().toLowerCase().compareTo(o2.getUd1name().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        //
        columnSortHandler.setComparator(columnUd2name,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getUd2name().toLowerCase().compareTo(o2.getUd2name().toLowerCase()) : 1;
                }
                return -1;
            }
        });
        //

        columnSortHandler.setComparator(columnUd3name,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getUd3name().toLowerCase().compareTo(o2.getUd3name().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnUd4name,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getUd4name().toLowerCase().compareTo(o2.getUd4name().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnUd5name,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getUd5name().toLowerCase().compareTo(o2.getUd5name().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnUd6name,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getUd6name().toLowerCase().compareTo(o2.getUd6name().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        columnSortHandler.setComparator(columnNotes,new Comparator<DeviceinfoNamesDTO>() {

            public int compare(DeviceinfoNamesDTO o1,DeviceinfoNamesDTO o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 != null) {

                    return (o2 != null) ? o1.getNotes().toLowerCase().compareTo(o2.getNotes().toLowerCase()) : 1;
                }
                return -1;
            }
        });

        table.addColumnSortHandler(columnSortHandler);
        table.getColumnSortList().push(columnNotes);
        table.getColumnSortList().push(columnUd2name);
        table.getColumnSortList().push(columnUd6name);
        table.getColumnSortList().push(columnUd5name);
        table.getColumnSortList().push(columnUd4name);
        table.getColumnSortList().push(columnUd1name);
        table.getColumnSortList().push(columnIpAddress);
        table.getColumnSortList().push(columnLocation);
        table.getColumnSortList().push(columnName);


    }

}

package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.west;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.wbc.supervisor.client.dashboard2.DashboardConstants;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.*;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidgetOneLiner;
import com.wbc.supervisor.shared.IpAddress;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.dto.DeviceinfoDataDTO;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 6/27/2015.
 */
public class BandwidthDetailsIpAddresses extends FlowPanel {

    private Tree wbcNameWidgetOneLinerTree = new Tree();
    int newNetworkId = 0;
    private WbcNameWidget currentNetwork = null;
    private boolean maintabLoaded = false;
    private ArrayList<WbcNameWidgetOneLiner> wbcNameWidgetOneLiners = new ArrayList<WbcNameWidgetOneLiner>();
    private CheckBox checkShowAllDevices = new CheckBox("Show All Devices");
    private Button btnNameSort = new Button("Name Sort");
    private Button btnIpSort = new Button("Ip Sort");
    private String lastSortBy = "IP";
    private static Logger logger = Logger.getLogger("DetailedPingControlPanel.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo("DetailedPingControlPanel", "Network Change");
    private WbcNameWidgetOneLiner wbcNameWidgetPreviouslySelected = null;
    private ArrayList<DeviceinfoDataDTO> deviceinfoDataDTOList = null;
    private GettingDetailedPingDataEvent gettingdetailedPingDataEvent = null;
    private String ipAddress ;
    Date sessionExpires = null;

    public BandwidthDetailsIpAddresses() {
        setWidth("100%");
        addStyleName("invalid");
        if (!networkChangeRpcInfo.isReady())
            logger.info("DetailedPingControlPanel At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes());
        HorizontalPanel hpnlButtons = new HorizontalPanel();
        hpnlButtons.add(btnNameSort);
        hpnlButtons.add(btnIpSort);
        hpnlButtons.setSpacing(5);
         add(checkShowAllDevices);
        add(hpnlButtons);
        final ScrollPanel scroll = new ScrollPanel();
        scroll.add(wbcNameWidgetOneLinerTree);
        scroll.setHeight(Window.getClientHeight() - 265 + "px");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                scroll.setHeight(Window.getClientHeight() - 265 + "px");
            }
        });
        add(scroll);
        setHandlers();
//        getMachineIpAddress();
        sessionExpires = new Date(System.currentTimeMillis() + com.wbc.supervisor.client.dashboard2.DashboardConstants.SESSION_DURATION);
//        ipAddress = (String) Cookies.getCookie("ip");
        btnNameSort.setWidth("100px");
        btnIpSort.setWidth("100px");
        setStyleName("pingDetailsWest");
    }

    private void setHandlers() {


        supervisor.eventBus.addHandler(GettingDetailedPingDataEvent.TYPE, new GettingDetailedPingDataEventHandler() {
            @Override
            public void onBrowse(GettingDetailedPingDataEvent event) {
                gettingdetailedPingDataEvent = event;
                getPingDetailsData(newNetworkId);
            }


        });

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
                    logger.info("DetailedPingControlPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                    newNetworkId = newNetwork.getId();
                    currentNetwork = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info(networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed.");
                        // test, show the times
                        logger.info("TEST " + networkChangeRpcInfo.showLongTimes());
                    }
                    networkChangeRpcInfo.setEventReceivedTime(System.currentTimeMillis());
//                    getPingDetailsData(newNetworkId);
                } else {
                    logger.info("DetailedPingControlPanel got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });
        btnNameSort.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sortByName();
            }
        });

        btnIpSort.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sortByIpAddress();
            }
        });

        checkShowAllDevices.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {


                if(checkShowAllDevices.isChecked()){
                    showAllDevices();
                    logger.log(Level.INFO, "'Show all Devices checked, 'Going to set Cookies in DetailedPingControlPanel");
                    System.out.println("sessionExpires value is :"+ sessionExpires);
//                   Cookies.setCookie("showAllDevices", "yes", sessionExpires, null, "/", false);
                    logger.log(Level.INFO, "Cookies set");
                }else{
                    showOnlyDevicesInGraph();
//                   Cookies.setCookie("showAllDevices", "no", sessionExpires, null, "/", false);
                }
            }
        });

    }

    private void showAllDevices(){

        populateWbcNameList(deviceinfoDataDTOList);
    }

    private void showOnlyDevicesInGraph() {
        logger.log(Level.INFO, "calling getFilteredListAsPerGraph");
        ArrayList<DeviceinfoDataDTO> checkedList = getFilteredListAsPerGraph();
        logger.log(Level.INFO, "back from getFilteredListAsPerGraph");
        populateWbcNameList(checkedList);
        logger.log(Level.INFO, "wbcNameList Populated");
    }

    public void getPingDetailsData(int nwid) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);
        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getNetworkDeviceData(nwid, new AsyncCallback<ArrayList<DeviceinfoDataDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("fail getting device list" + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<DeviceinfoDataDTO> list) {
                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                deviceinfoDataDTOList = list;

//                String showAllDevicesSavedInCookies = Cookies.getCookie("showAllDevices");
//                String ip = Cookies.getCookie("ip");
//                if (showAllDevicesSavedInCookies != null && ip!=null && ip.equals(ipAddress)) {
//                    if(showAllDevicesSavedInCookies.equalsIgnoreCase("yes")){
//                        checkShowAllDevices.setChecked(true);
//                        populateWbcNameList(list);
//                    }else{
//                        ArrayList<DeviceinfoDataDTO> checkedList = getFilteredListAsPerGraph();
//                        populateWbcNameList(checkedList);
//                    }
//                }else{
                if(checkShowAllDevices.isChecked()){
                    populateWbcNameList(list);
                }else{
                    ArrayList<DeviceinfoDataDTO> checkedList = getFilteredListAsPerGraph();
                    populateWbcNameList(checkedList);
                }
            }
//                Cookies.setCookie("ip", ipAddress, sessionExpires, null, "/", false);



//            }
        });
    }

    private ArrayList<DeviceinfoDataDTO> getFilteredListAsPerGraph() {
        ArrayList<DeviceinfoDataDTO> checkedList = new ArrayList<DeviceinfoDataDTO>();
        for (int j = 1; j < gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().size(); j++) {
            for (int i = 0; i < deviceinfoDataDTOList.size(); i++) {
                if (gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().get(j).getWbcSeriesInfo().getName().contains(deviceinfoDataDTOList.get(i).getIpAddress())) {
                    checkedList.add(deviceinfoDataDTOList.get(i));
                }
            }
        }
        return checkedList;
    }

    private void populateWbcNameList(final ArrayList<DeviceinfoDataDTO> list) {
        networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
        wbcNameWidgetOneLiners.clear();
        for (int i = 0; i < list.size(); i++) {
            addWbcNameWidgetOneLiner(list, i);
        }
        addChartSelectionHandler();

        if (lastSortBy.equals("IP")) {
            sortByIpAddress();
        } else {
            sortByName();
        }
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY

    }

//    private void addChartSelectionHandler() {
//        /////////////Selecting Node as per selected column in chart///////////
//        gettingdetailedPingDataEvent.getCoreChart().addSelectHandler(new SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {
//
//                JsArray<Selection> selections = gettingdetailedPingDataEvent.getCoreChart().getSelections();
//               if(wbcNameWidgetPreviouslySelected!=null){
//                   wbcNameWidgetPreviouslySelected.removeStyleName("bluebackground");
//               }
//                for (int i = 0; i < selections.length(); i++) {
//                    Selection selection = selections.get(0);
//                    int column = selection.getColumn();
//                    logger.info(gettingdetailedPingDataEvent.getDt().getColumnLabel(column));
//                    for(int j=0; j< wbcNameWidgetOneLiners.size(); j++){
//                        if(gettingdetailedPingDataEvent.getDt().getColumnLabel(column).contains(wbcNameWidgetOneLiners.get(j).getIp())){
//                            ///////////
//                            ScrollPanel scroll = (ScrollPanel)wbcNameWidgetOneLinerTree.getParent();
//                            int treePoistion=0;
//                            for(int k =0; k<wbcNameWidgetOneLinerTree.getItemCount(); k++){
//                                if(wbcNameWidgetOneLinerTree.getItem(k).getHTML().contains(wbcNameWidgetOneLiners.get(j).getIp())){
//                                    treePoistion = k;
//                                    break;
//                                }
//                            }
//                            scroll.setVerticalScrollPosition(treePoistion*15);
//                            //////////
//                            wbcNameWidgetOneLiners.get(j).setStyleName("bluebackground");
//                            wbcNameWidgetPreviouslySelected = wbcNameWidgetOneLiners.get(j);
//                            break;
//                        }
//                    }
//                }
//
//            }
//        });
//    }

    private void addChartSelectionHandler() {
        /////////////Selecting Node as per selected column in chart///////////
        gettingdetailedPingDataEvent.getCoreChart().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

                JsArray<Selection> selections = gettingdetailedPingDataEvent.getCoreChart().getSelections();
                if(wbcNameWidgetPreviouslySelected!=null){
                    wbcNameWidgetPreviouslySelected.removeStyleName("bluebackground");
                }
                for (int i = 0; i < selections.length(); i++) {
                    Selection selection = selections.get(0);
                    int column = selection.getColumn();
                    logger.info(gettingdetailedPingDataEvent.getDt().getColumnLabel(column));
                    for(int j=0; j< wbcNameWidgetOneLinerTree.getItemCount(); j++){
                        WbcNameWidgetOneLiner wbcNameWidgetOneLiner = (WbcNameWidgetOneLiner) wbcNameWidgetOneLinerTree.getItem(j).getWidget();

                        if(gettingdetailedPingDataEvent.getDt().getColumnLabel(column).contains(wbcNameWidgetOneLiner.getIp())){
                            ///////////
                            ScrollPanel scroll = (ScrollPanel)wbcNameWidgetOneLinerTree.getParent();
                            int treePosition=j;

                            scroll.setVerticalScrollPosition(treePosition*19);
                            wbcNameWidgetOneLiner.setStyleName("bluebackground");
                            wbcNameWidgetPreviouslySelected = wbcNameWidgetOneLiner;
                            break;
                        }
                    }
                }

            }
        });
    }

    private void adjustScroll(){

    }

    private void addWbcNameWidgetOneLiner(ArrayList<DeviceinfoDataDTO> list, int i) {
        final WbcNameWidgetOneLiner wbcNameWidgetOneLiner = new WbcNameWidgetOneLiner(list.get(i).getIpAddress(), list.get(i).getName(), "intravueLight");
        for (int j = 1; j < gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().size(); j++) {
            if (gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().get(j).getWbcSeriesInfo().getName().contains(list.get(i).getIpAddress())) {
                StringBuilder color =   getColor(gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().get(j).getWbcSeriesInfo().getR(), gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().get(j).getWbcSeriesInfo().getG(), gettingdetailedPingDataEvent.getDataBasedChartSeriesDTO().get(j).getWbcSeriesInfo().getB());
                wbcNameWidgetOneLiner.getColorAndEnableControl().getElement().getStyle().setProperty("backgroundColor", String.valueOf(color));
            }
        }
        TreeItem treeItem = new TreeItem();
        treeItem.setWidget(wbcNameWidgetOneLiner);
        wbcNameWidgetOneLinerTree.addItem(treeItem);
        wbcNameWidgetOneLiners.add(wbcNameWidgetOneLiner);


        wbcNameWidgetOneLiner.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (wbcNameWidgetPreviouslySelected != null) {
                    wbcNameWidgetPreviouslySelected.removeStyleName("bluebackground");
                }
                wbcNameWidgetOneLiner.setStyleName("bluebackground");
                wbcNameWidgetPreviouslySelected = wbcNameWidgetOneLiner;
                for(int i=0; i< gettingdetailedPingDataEvent.getDt().getNumberOfRows(); i++){
                    if(gettingdetailedPingDataEvent.getDt().getColumnLabel(i).contains(wbcNameWidgetOneLiner.getIp())){

                        Selection s = Selection.createColumnSelection(i);
                        final JsArray<Selection> selectionArray = Selection.createArray().cast();
                        selectionArray.set(0, s);// index will be 0 As we are only dealing with one selection at a time for now.
                        gettingdetailedPingDataEvent.getCoreChart().setSelections(selectionArray);

                        break;
                    }
                }

            }
        });
    }

    public void sortByName() {
        HashMap<String, WbcNameWidgetOneLiner> tempMap = new HashMap<String, WbcNameWidgetOneLiner>();
        for (WbcNameWidgetOneLiner name : wbcNameWidgetOneLiners) {
            String nameName = name.getName();
            nameName = MakeFirstLetterUppercase(nameName);//  making the first Letter as upper case Just to Make Sorting works fine.
            if (nameName == null || nameName.isEmpty()) {
                nameName = "     " + name.getIp();
            }
            tempMap.put(nameName, name);
        }
        List<String> nameList = new ArrayList<String>(tempMap.keySet());
        Collections.sort(nameList);
        wbcNameWidgetOneLinerTree.clear();
        for (String nameKey : nameList) {
            WbcNameWidgetOneLiner widget = tempMap.get(nameKey);
            widget.setNameFirst();
            TreeItem treeItem = new TreeItem();
            treeItem.setWidget(widget);
            wbcNameWidgetOneLinerTree.add(widget);

        }
        sortedByName();
        ; // place to put a break point
    }

    private String MakeFirstLetterUppercase(String nameName) {
        if (nameName.length() < 1)
            return nameName;
        String name = String.valueOf(nameName.charAt(0));
        name = name.toUpperCase();
        nameName = nameName.substring(1);
//        return name.concat(nameName);
        return name+nameName;
    }

    public void sortByIpAddress() {
        HashMap<IpAddress, WbcNameWidgetOneLiner> ipMap = new HashMap<IpAddress, WbcNameWidgetOneLiner>();
        HashMap<String, WbcNameWidgetOneLiner> notIpMap = new HashMap<String, WbcNameWidgetOneLiner>();
        for (WbcNameWidgetOneLiner name : wbcNameWidgetOneLiners) {
            IpAddress ip = new IpAddress(name.getIp());
            if (ip.isValid()) {
                ipMap.put(ip, name);
            } else {
                notIpMap.put(name.getIp(), name);
            }
        }
        List<IpAddress> ipList = new ArrayList<IpAddress>(ipMap.keySet());
        Collections.sort(ipList);
        wbcNameWidgetOneLinerTree.clear();
        for (IpAddress key : ipList) {
            WbcNameWidgetOneLiner widget = ipMap.get(key);
            widget.setIpFirst();
            TreeItem treeItem = new TreeItem();
            treeItem.setWidget(widget);
            wbcNameWidgetOneLinerTree.add(widget);
        }
        List<String> nameList = new ArrayList<String>(notIpMap.keySet());
        Collections.sort(nameList);
        for (String key : nameList) {
            WbcNameWidgetOneLiner widget = notIpMap.get(key);
            widget.setIpFirst();
            TreeItem treeItem = new TreeItem();
            treeItem.setWidget(widget);
            wbcNameWidgetOneLinerTree.add(widget);

        }
        sortedByIp();
        ; // place to put a break point
    }

    private void sortedByIp() {
        lastSortBy = "IP";
        btnNameSort.setEnabled(true);
        btnIpSort.setEnabled(false);
    }

    private void sortedByName() {
        lastSortBy = "Name";
        btnNameSort.setEnabled(false);
        btnIpSort.setEnabled(true);
    }

    public StringBuilder getColor(int r, int g, int b) {

        StringBuilder colorString = new StringBuilder();
        colorString.append("#");

        String value = Integer.toHexString(r);
        if(value.length()==1)value="0"+value;
        colorString.append(value);

        value=Integer.toHexString(g);
        if(value.length()==1)value="0"+value;
        colorString.append(value);

        value=Integer.toHexString(b);
        if(value.length()==1)value="0"+value;
        colorString.append(value);

        return colorString;
    }

}

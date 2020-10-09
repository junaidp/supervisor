package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.DashboardConstants;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameListboxWidget;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.WbcNamesDTO;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 9/26/14.
 */
public class WbcNamesControlPanel extends VerticalPanel {
    private ListBox listSortFields = new ListBox();
    private WbcNameListboxWidget listWbcNames = new WbcNameListboxWidget();
    private  TextBox selectedWbcTextBox;
    private WbcNameWidget lastSelectedWbcNameWidget;
    private static Logger logger = Logger.getLogger("WbcNamesControl.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "WbcNamesControlPanel", "fetch WbcNames" );
    private boolean showFiring = true;



    public ListBox getListSortFields() {
        return listSortFields;
    }

    public WbcNamesControlPanel(){
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        listSortFieldsLayout();
        listWbcNamesLayout();
        add(listSortFields);
        listSortFields.setWidth("100%");
        add(listWbcNames);
//        addStyleName("intravueLight");
    }

    private void listSortFieldsLayout(){
        listSortFields.clear();
        listSortFields.addItem("sort by");
        listSortFields.addItem(DashboardConstants.NETWORK_NAME);
        listSortFields.addItem(DashboardConstants.PARENT_IP);
        /* not ready yet
        listSortFields.addItem(DashboardConstants.KPI);
        listSortFields.addItem(DashboardConstants.ALARMS_WARNINGS);
        */
    }
    private void listWbcNamesLayout(){
        listWbcNames.setWidth("100%");
        listWbcNames.setStyleName("border");
        setWidth("100%");
//        listWbcNames.setSize("100%", Window.getClientHeight() - 120 + "px");
        fetchWbcNames();
    }



    private void sortControl(final WbcNameListboxWidget listWbcNames){
            listSortFields.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (listSortFields.getValue(listSortFields.getSelectedIndex()).equalsIgnoreCase(DashboardConstants.NETWORK_NAME)) {
                    listWbcNames.sortByName(true);
                } else if (listSortFields.getValue(listSortFields.getSelectedIndex()).equalsIgnoreCase(DashboardConstants.PARENT_IP)) {
                    listWbcNames.sortByIpaddress(false);
                } else if (listSortFields.getValue(listSortFields.getSelectedIndex()).equalsIgnoreCase(DashboardConstants.KPI)) {
                    // needs call to server
                } else if (listSortFields.getValue(listSortFields.getSelectedIndex()).equalsIgnoreCase(DashboardConstants.ALARMS_WARNINGS)) {
                    // needs call to server
                }
            }
        });
    }

    private void fetchWbcNames() {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);
        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getNetworkInfo(new AsyncCallback<ArrayList<WbcNamesDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.INFO, "getWbcNames Failed:" + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<WbcNamesDTO> wbcList) {
                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                //TODO clear the names that may already be in the list
                for(int i=0; i< wbcList.size(); i++) {
                    final WbcNameWidget wbcNameWidget = new WbcNameWidget(wbcList.get(i).getName1(), wbcList.get(i).getName2(), wbcList.get(i).getId(), wbcList.get(i).getRank());

                    listWbcNames.addNameWidget( wbcNameWidget);

                    makeNetworkUnSelected(wbcNameWidget);
                    for(int j=0; j<wbcNameWidget.getNameWidgetContainer().getWidgetCount(); j++ ){
                        final TextBox wbcTextBox = (TextBox) wbcNameWidget.getNameWidgetContainer().getWidget(j);



                        wbcTextBox.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                if(selectedWbcTextBox==null || selectedWbcTextBox.getParent()!=wbcTextBox.getParent()) {
                                    if (showFiring) logger.log(Level.INFO,"WbcNamesControlPanel: textBox clickHandler FIRING network changed event.");
                                    supervisor.eventBus.fireEvent(new SelectedNetworkChangeEvent(wbcNameWidget));
                                    if(lastSelectedWbcNameWidget == null ){
                                        lastSelectedWbcNameWidget = wbcNameWidget;
                                        makeNetworkUnSelected(listWbcNames.getWbcNameWidgets().get(0));
                                    }else{
                                        makeNetworkUnSelected(lastSelectedWbcNameWidget);
                                        lastSelectedWbcNameWidget = wbcNameWidget;
                                    }
                                    makeNetworkSelected(wbcNameWidget);
                                }
                                selectedWbcTextBox = wbcTextBox;

                            }
                        });


                    }
                    //Instead of this above loop , we can make another clickhandler for 2nd textBox, but u didnt want that i guess,
                    // And ClickHandler on Container doesnt work , because user not clicking on the container , he is clicking on textBox
                }

                if(listWbcNames.getWbcNameWidgets().size()>0){
                    if (showFiring) logger.log(Level.INFO,"WbcNamesControlPanel: widget size > 0, FIRING network changed event.");
                    WbcNameWidget wbcNameWidget = listWbcNames.getWbcNameWidgets().get(0);
                    supervisor.eventBus.fireEvent(new SelectedNetworkChangeEvent(wbcNameWidget));
//                    dashboard2.eventBus.fireEvent(new SelectedNetworkChangeEvent( listWbcNames.getWbcNameWidgets().get(0)));
                    makeNetworkSelected(listWbcNames.getWbcNameWidgets().get(0));
                }
                // THE FOLLOWING IS A TEST
                sortControl(listWbcNames);

                //TODO the WbcNameListboxWidget needs to handle setSelectedIndex and related methods, ? superclass ListBox
//                listWbcNames.setSelectedIndex(0);
            }
        });
    }

    private void makeNetworkSelected(WbcNameWidget wbcNameWidget) {
//        wbcNameWidget.removeStyleName("intravueLight");
//        wbcNameWidget.addStyleName("intravueDark");
        wbcNameWidget.getTb1().addStyleName("selectedNetwork");
        wbcNameWidget.getTb2().addStyleName("selectedNetwork");
        wbcNameWidget.getTb1().removeStyleName("intravueLight");
        wbcNameWidget.getTb2().removeStyleName("intravueLight");
        wbcNameWidget.getTb1().addStyleName("intravueDark");
        wbcNameWidget.getTb2().addStyleName("intravueDark");
    }

    private void makeNetworkUnSelected(WbcNameWidget wbcNameWidget) {
//        wbcNameWidget.addStyleName("intravueLight");
//        wbcNameWidget.removeStyleName("intravueDark");
        wbcNameWidget.getTb1().removeStyleName("selectedNetwork");
        wbcNameWidget.getTb2().removeStyleName("selectedNetwork");
        wbcNameWidget.getTb1().removeStyleName("intravueDark");
        wbcNameWidget.getTb2().removeStyleName("intravueDark");
        wbcNameWidget.getTb1().addStyleName("intravueLight");
        wbcNameWidget.getTb2().addStyleName("intravueLight");
    }

    public WbcNameListboxWidget getListWbcNames() {
        return listWbcNames;
    }

}



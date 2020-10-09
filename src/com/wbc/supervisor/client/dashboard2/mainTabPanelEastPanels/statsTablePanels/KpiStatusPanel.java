package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.dashboard2dto.DeviceInfoDTO;


import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Junaid on 11/11/2014.
 */
public class KpiStatusPanel extends Composite{
    interface KpiStatusPanelUiBinder extends UiBinder<HTMLPanel, KpiStatusPanel> {
    }

    @UiField
    Label unknown;
    @UiField
    Label ignored;
    @UiField
    Label criticalIntermittent;
    @UiField
    Label criticalAlwaysOn;
    int nwid = 1;

    private static KpiStatusPanelUiBinder ourUiBinder = GWT.create(KpiStatusPanelUiBinder.class);

    int newNetworkId=0;
    private WbcNameWidget currentNetworkWidget =null;
    boolean debug = true;
    private static Logger logger = Logger.getLogger("KpiStatusPanel.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "KpiStatusPanel", "Network Change" );


    public KpiStatusPanel() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(ourUiBinder.createAndBindUi(this));

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if (newNetwork != currentNetworkWidget) {
                    newNetworkId = newNetwork.getId();
                    currentNetworkWidget = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info( networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed." );
                        // test, show the times
                        logger.info( "TEST " + networkChangeRpcInfo.showLongTimes() );
                    }
                    networkChangeRpcInfo.setEventReceivedTime( System.currentTimeMillis());
                    getKpiInfo();
                }
            }
        });
    }

    private void getKpiInfo() {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);
        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getNetworkDeviceInfo(nwid, new AsyncCallback<ArrayList<DeviceInfoDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("fail getNetworkDeviceNamesMap" + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<DeviceInfoDTO> deviceNamesList) {
                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                updateKpiInfo(deviceNamesList);
            }
        });
    }

    private void updateKpiInfo(ArrayList<DeviceInfoDTO> deviceNamesList) {
        boolean debug = true;
        int unKnown =0;
        int ignore =0;
        int intermittent =0;
        int alwaysOn =0;

        for (DeviceInfoDTO dto : deviceNamesList) {
            if (dto.networkid != newNetworkId) continue;
            if (dto.getIpAddress().equals("Unresolved")) continue;
            if (dto.getIpAddress().substring(0,3).equals("n/a")) continue;
            int criticalType = dto.getCriticalType();
            if(criticalType ==0){
                unKnown++;
            }else if(criticalType ==1){
                ignore++;
            }else if(criticalType ==2){
                intermittent++;
            }else if(criticalType ==3){
                alwaysOn++;
            }
        }
        unknown.setText(unKnown+"");
        ignored.setText(ignore+"");
        criticalIntermittent.setText(intermittent+"");
        criticalAlwaysOn.setText(alwaysOn+"");
        if (debug) logger.config("updateKpiInfo - unknown " + unKnown + ", ignored " + ignore + ", interm. " + intermittent + " , always on " + alwaysOn);

    }
}
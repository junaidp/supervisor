package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.ConnectionDataDTO;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 10/15/2014.
 */
public class ConnectionSummary extends Composite {
    interface ConnectionSummaryUiBinder extends UiBinder<HTMLPanel, ConnectionSummary> {
    }

    @UiField
    Label verifiedConnected;
    @UiField
    Label unVerifiedConnected;
    @UiField
    Label verifiedDisconnected;
    @UiField
    Label unVerifiedDisconnected;
    @UiField
    Label totalConnected;
    @UiField
    Label totalDisconnected;
    @UiField
    Label totalVerified;
    @UiField
    Label totalNonVerified;
    @UiField
    Label total;
    @UiField
    Button btnDisconnectedReport;
    private static Logger logger = Logger.getLogger("ConnectionSummary.class");
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "ConnectionSummary", "Network Change" );


    private static ConnectionSummaryUiBinder ourUiBinder = GWT.create(ConnectionSummaryUiBinder.class);
    private WbcNameWidget currentNetwork=null;


    public ConnectionSummary() {
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        initWidget(ourUiBinder.createAndBindUi(this));
        updateConnectionData( 0,0,0,0);
        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                supervisor.logger.log(Level.INFO, "ConnectionSummaryPanel: got network change event");
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if (newNetwork != currentNetwork) {
                    supervisor.logger.log(Level.INFO, "SummaryBandwidthData: onNetworkChange: NEW network " + newNetwork.getName1());
                    currentNetwork = event.getWbcNameWidget();
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info( networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed." );
                        logger.info( "TEST " + networkChangeRpcInfo.showLongTimes() );
                    }
                    networkChangeRpcInfo.setEventReceivedTime( System.currentTimeMillis());
                    getConnectionData(currentNetwork.getId());
                } else {
                    supervisor.logger.log(Level.INFO, "SummaryBandwidthData got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });
        btnDisconnectedReport.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getMachineIpAddress();
            }
        });
     }

    public void updateConnectionData( int verifiedConnectedNo, int unVerifiedConnectedNo, int verifiedDisconnectedNo, int unVerifiedDisconnectedNo ) {
        networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
        verifiedConnected.setText(verifiedConnectedNo + "");
        unVerifiedConnected.setText(unVerifiedConnectedNo+"");
        verifiedDisconnected.setText(verifiedDisconnectedNo + "");
        unVerifiedDisconnected.setText(unVerifiedDisconnectedNo+"");
        totalConnected.setText( (verifiedConnectedNo + unVerifiedConnectedNo)+"");
        int iTotalDisconnected = (verifiedDisconnectedNo + unVerifiedDisconnectedNo);
        totalDisconnected.setText( iTotalDisconnected +"");
        totalVerified.setText( (verifiedConnectedNo +  verifiedDisconnectedNo)+"");
        totalNonVerified.setText( (unVerifiedDisconnectedNo + unVerifiedConnectedNo) +"");
        total.setText( (verifiedConnectedNo + unVerifiedConnectedNo + verifiedDisconnectedNo + unVerifiedDisconnectedNo) +"");
       if(iTotalDisconnected>0 )       {
           btnDisconnectedReport.setEnabled(true);
       }else {
           btnDisconnectedReport.setEnabled(false);
       }
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
    }


    public void getMachineIpAddress(){
        String appBase = GWT.getHostPageBaseURL();
        int beginInd = appBase.indexOf('/');
        int endInd = appBase.indexOf('8');
        String urlUsedByClient  = appBase.substring(beginInd+2, endInd-1);

//        String hostIp = (String) Cookies.getCookie("ip");
        Window.open("http://" + urlUsedByClient  + ":8765/dsbsvr/DashboardServer?report=disconnected&nwid=" + currentNetwork.getId(), "_blank", "");
    }

    private void getConnectionData(int networkId ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);
        try {
            networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
            dashboard2Service.getConnectionDataForNetwork(networkId, new AsyncCallback<ConnectionDataDTO>() {
                @Override
                public void onFailure(Throwable caught) {
                    networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                    supervisor.logger.log(Level.WARNING, "getConnectionDataForNetwork failed!");
                    updateConnectionData( 0,0,0,0);
                }

                @Override
                public void onSuccess(ConnectionDataDTO dto) {
                    // a test case to fill in initial data
                    networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                    updateConnectionData(dto.getVerifiedConnected(), dto.getUnverifiedConnected(), dto.getVerifiedDisconnected(), dto.getUnverifiedDisconnected());
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
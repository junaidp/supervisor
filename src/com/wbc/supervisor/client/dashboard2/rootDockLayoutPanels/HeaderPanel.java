package com.wbc.supervisor.client.dashboard2.rootDockLayoutPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.InternationalizationPanel;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;

import java.util.logging.Logger;


/**
 * Created by Junaid on 8/14/14.
 */
public class HeaderPanel extends VerticalPanel {

    private Label lblNetworkName = new Label("");
    private Label lblNetworkIp = new Label("");
    private HorizontalPanel hpnlHeader = new HorizontalPanel();
    private static Logger logger = Logger.getLogger("HeaderPanel.class");

    public HeaderPanel(){
        Label header = new Label(" Dashboard 2 - " + " " );

        HorizontalPanel hpnlNetwork = new HorizontalPanel();
        HorizontalPanel hpnlSpace = new HorizontalPanel();
        hpnlHeader.setStyleName("header");
        hpnlHeader.setWidth("100%");
        hpnlSpace.setWidth("200px");
        hpnlHeader.add(hpnlSpace);
        hpnlHeader.add(hpnlNetwork);
        hpnlNetwork.add(header);
        header.setStyleName("bigcenterText");
        lblNetworkName.setStyleName("bigcenterText");
        lblNetworkIp.setStyleName("bigcenterText");
        hpnlNetwork.add(lblNetworkName);
//        hpnlNetwork.add(lblNetworkIp);
        hpnlNetwork.setSpacing(10);
        hpnlNetwork.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);



        add(hpnlHeader);
        add(new MenuPanel());

        Timer minuteTimer = new Timer() {
            public void run() {
                getScannerState();
            }
        };
        minuteTimer.schedule(com.wbc.supervisor.client.dashboard2.DashboardConstants.TIMER_FETCH_SCANNERSTATE);


        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE,
                new SelectedNetworkChangeEventHandler() {
                    public void onNetworkChange(SelectedNetworkChangeEvent event) {
                        lblNetworkName.setText(" " + event.getWbcNameWidget().getTb1().getText() + ", "+ event.getWbcNameWidget().getTb2().getText());
//                        lblNetworkIp.setText(event.getWbcNameWidget().getTb2().getText());
                    }
                });

//        setWidth(Window.getClientWidth() - 20 + "px");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
               setWidth(Window.getClientWidth()-0+"px");
            }
        });
    }

    /*
    NOTE: getScannerState is being called in a timer, but the timer is part of RootDockLayout, not HeaderPanel
    This should probably change .....
     */
    public void getScannerState(){
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.getScannerState(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.info("HeaderPanel: fail: getScannerState! " + caught.getMessage() + "  cause > " + caught.getCause() );
            }

            @Override
            public void onSuccess(Integer scannerState) {
                    if(scannerState== com.wbc.supervisor.client.dashboard2.DashboardConstants.IV_STATE_INVALID){
                        hpnlHeader.setStyleName("invalid");
                    }
                    else if(scannerState== com.wbc.supervisor.client.dashboard2.DashboardConstants.IV_STATE_OFFLINE){
                        hpnlHeader.setStyleName("offline");
                    }
                    else if(scannerState== com.wbc.supervisor.client.dashboard2.DashboardConstants.IV_STATE_ONLINE){
                        hpnlHeader.setStyleName("online");
                    }
                    else if(scannerState== com.wbc.supervisor.client.dashboard2.DashboardConstants.IV_STATE_STOPPED){
                        hpnlHeader.setStyleName("stopped");
                    }
                    else if(scannerState== com.wbc.supervisor.client.dashboard2.DashboardConstants.IV_STATE_UNKNOWN){
                        hpnlHeader.setStyleName("unknown");
                    }
                hpnlHeader.add(new InternationalizationPanel(hpnlHeader.getStyleName()));
                RootPanel.get("loadingMessage").setVisible(false);
            }
        });

    }



}

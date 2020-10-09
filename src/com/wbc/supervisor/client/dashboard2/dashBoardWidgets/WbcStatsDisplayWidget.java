package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.*;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Junaid on 10/24/2014.
 */
public class WbcStatsDisplayWidget extends VerticalPanel {
    private Button btnNumOfDevices = new Button("0");
    private FocusPanel colorAndEnableControl = new FocusPanel();
    private Label nameOfStat = new Label("name");
    private String backgroundColor = "graybackground";
    WbcNameWidget currentNetwork=null;
    int currentNetworkId;
    int statIndexNumber;
    long lastThresholdMinute = 0;
    int  hoursToGraph = 8;
    // data needed to show details
    private static Logger logger = Logger.getLogger("WbcStatsDisplayWidget.class");


    public WbcStatsDisplayWidget( String name, String backgroundColor, int statIndexNumber ){
        nameOfStat.setText(name);
        this.backgroundColor = backgroundColor;
        this.statIndexNumber = statIndexNumber;
        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.setSpacing(5);
        add(mainPanel);
        mainPanel.add(colorAndEnableControl);
        mainPanel.add(nameOfStat);
        mainPanel.add(btnNumOfDevices);
        btnNumOfDevices.setWidth("60px");
        btnNumOfDevices.setHeight("25px");
        nameOfStat.setWidth("120px");// for making same size and horizontal aligned
        colorAndEnableControl.setSize("15px", "15px");
        colorAndEnableControl.setStyleName( backgroundColor );
        addStyleName("intravueMedium");
        setClickHandler();

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                boolean debug = false;
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if ( newNetwork != currentNetwork ) {
                    if (debug) logger.log(Level.INFO, "WbcStatsDisplayWidget: onNetworkChange: NEW network " + newNetwork.getName1());
                    currentNetwork = event.getWbcNameWidget();
                    currentNetworkId = currentNetwork.getId();

                } else {
                    if (debug) logger.log(Level.INFO, "WbcStatsDisplayWidget got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });

        supervisor.eventBus.addHandler(WbcGeneralEvent.TYPE,
                new WbcGeneralEventHandler() {
                    public void onValueChange(WbcGeneralEvent event) {
                        if (event.getName().equals("TimePeriodChange")) {
                            int hours = 8;
                            try {
                                hours = Integer.valueOf( event.getData());
                                hoursToGraph = hours;
                            } catch (NumberFormatException e) {
                                logger.log(Level.INFO, "format exception getting TimePeriodChange ");
                            }
                        }
                    }
                });


    }
    public void setClickHandler(){

        btnNumOfDevices.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

//                if(isNumber(btnNumOfDevices.getText())){
//                     Window.open("http://127.0.0.1:8765/dsbsvr/DashboardServer?report=statdetails&nwid="+currentNetworkId+"&stat="+statIndexNumber+"", "_blank", "");
//                }
                callDeviceStatsDetailsJsp();
            }
        });
    }

    private void callDeviceStatsDetailsJsp() {

        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.callDeviceStatsDetailsJsp(currentNetworkId, statIndexNumber, hoursToGraph, new AsyncCallback<String>(){
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(String result) {
                Window.open("DeviceStatDetails.jsp","","");
            }
        });



    }

    private boolean isNumber(String text) {
        boolean isInt = false;
        boolean isFloat = false;
        try{
            int num = Integer.parseInt(text);
            isInt = true;
        }catch (Exception ex){
            isInt = false;
        }
        try{
            float num = Float.parseFloat(text);
            isFloat = true;
        }catch (Exception ex){
            isFloat = false;
        }

        if(isInt || isFloat){
            return true;
        }

        return false;
    }

    public void setCountText( int count ) {
        btnNumOfDevices.setText( "" + count );
        if(btnNumOfDevices.getText().equals("0")) {
            btnNumOfDevices.setEnabled(false);
        }
    }
}

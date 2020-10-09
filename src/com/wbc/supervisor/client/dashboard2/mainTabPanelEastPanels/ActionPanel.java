package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.events.WbcGeneralEvent;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;

import java.util.logging.Level;

/**
 * Created by Junaid on 9/17/14.
 */
public class ActionPanel extends Composite{
    interface ActionUiBinder extends UiBinder<HorizontalPanel, ActionPanel> {
    }

    @UiField
     Label name1;
    @UiField
    Label name2;
    @UiField
    Button browseButton;
    @UiField
    HorizontalPanel hpnlTimePeriod;
    @UiField
    ListBox listHours;

    WbcNameWidget currentNetwork=null;
    @UiField
    Button test;
    private static ActionUiBinder ourUiBinder = GWT.create(ActionUiBinder.class);

    public ActionPanel() {
        //TODO make height smaller, below added but commented out after uibinder failure
        // setHeight( "100px");
        initWidget(ourUiBinder.createAndBindUi(this));
        setHandlers();

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                currentNetwork = event.getWbcNameWidget();
            }
        });

    }

    private void setHandlers() {

        listHours.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                String name = "TimePeriodChange";
                String data = listHours.getValue(listHours.getSelectedIndex());
                supervisor.eventBus.fireEvent(new WbcGeneralEvent( name, data));
            }
        });

        hpnlTimePeriod.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
        browseButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {

                launchBrowseIntravue( currentNetwork.getId() );

            }
        });
    }

    private void launchBrowseIntravue( int nwid ) {

        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.callBrowseIntravueNetwork( nwid,  new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(String showNumber ) {

                /* JUNAID

                The web page to open will be http://<the url used by client to connect, session info?>:8765/iv2/ivue.jsp?n=X where X is the showNumber

                 */

                String ipUsedByClient = "127.0.0.1" ;  // this will not normally be the case, it comes from session or is stored
                String url = "http://"+ ipUsedByClient + ":8765/iv2/ivue.jsp";
                if (showNumber.equals("0")) {
                    supervisor.logger.log(Level.INFO, "ERROR: launchBrowseIntravue has 0 as the network show number ");
                } else {
                    url = "http://"+ ipUsedByClient + ":8765/iv2/ivue.jsp?n=" + showNumber ;
                }
                Window.open( url, "", "");
            }
        });
    }
}
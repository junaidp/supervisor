package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.west;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Created by Junaid on 6/27/2015.
 */
public class BandwidthDetailControlsWest extends FlowPanel {

    public BandwidthDetailControlsWest(){
//        add(new HTML("Bandwidth Detail Controls West"));
//        addStyleName("online");
        layout();
    }

    public void layout(){
        setWidth("100%");
        ListBox listNumberOfDevices = new ListBox();
        add(listNumberOfDevices);
        listNumberOfDevices.addItem("1");
        listNumberOfDevices.addItem("2");
        listNumberOfDevices.addItem("3");

    }
}

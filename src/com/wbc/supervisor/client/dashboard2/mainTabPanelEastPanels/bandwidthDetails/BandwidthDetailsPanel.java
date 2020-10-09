package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.east.BandwidthDetailsEast;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.west.BandwidthDetailsWest;

/**
 * Created by Junaid on 6/27/2015.
 */
public class BandwidthDetailsPanel extends HorizontalPanel {

    public BandwidthDetailsPanel(){
        setSize("100%", "100%");
        add(new BandwidthDetailsWest());
        add(new BandwidthDetailsEast());
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {

                setSize("100%", "100%");
            }
        });

    }
}

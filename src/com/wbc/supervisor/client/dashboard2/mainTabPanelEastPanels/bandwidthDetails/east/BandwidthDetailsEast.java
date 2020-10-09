package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.east;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 6/27/2015.
 */
public class BandwidthDetailsEast extends VerticalPanel {

    public BandwidthDetailsEast(){
        add(new BandwidthDetailControlsEast());
        add(new ReceiveBandwidthGraph());
        add(new TransmitBandwidthGraph());
        setSize("100%", "100%");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                setSize("100%", "100%");
            }
        });
    }
}

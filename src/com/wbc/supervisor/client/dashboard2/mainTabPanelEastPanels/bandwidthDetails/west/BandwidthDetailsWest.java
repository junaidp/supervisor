package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.west;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 6/27/2015.
 */
public class BandwidthDetailsWest extends VerticalPanel {

    public BandwidthDetailsWest(){
        updatePanelSize();
        add(new BandwidthDetailControlsWest());
        add(new BandwidthDetailsIpAddresses());
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                updatePanelSize();
            }
        });
    }

    private void updatePanelSize() {
        setWidth(Window.getClientWidth()/5+"px");
//        setHeight(Window.getClientHeight()-200+"px");
    }
}

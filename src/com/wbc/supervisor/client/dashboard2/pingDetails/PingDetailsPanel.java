package com.wbc.supervisor.client.dashboard2.pingDetails;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 1/21/2015.
 */
public class PingDetailsPanel extends VerticalPanel {
    public PingDetailsEastPanel getPingDetailsEastPanel() {
        return pingDetailsEastPanel;
    }

    PingDetailsEastPanel pingDetailsEastPanel = new PingDetailsEastPanel();

    public PingDetailsPanel(){

        HorizontalPanel splitPanel = new HorizontalPanel();
        splitPanel.add(new DetailedPingControlPanel());
        splitPanel.add(pingDetailsEastPanel);       // FOR GWT CHARTS
        splitPanel.setWidth("100%");
        add(splitPanel);
        setWidth("100%");

    }
}

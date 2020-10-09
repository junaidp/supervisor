package com.wbc.supervisor.client.dashboard2.visjsCharts;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Junaid on 4/29/2015.
 */
public class NetworkVueWestPanel extends ScrollPanel {
    TextBox txtSearch = new TextBox();
    public NetworkVueWestPanel(){
        layout();
    }

    private void layout() {
        VerticalPanel vpnlMain = new VerticalPanel();
        add(vpnlMain);
        vpnlMain.add(new DeviceViewPanel());
        Label lblSearch = new Label("Search");
        HorizontalPanel hpnlSearch = new HorizontalPanel();
        hpnlSearch.add(lblSearch);
        hpnlSearch.add(txtSearch);
        vpnlMain.add(hpnlSearch);
        vpnlMain.setSpacing(3);
        hpnlSearch.setSpacing(3);
        setHeight(Window.getClientHeight() - 120 + "px");
        setAlwaysShowScrollBars(true);
        vpnlMain.setStyleName("intravueDark");
    }
}

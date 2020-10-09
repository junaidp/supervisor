package com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Created by Junaid on 12/16/2014.
 */

public class DeviceTabPanel extends TabPanel {
    private Label lblIncidents = new Label("Incidents");
    private Label lblDeviceInfo = new Label("Device Data");
    private Label lblNames = new Label("Names");
    private DeviceInfoPanel deviceInfoPanel = new DeviceInfoPanel();
    private NamesPanel namespanel = new NamesPanel();


    public void displayDeviceTabPanel() {
        lblIncidents.setWordWrap(false);
        lblDeviceInfo.setWordWrap(false);
        deviceInfoPanel.displayDeviceInfoPanel();
        namespanel.displayNamesPanel();
        add(deviceInfoPanel, lblDeviceInfo);
        add(namespanel, lblNames);
//        add(new IncidentsPanel(), lblIncidents);  to be added after version 1.0
        selectTab(0);
 }

}


package com.wbc.supervisor.client.dashboard2.visjsCharts;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 4/29/2015.
 */
public class DeviceViewPanel extends VerticalPanel {

    private Button btnIp = new Button("IP");
    private Button btnMac = new Button("MAC");
    private Button btnDevice = new Button("Device");
    private Button btnLocation = new Button("Location");
    private Button btnVendor = new Button("Vendor");
    private Button btnModel = new Button("Model");
    private Button btnVersion = new Button("Version");
    private Button btnVersionMac = new Button("Vendor-MAC");
    private String controlsWidth = "200px";



    public DeviceViewPanel(){
        layout();
    }

    private void layout() {
        Label lblViewNames = new Label("View Names");
        lblViewNames.setStyleName("boldText");
        add(lblViewNames);
        add(btnIp);
        add(btnMac);
        add(btnDevice);
        add(btnLocation);
        add(btnVendor);
        add(btnModel);
        add(btnVersion);
        add(btnVersionMac);
        btnIp.setWidth(controlsWidth);
        btnMac.setWidth(controlsWidth);
        btnDevice.setWidth(controlsWidth);
        btnLocation.setWidth(controlsWidth);
        btnVendor.setWidth(controlsWidth);
        btnModel.setWidth(controlsWidth);
        btnVersion.setWidth(controlsWidth);
        btnVersionMac.setWidth(controlsWidth);
        setSpacing(3);
    }

}

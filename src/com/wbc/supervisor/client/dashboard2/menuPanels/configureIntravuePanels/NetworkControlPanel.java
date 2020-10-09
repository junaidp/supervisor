package com.wbc.supervisor.client.dashboard2.menuPanels.configureIntravuePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by Junaid on 5/12/2015.
 */
public class NetworkControlPanel extends Composite {
    interface NetworkControlPanelUiBinder extends UiBinder<HTMLPanel, NetworkControlPanel> {
    }

    private static NetworkControlPanelUiBinder ourUiBinder = GWT.create(NetworkControlPanelUiBinder.class);

    public NetworkControlPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}
package com.wbc.supervisor.client.dashboard2.menuPanels.configureIntravuePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by Junaid on 5/12/2015.
 */
public class NetworksListPanel extends Composite {
    interface NetworksListPanelUiBinder extends UiBinder<HTMLPanel, NetworksListPanel> {
    }

    private static NetworksListPanelUiBinder ourUiBinder = GWT.create(NetworksListPanelUiBinder.class);

    public NetworksListPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}
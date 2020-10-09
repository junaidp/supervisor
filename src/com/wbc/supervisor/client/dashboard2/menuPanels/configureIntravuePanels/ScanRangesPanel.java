package com.wbc.supervisor.client.dashboard2.menuPanels.configureIntravuePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by Junaid on 5/12/2015.
 */
public class ScanRangesPanel extends Composite {
    interface ScanRangesPanelUiBinder extends UiBinder<HTMLPanel, ScanRangesPanel> {
    }

    private static ScanRangesPanelUiBinder ourUiBinder = GWT.create(ScanRangesPanelUiBinder.class);

    public ScanRangesPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}
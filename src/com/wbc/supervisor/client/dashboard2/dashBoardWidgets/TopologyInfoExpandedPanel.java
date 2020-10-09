package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.wbc.supervisor.shared.dashboard2dto.DeviceTopologyInfo;


/**
 * Created by Junaid on 3/1/2015.
 */
public class TopologyInfoExpandedPanel extends Composite {
    interface TopologyInfoExpandedPanelUiBinder extends UiBinder<HTMLPanel, TopologyInfoExpandedPanel> {
    }
    @UiField
    HTML header;
    private static TopologyInfoExpandedPanelUiBinder ourUiBinder = GWT.create(TopologyInfoExpandedPanelUiBinder.class);

    public TopologyInfoExpandedPanel(DeviceTopologyInfo topoInfo) {
        initWidget(ourUiBinder.createAndBindUi(this));
        layout(topoInfo);
    }

    private void layout(DeviceTopologyInfo topoInfo) {

        header.setHTML("100     47      0      0      1320");

        header.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    }
}
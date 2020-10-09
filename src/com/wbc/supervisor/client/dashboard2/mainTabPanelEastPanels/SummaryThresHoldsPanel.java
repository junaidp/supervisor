package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.summaryThresholds.SummaryBandwidthData;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.summaryThresholds.SummaryPingResponses;

/**
 * Created by Junaid on 10/2/14.
 */
public class SummaryThresHoldsPanel extends Composite {
    interface SummaryThresHoldsPanelUiBinder extends UiBinder<HTMLPanel, SummaryThresHoldsPanel> {
    }
    @UiField
    SummaryPingResponses summaryPingResponses;
    @UiField
    SummaryBandwidthData summaryBandwidthData;
    @UiField
    HorizontalPanel container;

    private static SummaryThresHoldsPanelUiBinder ourUiBinder = GWT.create(SummaryThresHoldsPanelUiBinder.class);

    public SummaryThresHoldsPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event){
//                container.setWidth(Window.getClientWidth()-190+"px");
                container.setSpacing(2);
                container.setWidth("100%");
                summaryPingResponses.setWidth("50%");
                summaryBandwidthData.setWidth("50%");
            }
        });
    }
}
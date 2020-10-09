package com.wbc.supervisor.client.dashboard2.menuPanels.configureIntravuePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by Junaid on 5/11/2015.
 */
public class ProductSummaryPanel extends Composite {
    interface ProductSummaryPanelUiBinder extends UiBinder<HTMLPanel, ProductSummaryPanel> {
    }

    private static ProductSummaryPanelUiBinder ourUiBinder = GWT.create(ProductSummaryPanelUiBinder.class);

    public ProductSummaryPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}
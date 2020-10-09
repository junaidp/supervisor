package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Created by Junaid on 11/11/2014.
 */
public class AlarmsAndWarningsPanel extends Composite{
    interface AlarmsAndWarningsPanelUiBinder extends UiBinder<HTMLPanel, AlarmsAndWarningsPanel> {
    }

    private static AlarmsAndWarningsPanelUiBinder ourUiBinder = GWT.create(AlarmsAndWarningsPanelUiBinder.class);
    @UiField Label ip;
    public AlarmsAndWarningsPanel() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(ourUiBinder.createAndBindUi(this));


    }
}
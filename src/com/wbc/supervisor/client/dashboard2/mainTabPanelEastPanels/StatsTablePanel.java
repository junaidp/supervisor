package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels.AlarmsAndWarningsPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels.ConnectionSummary;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels.KpiStatusPanel;

/**
 * Created by Junaid on 9/16/14.
 */
public class StatsTablePanel extends HorizontalPanel {

    public StatsTablePanel(){

        addStyleName("layoutTable");
        setWidth("100%");
        setHeight("100%");

        ConnectionSummary connectionsPanel = new ConnectionSummary();
        KpiStatusPanel kpiStatusPanel = new KpiStatusPanel();
        AlarmsAndWarningsPanel alarmsAndWarningsPanel = new AlarmsAndWarningsPanel();
        // ConnectionSummaryFlexTable connectionsPanel = new ConnectionSummaryFlexTable();

        add(connectionsPanel);
        add(kpiStatusPanel);
        add(alarmsAndWarningsPanel);


    }
}

package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.KpiDetailsPanels;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.statsTablePanels.KpiStatusPanel;

/**
 * Created by Junaid on 4/7/2015.
 */
public class KpiTopPanel extends HorizontalPanel {

    public KpiTopPanel(){

        add(new KpiControlPanel());
        add(new KpiPanelStatsTable());
        add(new KpiStatusPanel());
        setStyleName("layoutTable");
        setSpacing(5);
        setStyleName("intravueMedium");
    }
}

package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.KpiDetailsPanels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.TimeBasedPanels.StatsBarChartControlPanel;

/**
 * Created by Junaid on 1/22/2015.
 */
public class KpiDetails  extends VerticalPanel {

    public KpiDetails(){
//        setSize("100%", Window.getClientHeight()-100+"px");
        setWidth("100%");// not setting up height as we have setup the height background color in main panel
        VerticalPanel containerKpiStats = new VerticalPanel();
//        KpiPanelStatsTable kpiPanelStatsBar = new KpiPanelStatsTable();
        addStyleName("intravueLight");
        KpiTopPanel kpiTopPanel = new KpiTopPanel();
        containerKpiStats.add(kpiTopPanel);
        containerKpiStats.setSize("100%", "250px");
        add(containerKpiStats);
        containerKpiStats.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        containerKpiStats.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        StatsBarChartControlPanel leftPanel = new StatsBarChartControlPanel();
        TimebasedStatsBarchartPanelForKpi timebasedStatsBarchartPanelForKpi = new TimebasedStatsBarchartPanelForKpi( leftPanel );
        add(timebasedStatsBarchartPanelForKpi);
    }
}

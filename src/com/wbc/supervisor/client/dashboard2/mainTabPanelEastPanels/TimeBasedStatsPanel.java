package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.TimeBasedPanels.StatsBarChartControlPanel;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.TimeBasedPanels.TimebasedStatsBarchartPanel;

/**
 * Created by Junaid on 8/14/14.
 */
public class TimeBasedStatsPanel extends HorizontalPanel {



    public TimeBasedStatsPanel() {


        StatsBarChartControlPanel leftPanel = new StatsBarChartControlPanel();
        TimebasedStatsBarchartPanel rightPanel = new TimebasedStatsBarchartPanel( leftPanel );

        add(leftPanel);
        add(rightPanel);
        setSpacing(2);
//        leftPanel.setWidth("150px");
//        setStyleName("intravueDark");
//    setVisible(false);
//        setHeight(Window.getClientHeight()+"px");
//        Window.addResizeHandler(new ResizeHandler(){
//            @Override
//            public void onResize(ResizeEvent event) {
//                setHeight(Window.getClientHeight()-10+"px");
//            }
//        });
    }

}
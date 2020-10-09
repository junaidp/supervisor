package com.wbc.supervisor.client.dashboard2.mainPanelPanels;


import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo.DeviceTabPanel;
import com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo.TopologyInfoPanel;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.ChangedPanel;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.LoadingPanel;
import com.wbc.supervisor.client.dashboard2.events.AllMainPanelsLoadedEvent;
import com.wbc.supervisor.client.dashboard2.events.AllMainPanelsLoadedEventHandler;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.AlarmsNWarningDetails;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.KpiDetailsPanels.KpiDetails;
import com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.BandwidthDetailsPanel;
import com.wbc.supervisor.client.dashboard2.pingDetails.PingDetailsPanel;
import com.wbc.supervisor.client.dashboard2.visjsCharts.NetworkTopologyChart;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;

import java.util.logging.Level;
import java.util.logging.Logger;
//LOG4J imports
//import org.apache.log4j.Logger;
//import org.apache.log4j.MDC;
//import org.apache.log4j.NDC;


/**
 * Created by Junaid on 8/13/14.
 */
public class EastPanel extends VerticalPanel {

    private MainEastPanel mainEastPanel;
    private static Logger logger = Logger.getLogger("MainEastPanel.class");
    private  int currentNetworkId = 0;
    private boolean networkTopologyChartLoaded = false;
    private VerticalPanel mainPanel = new VerticalPanel();
    private boolean debugStartup = true;
    boolean disableNetworkVue = false;

    public EastPanel(){
        if (debugStartup) supervisor.logger.log(Level.ALL, "DebugStartup: EastPanel ctor start");
        mainEastPanel = new MainEastPanel();
        mainPanel.add(new LoadingPanel());
        add(layout());
        setWidth("100%");
        addStyleName("intravueLight");
        if (debugStartup) supervisor.logger.log(Level.ALL, "DebugStartup: EastPanel ctor end");
    }

    public TabPanel layout() {
        boolean disableVisjs = false;
        final com.wbc.supervisor.client.dashboard2.AppConstants constantsI8n=(com.wbc.supervisor.client.dashboard2.AppConstants)GWT.create(com.wbc.supervisor.client.dashboard2.AppConstants.class);
        final TabPanel panel = new TabPanel();
        panel.setWidth("90%");
        logger.log(Level.INFO,"//////////////////////////We are in East Panel Layout///////////////////////");
        Label lbl = new Label(constantsI8n.MAIN());


        if (debugStartup) logger.log(Level.ALL, "DebugStartup: EastPanel layout, new DeviceTabPanel ");
        final DeviceTabPanel deviceTabPanel = new DeviceTabPanel();
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: EastPanel layout, new PingDetailsPanel ");
        final PingDetailsPanel pingDetailsPanel = new PingDetailsPanel();
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: EastPanel layout, new KpiDetails ");
        final KpiDetails kpiDetails = new KpiDetails();
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: EastPanel layout, new BandwidthDetails ");
        final BandwidthDetailsPanel bandwidthDetails = new BandwidthDetailsPanel();
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: EastPanel layout, new AlarmsNWarningDetails ");
        final AlarmsNWarningDetails alarmsNWarningDetails = new AlarmsNWarningDetails();

        final Label lblTopologyInfo = new Label("Topology Info");
        final Label lblDeviceInfo = new Label("Device Info");
        final Label lblPingDetails = new Label("Ping Details");
        final Label lblKpiDetails = new Label("Kpi Details");
        final Label lblAlarmsNWarningDetails = new Label("Alarms and Warnings Details");
        final Label lblBandwidthDetails = new Label("Bandwidth Details");

        lblTopologyInfo.setWordWrap(false);
        lblDeviceInfo.setWordWrap(false);
        lblPingDetails.setWordWrap(false);
        lblKpiDetails.setWordWrap(false);
        lblAlarmsNWarningDetails.setWordWrap(false);
        lblBandwidthDetails.setWordWrap(false);


        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                supervisor.logger.log(Level.INFO, "EastPanel: onNetworkChange: NEW network " + newNetwork.getName1());
                currentNetworkId = newNetwork.getId();
                mainPanel.clear();
                mainPanel.add(new LoadingPanel());
            }
        });

        supervisor.eventBus.addHandler(AllMainPanelsLoadedEvent.TYPE, new AllMainPanelsLoadedEventHandler() {
            @Override
            public void onMainPanelLoaded(AllMainPanelsLoadedEvent event) {
                mainPanel.clear();
                mainPanel.add(mainEastPanel);
            }
        });

        deviceTabPanel.displayDeviceTabPanel();
        panel.add(mainPanel, lbl);
        panel.setWidth("100%");
        if (!disableVisjs) {
            final TopologyInfoPanel topologyInfoPanel = new TopologyInfoPanel();
            topologyInfoPanel.displayTopologyInfoPanel();
            panel.add(topologyInfoPanel, lblTopologyInfo);
        }
        panel.add(deviceTabPanel, lblDeviceInfo);
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: EastPanel layout, new NetworkTopologyChart ");
        final NetworkTopologyChart networkTopologyChart =  new NetworkTopologyChart();
        supervisor.logger.log(Level.ALL, "NOT CALLING OR INITIALIZING NETWORK PANEL IN EastPanel JWM ");
        if (!disableNetworkVue) {
            panel.add(networkTopologyChart, constantsI8n.VISJS_CHARTS());
        }
        panel.add(pingDetailsPanel, lblPingDetails);
        panel.add(bandwidthDetails, lblBandwidthDetails);
        panel.add(kpiDetails, lblKpiDetails);
        panel.add(new ChangedPanel(), "Changed panel test");
//        panel.add(alarmsNWarningDetails, lblAlarmsNWarningDetails);


//        dashboard2.eventBus.addHandler(MainPanelLoadedEvent.TYPE, new MainPanelLoadedEventHandler() {
//            @Override
//            public void onMainPanelLoaded(MainPanelLoadedEvent event) {
//                loadAllTabs(topologyMainPanel, deviceTabPanel);
//            }
//        });

        mainEastPanel.setVisible(true);

        panel.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {

                if (!disableNetworkVue) {
                    if (event.getSelectedItem() == 3 && currentNetworkId != 0 && !networkTopologyChartLoaded) {
                        networkTopologyChart.createNetworkTopologyChart(currentNetworkId);//To Display charts we need to call NetworkTopology here on selection of tab, otherwise chart wont appear
                        networkTopologyChartLoaded = true;
                    } else {
                        supervisor.logger.log(Level.INFO, "EastPanel: not calling networkTopologyChart.createNetworkTopologyChart, current network id " + currentNetworkId + ", topo chart loaded " + networkTopologyChartLoaded);
                    }
                }

                ///
                if(event.getSelectedItem() == 4 && currentNetworkId != 0){
                    pingDetailsPanel.getPingDetailsEastPanel().fetchDataIfDataNotFetchedForLast2Minutes();
                } else {
                    supervisor.logger.log(Level.INFO, "EastPanel: not calling networkTopologyChart.createNetworkTopologyChart, current network id " + currentNetworkId + ", topo chart loaded " + networkTopologyChartLoaded);
                }

            }
        });
         panel.selectTab(0);
         return panel;
    }

}

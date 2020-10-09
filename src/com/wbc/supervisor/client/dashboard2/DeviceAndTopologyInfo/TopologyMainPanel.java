package com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Junaid on 2/22/2015.
 */
public class TopologyMainPanel extends HorizontalPanel {

    public TopologyMainPanel(){
        TopologyInfoPanel topologyInfoPanel = new TopologyInfoPanel();
        topologyInfoPanel.displayTopologyInfoPanel();
        setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        add(topologyInfoPanel);
        add(new ExpandedTopologyInfoPanel());
        setStyleName("intravueLight");
    }
}

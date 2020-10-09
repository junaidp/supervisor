package com.wbc.supervisor.client.dashboard2.mainPanelPanels;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.WbcNamesControlPanel;


/**
 * Created by Junaid on 8/13/14.
 */


public class WestPanel extends VerticalPanel {

    private WbcNamesControlPanel wbcNamesControlPanel;


    public WestPanel(){
        wbcNamesControlPanel = new WbcNamesControlPanel();
        add(wbcNamesControlPanel);
        setWidth("100%");
     }
}

package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Junaid on 2/8/2015.
 */


public class LoadingPanel extends HorizontalPanel {

    public LoadingPanel(){
        VerticalPanel mainPanel = new VerticalPanel();
        Image imgLoading = new Image("images/loading.gif");
        Label lblLoading = new Label("Loading...");
        mainPanel.add(imgLoading);
        mainPanel.add(lblLoading);
        setSize("100%", Window.getClientHeight() - 150 + "px");
        setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        add(mainPanel);
        setSpacing(3);


    }

}


package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Created by Junaid on 7/1/2015.
 */
public class LegendWidget extends HorizontalPanel {

    public LegendWidget(String imageUrl, String desc){

        add(new Image(imageUrl));
        add(new Label(desc));
        setSpacing(5);

    }
}

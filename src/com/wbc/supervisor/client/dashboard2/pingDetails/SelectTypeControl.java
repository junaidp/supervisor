package com.wbc.supervisor.client.dashboard2.pingDetails;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Created by Junaid on 2/8/2015.
 */
public class SelectTypeControl extends HorizontalPanel {

    private ListBox listSelectType = new ListBox();

    public SelectTypeControl(){

        listSelectType.addItem("Simple Average", "savg");
        listSelectType.addItem("Mean Average"   , "mavg");
        listSelectType.addItem("Max Ping"       , "max");
        listSelectType.addItem("Std. Dev"       , "stddev");

        Label lblSelectType = new Label("Select Type");
        setSpacing(3);

        add(lblSelectType);
        add(listSelectType);
    }

    public ListBox getListSelectType() {
        return listSelectType;
    }

    public void setListSelectType(ListBox listSelectType) {
        this.listSelectType = listSelectType;
    }

}

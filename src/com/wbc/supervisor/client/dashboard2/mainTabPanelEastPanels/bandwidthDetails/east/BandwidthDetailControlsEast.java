package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.bandwidthDetails.east;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Junaid on 6/27/2015.
 */
public class BandwidthDetailControlsEast extends HorizontalPanel {

    public BandwidthDetailControlsEast(){

//        add(new HTML("Bandwidth Details ControlsEast"));
        addStyleName("invalid");
        layout();
    }

    public void layout(){
        Label text = new Label();
        text.setText("Devices to display");
        text.getElement().getStyle().setDisplay(Style.Display.BLOCK);
        add(text);
        setSpacing(5);

        ListBox listNumberOfDevices = new ListBox();
        add(listNumberOfDevices);
        listNumberOfDevices.addItem("1");
        listNumberOfDevices.addItem("2");
        listNumberOfDevices.addItem("3");

        text = new Label();
        text.setText("Select type");
        add(text);

        ListBox listOfTypes = new ListBox();
        add(listOfTypes);
        listOfTypes.addItem("1");
        listOfTypes.addItem("2");
        listOfTypes.addItem("3");

        text = new Label();
        text.setText("Time of last update");
        add(text);


        text = new Label();
        text.setText("4/5/2020 11:32 am");
        add(text);

    }
}

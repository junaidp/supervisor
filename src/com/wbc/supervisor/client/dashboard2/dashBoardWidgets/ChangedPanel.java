package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 6/23/2015.
 */
public class ChangedPanel extends VerticalPanel {

    public ChangedPanel(){
        // Change this to a dropdown list
        // items in list are IP Address, Mac Address, Name, Location, Vendor, Model, Version, User Defined 1, User Defined 2, User Defined 3
        // Default IP Address
        ListBox lstIpAddress = new ListBox();
        lstIpAddress.addItem("IP Address");
        lstIpAddress.addItem("Mac Address");
        lstIpAddress.addItem("Name");
        lstIpAddress.addItem("Location");
        lstIpAddress.addItem("Vendor");
        lstIpAddress.addItem("Model");
        lstIpAddress.addItem("Version");
        lstIpAddress.addItem("Ud1");
        lstIpAddress.addItem("Ud2");
        lstIpAddress.addItem("Ud3");

        // Change this to a dropdown list
        // items in list are Empty, IP Address, Mac Address, Name, Location, Vendor, Model, Version, User Defined 1, User Defined 2, User Defined 3
        // Default Empty
        ListBox lstModel = new ListBox();
        lstModel.addItem("IP Address");
        lstModel.addItem("Mac Address");
        lstModel.addItem("Name");
        lstModel.addItem("Location");
        lstModel.addItem("Vendor");
        lstModel.addItem("Model");
        lstModel.addItem("Version");
        lstModel.addItem("Ud1");  //NOT sure if we need to put these in the listBox , or there relative values from db here.
        lstModel.addItem("Ud2");  //If we need to put the related actual value from db here , let me know which rpc shold i use
        lstModel.addItem("Ud3");
        // all of these are a legend to read the NetworkVUE symbols
        // the 'look' is an image on the left and a description on the right
        // the images are the images which would be used if/when the NetworkVUE changes to use images instead of ellipse, triangle, rectangle, etc.

        LegendWidget legendWidgetWap = new LegendWidget("images/refresh.png", "Wap"); // we will be putting image Url here, jjust putting one as an example
        LegendWidget legendWidgetOfficeDev = new LegendWidget("", "Office Dev");
        LegendWidget legendWidgetSwitch = new LegendWidget("", "Switch");
        LegendWidget legendWidgetAutomation = new LegendWidget("", "Automation");
        LegendWidget legendWidgetDevice = new LegendWidget("", "Device");
        LegendWidget legendWidgetVm = new LegendWidget("", "Vm");
        LegendWidget legendWidgetPlc = new LegendWidget("", "PLC");


        add(lstIpAddress);
        add(lstModel);
        add(legendWidgetWap);
        add(legendWidgetOfficeDev);
        add(legendWidgetSwitch);
        add(legendWidgetAutomation);
        add(legendWidgetDevice);
        add(legendWidgetVm);
        add(legendWidgetPlc);
        setSpacing(5);




    }

}

package com.wbc.supervisor.client.dashboard2.wbcwidgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Created by Junaid on 1/22/2015.
 */
public class WbcNameWidgetOneLiner extends FocusPanel {
    private FocusPanel colorAndEnableControl = new FocusPanel();

    public FocusPanel getColorAndEnableControl() {
        return colorAndEnableControl;
    }

    private String name;
    private String ip;
    private Label lbl_Ip ;
    private Label lbl_Name;
    private Label lbl_Separator;
    private HorizontalPanel ipNameContainer = new HorizontalPanel();


    public WbcNameWidgetOneLiner(String ip, String name, String backgroundColor){
        HorizontalPanel hpnlMain = new HorizontalPanel();
        colorAndEnableControl.setSize("15px", "15px");
        colorAndEnableControl.setStyleName( backgroundColor );
        this.name = name;
        this.ip = ip;
        lbl_Ip = new Label(this.ip);
        lbl_Name = new Label(this.name);
        lbl_Separator = new Label(" - ");
        hpnlMain.add(colorAndEnableControl);
        hpnlMain.add(ipNameContainer);
        add(hpnlMain);

    }



    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "WbcNameWidgetOneLiner{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

    public void setNameFirst(){
        ipNameContainer.add(lbl_Name);
        ipNameContainer.add(lbl_Separator);
        ipNameContainer.add(lbl_Ip);
    }

    public void setIpFirst(){
        ipNameContainer.add(lbl_Ip);
        ipNameContainer.add(lbl_Separator);
        ipNameContainer.add(lbl_Name);
    }
}

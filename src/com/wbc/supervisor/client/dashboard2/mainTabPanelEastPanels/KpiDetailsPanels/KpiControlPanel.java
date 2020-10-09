package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.KpiDetailsPanels;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.dashboard2.events.WbcGeneralEvent;
import com.wbc.supervisor.client.dashboard2.events.WbcGeneralEventHandler;

/**
 * Created by Junaid on 4/7/2015.
 */
public class KpiControlPanel extends VerticalPanel {

    SimpleRadioButton radioHours = new SimpleRadioButton("group");
    SimpleRadioButton radioYesterday = new SimpleRadioButton("group");
    Label lblHours = new Label("08 ");

    Label lblYesterday = new Label("Yesterday");



    public KpiControlPanel(){
        setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        radioHours.setValue(true);
        Label lblHoursText = new Label("Hours");
        VerticalPanel vpnlSpace = new VerticalPanel();
        vpnlSpace.setHeight("30px");
        HorizontalPanel hpnlHours = new HorizontalPanel();
        HorizontalPanel hpnlYesterday = new HorizontalPanel();
        hpnlHours.add(radioHours);
        hpnlHours.add(lblHours);
        hpnlHours.add(lblHoursText);
        hpnlHours.setSpacing(2);
        hpnlYesterday.setSpacing(2);
        hpnlYesterday.add(radioYesterday);
        hpnlYesterday.add(lblYesterday);

        add(vpnlSpace);
        add(hpnlHours);
        add(hpnlYesterday);

        supervisor.eventBus.addHandler(WbcGeneralEvent.TYPE,
                new WbcGeneralEventHandler() {
                    public void onValueChange(WbcGeneralEvent event) {
                        if (event.getName().equals("TimePeriodChange")) {
                            lblHours.setText(event.getData());
                        }
                    }
                });

        radioHours.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                String name = "KpiPanelTimePeriodChange";
                String data = "hours";
                supervisor.eventBus.fireEvent(new WbcGeneralEvent(name, data));
            }
        });

        radioYesterday.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                String name = "KpiPanelTimePeriodChange";
                String data = "yesterday";
                supervisor.eventBus.fireEvent(new WbcGeneralEvent(name, data));
            }
        });

    }

    public SimpleRadioButton getRadioHours() {
        return radioHours;
    }

    public void setRadioHours(SimpleRadioButton radioHours) {
        this.radioHours = radioHours;
    }

    public SimpleRadioButton getRadioYesterday() {
        return radioYesterday;
    }

    public void setRadioYesterday(SimpleRadioButton radioYesterday) {
        this.radioYesterday = radioYesterday;
    }
}

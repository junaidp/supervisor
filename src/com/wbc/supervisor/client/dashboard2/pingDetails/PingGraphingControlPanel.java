package com.wbc.supervisor.client.dashboard2.pingDetails;

import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.dashboard2.AppConstants;
import com.wbc.supervisor.client.dashboard2.DashboardConstants;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.LoadingPanel;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.LoadingPanel;

/**
 * Created by Junaid on 2/8/2015.
 */
public class PingGraphingControlPanel extends HorizontalPanel {

    private ListBox listMaxNumbers = new ListBox();
    private Label lblLoading = new Label("Loading...");
    private Image updateNow = new Image("images/refresh.png");
    private Label timeOfLastUpdate = new Label();


    public ListBox getListSelectType() {
        return listSelectType;
    }

    public LoadingPanel getLoading() {
        return loading;
    }

    private ListBox listSelectType = new ListBox();
    private LoadingPanel loading = new LoadingPanel();
    public Label getTimeOfLastUpdate() {
        return timeOfLastUpdate;
    }

    public Image getUpdateNow() {
        return updateNow;
    }

    public void setUpdateNow(Image updateNow) {
        this.updateNow = updateNow;
    }

    public PingGraphingControlPanel(){
        loading.setVisible(false);
        Label lblMaxNumbers = new Label(com.wbc.supervisor.client.dashboard2.DashboardConstants.DEVICES_TO_GRAPH);
        Label lblTimeofLastUpdate = new Label(com.wbc.supervisor.client.dashboard2.DashboardConstants.TIME_OF_LAST_UPDATE);
        Label lblSelectType = new Label(com.wbc.supervisor.client.dashboard2.DashboardConstants.SELECT_TYPE);
        lblLoading.setVisible(false);
        add(lblLoading);
        add(lblMaxNumbers);
        add(listMaxNumbers);
        setSpacing(3);
        add(lblSelectType);
        add(listSelectType);
        add(updateNow);
        add(lblTimeofLastUpdate);
        add(timeOfLastUpdate);

        lblMaxNumbers.setStyleName("blueText");
        lblSelectType.setStyleName("blueText");
        lblTimeofLastUpdate.setStyleName("blueText");

        listMaxNumbers.addItem("1");
        listMaxNumbers.addItem("2");
        listMaxNumbers.addItem("5");
        listMaxNumbers.addItem("10");
        listMaxNumbers.addItem("15");
        listMaxNumbers.addItem("20");
        listMaxNumbers.setSelectedIndex(1);
        updateNow.setStyleName("point");

        listSelectType.addItem("Simple Average" , "savg");
        listSelectType.addItem("Mean Average"   , "mavg");
        listSelectType.addItem("Max Ping"       , "max");
        listSelectType.addItem("Std. Dev"       , "stddev");


        setSpacing(5);


        final com.wbc.supervisor.client.dashboard2.AppConstants constantsI8n=(com.wbc.supervisor.client.dashboard2.AppConstants) com.google.gwt.core.shared.GWT.create(com.wbc.supervisor.client.dashboard2.AppConstants.class);
        String updateTitle = new String(constantsI8n.UPDATE_NOW());
        updateNow.setTitle(updateTitle);


    }


    public ListBox getListMaxNumbers() {
        return listMaxNumbers;
    }

    public void setListMaxNumbers(ListBox listMaxNumbers) {
        this.listMaxNumbers = listMaxNumbers;
    }

    public Label getLblLoading() {
        return lblLoading;
    }

    public void setLblLoading(Label lblLoading) {
        this.lblLoading = lblLoading;
    }

}
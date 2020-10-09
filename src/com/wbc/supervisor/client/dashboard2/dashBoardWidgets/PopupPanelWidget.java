package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class PopupPanelWidget {

    DecoratedPopupPanel popup ;
    VerticalPanel vpnlMain;
    private HorizontalPanel hpnlSPace;

    public HorizontalPanel getHpnlSPace() {
        return hpnlSPace;
    }

    public void setHpnlSPace(HorizontalPanel hpnlSPace) {
        this.hpnlSPace = hpnlSPace;
    }

    Image close = new Image("close.jpg");

    public PopupPanelWidget(Widget widget) {
        HorizontalPanel hpnlClose = new HorizontalPanel();
        hpnlSPace = new HorizontalPanel();
        hpnlSPace.setWidth("850px");
        hpnlClose.add(hpnlSPace);
        hpnlClose.add(close);
        close.setStyleName("point");
        popup = new DecoratedPopupPanel();
        vpnlMain = new VerticalPanel();
        vpnlMain.add(hpnlClose);
        vpnlMain.add(widget);
//		vpnlMain.setSize("800px","425px");
        popup.setWidget(vpnlMain);
        vpnlMain.setWidth("800px");

        popup.setStyleName("intravueMedium");
        popup.setGlassEnabled(true);
        popup.center();

        close.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent arg0) {
                popup.removeFromParent();
            }});
    }

    public DecoratedPopupPanel getPopup() {
        return popup;
    }


    public void setPopup(DecoratedPopupPanel popup) {
        this.popup = popup;
    }


    public Image getClose() {
        return close;
    }


    public void setClose(Image close) {
        this.close = close;
    }

    public VerticalPanel getVpnlMain() {
        return vpnlMain;
    }

    public void setVpnlMain(VerticalPanel vpnlMain) {
        this.vpnlMain = vpnlMain;
    }





}


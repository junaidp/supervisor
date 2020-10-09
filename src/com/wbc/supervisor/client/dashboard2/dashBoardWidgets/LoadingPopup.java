package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 1/13/2015.
 */
public class LoadingPopup {

    private DecoratedPopupPanel popupLoading;

    public void display(){
    popupLoading = new DecoratedPopupPanel ();
    popupLoading.setSize("100%", "100%");
    VerticalPanel vpnlLoad = new VerticalPanel();
    vpnlLoad.setSize("20px", "20px");
    vpnlLoad.add(new Image("images/loading.gif"));
    popupLoading.setWidget(new Image("images/loading.gif"));
    popupLoading.setStyleName("whitesmokeBackground");
//        popupLoading.setPopupPosition(200,210);
//        popupLoading.setGlassEnabled(true);

        popupLoading.center();
}


    public void remove(){
        popupLoading.removeFromParent();
    }


}

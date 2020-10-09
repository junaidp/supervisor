package com.wbc.supervisor.client.dashboard2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.wbc.supervisor.client.dashboard2.rootDockLayoutPanels.HeaderPanel;


/**
 * Created by Junaid on 8/14/14.
 */
public class RootDockLayout extends Composite  {

    interface MainViewUiBinder extends UiBinder<DockLayoutPanel, RootDockLayout> {
    }
    private static MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);


    @UiField
    HeaderPanel headerPanel;

    public RootDockLayout() {
        initWidget(ourUiBinder.createAndBindUi(this));

        //TODO Junaid, wouldn't it be more obvious what is happening to put the timer in the headerPanel rather than call a method in there from some other class ?
       //Moved.. Thanks for this , Dont know what i thought at the time of placing that timer here..

    }


}
package com.wbc.supervisor.client.dashboard2.mainPanelPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 8/14/14.
 */
public class MainEastPanel extends Composite{
    interface MainEastPanelUiBinder extends UiBinder<VerticalPanel, MainEastPanel> {
    }
    private static MainEastPanelUiBinder ourUiBinder = GWT.create(MainEastPanelUiBinder.class);

    @UiField
    VerticalPanel mainPanel;

    public MainEastPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        mainPanel.setHeight(Window.getClientHeight()-200+"px");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                mainPanel.setHeight(Window.getClientHeight()-200+"px");
            }
        });
    }




}
package com.wbc.supervisor.client.dashboard2.menuPanels.configureIntravuePanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by Junaid on 5/11/2015.
 */
public class ConfigureIntravue extends Composite {
    interface ConfigureIntravueUiBinder extends UiBinder<HTMLPanel, ConfigureIntravue> {
    }

    private static ConfigureIntravueUiBinder ourUiBinder = GWT.create(ConfigureIntravueUiBinder.class);

    public ConfigureIntravue() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}
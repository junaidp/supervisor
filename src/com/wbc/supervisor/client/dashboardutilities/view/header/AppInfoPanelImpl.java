package com.wbc.supervisor.client.dashboardutilities.view.header;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class AppInfoPanelImpl implements IsWidget, AppInfoPanel
{
	private final static String APP_NAME = "WBC-Utilities for IntraVUE";
	// private final static String APP_VERSION = "0.9.200415-1 Beta";
	private String selectedUtility = "";

	@Override
	public Widget asWidget()
	{
		return layout();
	}

	private Widget layout()
	{
		HorizontalPanel container = new HorizontalPanel();
		container.add( new HTML( APP_NAME ) );
		container.add( new HTML(Constants.APP_VERSION) );
		container.add( new HTML(selectedUtility) );
		return container;
	}

	@Override
	public String getAppInfo()
	{
		String info = APP_NAME + " - " + Constants.APP_VERSION +" " +selectedUtility+ " ";
		return info;
	}

	@Override
	public void setSelectedUtility( String selectedUtility )
	{
		this.selectedUtility = selectedUtility;
	}

}

package com.wbc.supervisor.client.dashboardutilities.view.header;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;


public class AppBrandingPanelImpl implements IsWidget, AppBrandingPanel
{

	private final static String WBC_logo_FILE = "images/WBC-INS-Logo.png";
	@Override
	public Widget asWidget()
	{
		Image img = new Image(WBC_logo_FILE);
		return img;
	}

	@Override
	public Image getLogo()
	{
		return new Image(WBC_logo_FILE);
	}
}

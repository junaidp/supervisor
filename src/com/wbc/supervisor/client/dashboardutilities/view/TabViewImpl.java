package com.wbc.supervisor.client.dashboardutilities.view;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

//Created in begining thinking it will be tab application , Leaving it now , Just in case we will need tabs in our app later.
public abstract class TabViewImpl extends VerticalLayoutContainer
{

	public TabViewImpl()
	{
		TabPanel tab = new TabPanel();
		TabItemConfig config = new TabItemConfig();
		config.setHTML( "Dashboard" );
		tab.add( getDashBoard(), config );
		add( tab );
	}

	public abstract Widget getDashBoard();

}

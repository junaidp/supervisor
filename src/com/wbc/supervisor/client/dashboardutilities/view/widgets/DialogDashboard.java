package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;

public class DialogDashboard extends Dialog
{

	public DialogDashboard( Widget widget, String heading)
	{
		setSize( "500px", "500px" );
		setWidget( widget );
		setHeading( heading );
	}

	public DialogDashboard( Widget widget, String heading, int width, int height)
	{
		setSize( width+"px", height+"px" );
		setWidget( widget );
		setHeading( heading );

	}

	public void go()
	{
		show();
	}

}

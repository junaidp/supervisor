package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class MenuManual extends MenuBase implements IsWidget
{


	@Override
	public Widget asWidget() {
		container.clear();
		container.add( layout());
		return super.asWidget();

	}

	public MenuManual()
	{

	}

	public Widget layout()
	{
		Frame frame = new Frame("help/wbcutilHelp.htm");
		setSize( frame );
		frame.addStyleName( "frame" );
		Window.addResizeHandler( event -> setSize( frame ) );
		return frame;
	}

	private void setSize( Frame frame )
	{
		frame.setSize( "100%", Window.getClientHeight()-200+"px" );
	}
}

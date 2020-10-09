package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.google.gwt.user.client.rpc.IsSerializable;

public class MenuItemDashboard extends MenuItem implements IsSerializable
{
	public MenuItemDashboard(String text, String id, MenuItem... menuItems ){

		setText( text );
		setId( id );
		if(menuItems != null)
		{
			Menu menu = new Menu();
			setSubMenu( menu );
			for ( MenuItem menuItem : menuItems )
			{
				menu.add( menuItem );
			}
		}

	}

	public MenuItemDashboard()
	{

	}
}

package com.wbc.supervisor.client.dashboardutilities.view.menus;

public enum MenuEnum
{
	CONNECTION("Connection", "0", new MenuItemDashboard());

	String name;
	String id;
	MenuItemDashboard menuItem;

	 MenuEnum(String name, String id, MenuItemDashboard menuItem){


	}
}

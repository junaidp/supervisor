package com.wbc.supervisor.client.dashboardutilities.view.header;

import com.wbc.supervisor.client.dashboardutilities.DashboardUtilMessages;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.login.UserPanel;
import com.wbc.supervisor.client.dashboardutilities.view.menus.about.MenuAbout;
import com.wbc.supervisor.client.dashboardutilities.view.menus.status.MenuStatus;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.login.ConnectionDialog;
import com.wbc.supervisor.client.dashboardutilities.view.login.LoginPanel;
import com.wbc.supervisor.client.dashboardutilities.view.menus.*;
import com.wbc.supervisor.client.dashboardutilities.view.menus.kpimanagement.MenuKPIManagement;
import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.MenuSwitchProbe;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.logging.client.SimpleRemoteLogHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.*;

import java.util.Date;

public class HeaderPanelImpl extends VerticalLayoutContainer implements HeaderPanel
{

	 private AppBrandingPanel appBrandingPanel = new AppBrandingPanelImpl();
	 private AppInfoPanel appInfoPanel = new AppInfoPanelImpl();
	 private MenuBarItem home = null;
	 private MenuBarItem settings = null;
	 private MenuBarItem utilities = null;
	 private MenuBarItem help = null;
	 private Menu subMenuUtilities = null;
	 private VerticalLayoutContainer centerContainer;
	 private MenuBase menu = null;
	 private ContentPanel headerPanel = null;
	 private LoginPanel loginPanel = null;
	 private MenuSwitchProbe menuSwitchProbe = null;
	 private MenuCleanDatabase menuCleanDatabase = null;
	 private MenuThresholdAnalysis menuThresholdAnalysis = null;
	 private MenuKPIManagement kpiManagement = null;
	 private MenuConnectionReport menuConnectionReport;
	 private MenuDisconnectedStatisticReport menuDisconnectedStatisticReport;
	 private MenuManual menuManual;
	 private MenuAbout menuAbout;
	 private MenuStatus menuStatus;
	 private String selectedMenu = "";
	 private HTML currentIpAndServerHost = new HTML("Not Connected");
	 private SimpleRemoteLogHandler remoteLog = new SimpleRemoteLogHandler();



	private DashboardUtilMessages messages =
			GWT.create(DashboardUtilMessages.class);
	 private String url = GWT.getModuleBaseURL().substring( 0, GWT.getModuleBaseURL().length()-1);


	public HeaderPanelImpl( VerticalLayoutContainer centerContainer )
	{
		this.centerContainer = centerContainer;
		init();
	}

	 private void init()
	 {
		 add(layout());
		 display( getLogin(false) );
		 updateCurrentIpAndServerHost(false);

	 }

	 private void display( MenuBase menu)
	 {
	 	 centerContainer.clear();
		 centerContainer.add( menu );
	 }
	 private Widget layout()
	{
		url = url+".html";

		subMenuUtilities = new Menu();
		Menu subMenuItemHelp = new Menu();
		Menu subMenuItemSettings = new Menu();
		Menu subMenuItemLanguage = new Menu();
		Menu subMenuItemNone = new Menu();
		//subMenuItemNone.setVisible( false );

		MenuItem menuCRC = new MenuItem( "CRC Report" );
		MenuItem menuIfInError = new MenuItem( "Input Errors Report" );

		subMenuUtilities.add( menuCRC );

		subMenuUtilities.add( menuIfInError );
		subMenuUtilities.add( new MenuItem( Constants.CONNECTION_REPORT  ) );
		subMenuUtilities.add( new MenuItem( Constants.DISCONNCTED_STATISC_REPORT));
		subMenuUtilities.add( new MenuItem( Constants.THRESHOLD_ANALYSIS ) );
		subMenuUtilities.add( new MenuItem( Constants.KPI_MANAGEMENT) );
		subMenuUtilities.add( new MenuItem( Constants.CREATE_DATABASE ) );
		subMenuUtilities.add( new MenuItem( Constants.MENU_UPDATE_INTRAVUE_DEVICE_INFO));
		subMenuUtilities.add( new MenuItem( Constants.SWITCH_PROBE ));

		subMenuItemHelp.add( new MenuItem( Constants.MENU_MANUAL  ) );
		subMenuItemHelp.add( new MenuItem( Constants.MENU_ABOUT  ) );
		subMenuItemNone.add( new MenuItem( Constants.MENU_STATUS  ) );

		subMenuItemSettings.add( new MenuItem (Constants.MENU_CONNECT ) );
		subMenuItemSettings.add( new MenuItem (Constants.MENU_UPLOAD_PK ) );
	//	subMenuItemSettings.add( new MenuItem (Constants.USERS) );

		subMenuItemLanguage.add( new MenuItem (Constants.MENU_ENGLISH) );
		subMenuItemLanguage.add( new MenuItem (Constants.MENU_GERMAN) );

		home = new MenuBarItem( messages.id_home(), subMenuItemNone);
		settings = new MenuBarItem( messages.id_settings() , subMenuItemSettings);
		utilities = new MenuBarItem( messages.id_utilities(), subMenuUtilities );
		help = new MenuBarItem( messages.id_help() , subMenuItemHelp);
		MenuBarItem language = new MenuBarItem( "Language", subMenuItemLanguage );

		Menu subMenuCRC = new Menu();

		Menu subMenuCIfInError = new Menu();

		menuItemHours( subMenuCRC );
		menuItemHours( subMenuCIfInError );

		subMenuCRC.setId( Constants.CRC_ID);
		subMenuCIfInError.setId( Constants.IFINERROR_ID);

		menuCRC.setSubMenu( subMenuCRC );
		menuIfInError.setSubMenu( subMenuCIfInError );

		MenuBar menuBar = new MenuBar();
		menuBar.add(home);
		menuBar.add( settings );
		menuBar.add( utilities );
		menuBar.add( help );
		menuBar.add( language );

		VerticalLayoutContainer vc = new VerticalLayoutContainer();

		NorthSouthContainer northSouthContainer = new NorthSouthContainer();
		northSouthContainer.setNorthWidget( menuBar );

		headerPanel = new ContentPanel();
		headerPanel.setHeading(appInfoPanel.getAppInfo());
		headerPanel.add( northSouthContainer );
		activatePanel(false );
		HorizontalPanel hc = new HorizontalPanel();
		hc.setWidth( "100%" );
		HTML htmlLogoText = new HTML("WBC-Utilities");
		htmlLogoText.addStyleName( "logoText" );
		hc.add( htmlLogoText);

		VerticalPanel vCurrentIpAndServerHost = new VerticalPanel();
		vCurrentIpAndServerHost.setWidth( "100%" );
		vCurrentIpAndServerHost.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
		vCurrentIpAndServerHost.add(currentIpAndServerHost);
		currentIpAndServerHost.addStyleName("logoText");
		hc.add( vCurrentIpAndServerHost );

		VerticalPanel vBrandingPanel = new VerticalPanel();
		vBrandingPanel.setWidth( "100%" );
		vBrandingPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		vBrandingPanel.add( appBrandingPanel.getLogo() );
		hc.add( vBrandingPanel );
		vc.add( hc );
		vc.add( headerPanel );

		SelectionHandler<Item> handler = new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				if (event.getSelectedItem() instanceof MenuItem) {
					MenuItem item = (MenuItem) event.getSelectedItem();
					if( !item.getText().equalsIgnoreCase( Constants.MENU_MANUAL) &&  !item.getText().equalsIgnoreCase(Constants.MENU_ABOUT))
					{
						clearContainer();
						updateHeaderText( item.getText());
						selectedMenu = item.getText();
					}

					loadView( item.getText());

				}
			}
		};

		//Window.addResizeHandler( Event -> loadView(selectedMenu, true) );

		subMenuUtilities.addSelectionHandler(handler);
		subMenuItemSettings.addSelectionHandler( handler );
		subMenuItemLanguage.addSelectionHandler( handler );
		subMenuItemHelp.addSelectionHandler( handler );
		subMenuItemNone.addSelectionHandler( handler );

		return vc;

	}

	private void loadView( String selectedMenu)
	{
		switch(selectedMenu)
		{
		case Constants.CREATE_DATABASE:
			display(getMenuCleanDatabase());
			break;
		case Constants.SWITCH_PROBE:
			display( getMenuSwitchProbe() );
			break;
		case Constants.THRESHOLD_ANALYSIS:
			display( getMenuThreshold(true) );
			break;
		case Constants.KPI_MANAGEMENT:
			display( getMenuKpiManagement(true) );
			break;
		case Constants.MENU_CONNECT:
			display(getConnectionPanel());
			break;
		case Constants.USERS:
			display(getUsersPanel());
			break;
		case Constants.CONNECTION_REPORT:
			display( getConnectionReport(true) );
			break;
		case Constants.MENU_ENGLISH:
			Window.Location.assign( url+"?locale=en");
			break;
		case Constants.MENU_GERMAN:
			Window.Location.assign( url+"?locale=de");
			break;
		case Constants.DISCONNCTED_STATISC_REPORT:
			display( getDisconnectedStatisticReport() );
			break;
		case Constants.MENU_MANUAL:
			Window.open( GWT.getHostPageBaseURL()+"help/wbcutilHelp.htm", "", null );
			break;
		case Constants.MENU_ABOUT:
			getAbout();
			break;
		case Constants.MENU_STATUS:
			display( getStatus());
			break;
		case Constants.MENU_UPLOAD_PK:
			display( getUploadPk());
			break;
		case Constants.MENU_UPDATE_INTRAVUE_DEVICE_INFO:
			display( getUpdateIntravueDeviceInfo());
			break;
		default:
				DashboardUtils.logInfo("Unexpected selected Menu: " + selectedMenu);
		}
	}

	private void clearContainer()
	{
		centerContainer.clear();
	}

	private void menuItemHours( Menu subMenu )
	{

		MenuItem every4Hours = new MenuItem( Constants.EVERY_4_HOUR);
		MenuItem daily = new MenuItem( Constants.DAILY_FOR_1_WEEK);
		MenuItem hourly8Hours = new MenuItem( Constants.HOURLY8HOURS);
		MenuItem hourly24Hours = new MenuItem( Constants.HOURLY24HOURS);
		MenuItem hourly48Hours = new MenuItem( Constants.HOURLY48HOURS);

		every4Hours.setId( "72" );
		hourly8Hours.setId( "8" );
		hourly24Hours.setId( "24" );
		hourly48Hours.setId( "48" );
		daily.setId( "week" );

		subMenu.add( hourly8Hours);
		subMenu.add( hourly24Hours);
		subMenu.add( hourly48Hours);
		subMenu.add( every4Hours);
		subMenu.add( daily);

		subMenu.addSelectionHandler( new SelectionHandler<Item>()
		{
			@Override
			public void onSelection( SelectionEvent<Item> event )
			{
				clearContainer();
				if (event.getSelectedItem() instanceof MenuItem)
				{
					MenuItem item = (MenuItem) event.getSelectedItem();
					String text = subMenu.getId().equals( Constants.CRC_ID)? "CRC Report -": "IfInError Report -";
					updateHeaderText( text + item.getText());
					menu = new MenuCRC(subMenu.getId() ,event.getSelectedItem().getId(),new AsyncCallback()
					{
						@Override
						public void onFailure( Throwable caught )
						{

						}

						@Override
						public void onSuccess( Object result )
						{
							display( menu );

						}
					});
				}
			}
		} );

	}

	private void updateHeaderText( String text)
	{
		if(text.isEmpty())
			appInfoPanel.setSelectedUtility("");
			else
		appInfoPanel.setSelectedUtility(" ("+ text+ ")");
		headerPanel.setHeading(appInfoPanel.getAppInfo());
	}

	@Override
	public void activatePanel( boolean activate )
	{
		home.setEnabled( activate );
		//settings.setEnabled( activate );
		utilities.setEnabled( activate );
		//help.setEnabled( activate );
		subMenuUtilities.setEnabled( activate );
	}

	private LoginPanel getLogin(boolean show)
	{

			loginPanel = new LoginPanel(show, new AsyncCallback<String>()
			{
				@Override
				public void onFailure( Throwable caught )
				{
					updateCurrentIpAndServerHost(false);
				}

				@Override
				public void onSuccess( String result )
				{
					if(result.equals(Constants.IPNOTFOUND)) {
						DashboardUtils.logInfo("Opening ConnectionPanel");
						display(getConnectionPanel());
					}
						else {
						onSuccessFullLogin();
					}
				}
			} );

		return loginPanel;
	}

	private UserPanel getUsersPanel()
	{

		UserPanel userPanel = new UserPanel(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(String result) {
				//onSuccessFullLogin();
			}
		});

		return userPanel;
	}

	private ConnectionDialog getConnectionPanel()
	{
		ConnectionDialog connectionPanel = new ConnectionDialog(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				DashboardUtils.logError("Opening Connection Dialog Failed:" + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				DashboardUtils.logInfo("On Successfull login:" + result);
				onSuccessFullLogin();
			}
		});

		return connectionPanel;
	}

	private void onSuccessFullLogin() {
		activatePanel(true);
		clearContainer();
		updateCurrentIpAndServerHost(true);
		display( getStatus());
	}

	private void updateCurrentIpAndServerHost(boolean connected) {
		if(connected)
		{
			int begin = GWT.getHostPageBaseURL().indexOf("//") + 2;
			int end = GWT.getHostPageBaseURL().lastIndexOf(":");
			if (begin != -1 && end != -1) {
				String ip = GWT.getHostPageBaseURL().substring(begin, end);
				//currentIpAndServerHost.setHTML(ip.equals("127.0.0.1") ? ip + " - Localhost" : ip + " - no name");
				currentIpAndServerHost.setHTML(Globals.HOST_IP_ADDRESS +" - "+  Cookies.getCookie("hostName"));

			}
		}
		else
		{
			currentIpAndServerHost.setHTML("Not Connected");

		}
	}

	private MenuSwitchProbe getMenuSwitchProbe()
	{
		menuSwitchProbe = new MenuSwitchProbe();
		return menuSwitchProbe;
	}

	private MenuCleanDatabase getMenuCleanDatabase()
	{
		if (menuCleanDatabase == null)
		{
			menuCleanDatabase = new MenuCleanDatabase();
		}
		return menuCleanDatabase;
	}

	private MenuThresholdAnalysis getMenuThreshold( boolean refresh )
	{
		if (menuThresholdAnalysis == null || refresh)
		{
			menuThresholdAnalysis = new MenuThresholdAnalysis(new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable caught) {

				}

				@Override
				public void onSuccess(String result) {
					clearContainer();
				}
			});
		}
		return menuThresholdAnalysis;
	}

	private MenuKPIManagement getMenuKpiManagement(boolean refresh)
	{
		if (kpiManagement == null || refresh)
		{
			kpiManagement = new MenuKPIManagement();
		}
		return kpiManagement;
	}

	private MenuConnectionReport getConnectionReport( boolean refresh )
	{
		if (menuConnectionReport == null || refresh)
		{
			menuConnectionReport = new MenuConnectionReport();
		}
		return menuConnectionReport;
	}

	private MenuDisconnectedStatisticReport getDisconnectedStatisticReport()
	{
			menuDisconnectedStatisticReport = new MenuDisconnectedStatisticReport();
			return menuDisconnectedStatisticReport;
	}

	private MenuManual getManual()
	{
		if (menuManual == null)
		{
			menuManual = new MenuManual();
		}
		return menuManual;
	}

	private MenuAbout getAbout()
	{

			menuAbout = new MenuAbout(new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable caught) {

				}

				@Override
				public void onSuccess(String result) {
					//if(result.equalsIgnoreCase("closed")){
				//		display(getStatus());
			//		}

				}
			});
			return menuAbout;
	}

	private MenuStatus getStatus()
	{
		menuStatus = new MenuStatus(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(String result) {
				if(result.equalsIgnoreCase("Refreshed"))
					updateHeaderText("Host Status - last updated: "+ DashboardUtils.getFormattedDateWithTimeJanFormat(new Date()));
			}
		});
		return menuStatus;
	}

	private MenuBase getUploadPk() {
		MenuUploadPK menuUploadPk = new MenuUploadPK();
		return menuUploadPk;
	}

	private MenuBase getUpdateIntravueDeviceInfo() {
		MenuUpdateIntravueDeviceInfo menuUpdateIntravueDeviceInfo = new MenuUpdateIntravueDeviceInfo();
		return menuUpdateIntravueDeviceInfo;
	}

}

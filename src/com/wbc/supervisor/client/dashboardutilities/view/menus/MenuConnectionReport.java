package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.DashboardUtilMessages;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.utils.*;
import com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe.BaseGrid;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ConnectionInfoData;
import com.wbc.supervisor.shared.dashboardutilities.ConnectionInfoInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.IpData;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.MacInfo;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.*;

import java.util.*;

public class MenuConnectionReport<T extends ConnectionInfoData> extends MenuBase implements IsWidget
{

	private static final ConnectionProperties props = GWT.create( ConnectionProperties.class);
	private ListStore<ConnectionInfoData> store = null;
	private ExportWidget exportWidget = new ExportWidget();
	private FilterWidget filterWidget = new FilterWidget();
	private Grid<ConnectionInfoData> grid = null;
	protected ArrayList<T> tempList =  new ArrayList<T>();
	protected List<T> list;
	private DashboardUtilMessages messages =
			GWT.create(DashboardUtilMessages.class);

	@Override
	public Widget asWidget() {

		container.clear();
		container.add( layout());
		doFilter();
		return super.asWidget();

	}

	public MenuConnectionReport()
	{
		getData();
	}

	private void getData()
	{
		String url = DashboardUtils.getFullUrl(Constants.URL_CONNECTION_REPORT);
		logInfo("Calling "+ url);
		getConnectionData(url);

	}

	private void getConnectionData( String json )
	{
		logInfo("Getting Data for Connection Report ");
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
		rpcService.getConnectionData( json, new AsyncCallback<ConnectionInfoInfo>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				logWarning("Getting Data for Connection Report "+ caught.getStackTrace());
				new WarningMessageBox( Constants.CONNECTION_REPORT, caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( ConnectionInfoInfo result )
			{
				logInfo("Connection Report  success : " + result);
				DashboardUtils.handleError(result.getErrorInfo(), Constants.CONNECTION_REPORT, new AsyncCallback<Void>()
				{
					@Override
					public void onFailure( Throwable caught )
					{
						if(caught != null)
						logWarning("Connection Report Failed handleError: " + caught.getLocalizedMessage() );
					}

					@Override
					public void onSuccess( Void data )
					{
						// logInfo("Connection Report populating data: "+ result.toString());
						populate( result );
						exportWidget.setVisible(true);
					}
				} );

			}
		} );
	}

	private Widget layout()
	{
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		HorizontalLayoutContainer h = new HorizontalLayoutContainer();
		h.add(exportWidget);
		h.add(filterWidget, new HorizontalLayoutContainer.HorizontalLayoutData(-1,-1, new Margins(0,0,0,100)));

		container.add(h);
		container.add( grid() );
		return  container;
	}

	public interface ConnectionProperties extends PropertyAccess<ConnectionInfoData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<ConnectionInfoData> key();
		ValueProvider<ConnectionInfoData, String> ipaddress();
		ValueProvider<ConnectionInfoData, String> deviceName();
		ValueProvider<ConnectionInfoData, String> vendor();
		ValueProvider<ConnectionInfoData, String> network();
		ValueProvider<ConnectionInfoData, Date> joinTime();
		ValueProvider<ConnectionInfoData, String> joinDays();
		ValueProvider<ConnectionInfoData, Date> lastTime();
		ValueProvider<ConnectionInfoData, String> lastDays();
		ValueProvider<ConnectionInfoData, Boolean> isConnected();
		ValueProvider<ConnectionInfoData, Integer> criticalState();

	}

	public Grid<ConnectionInfoData> grid()
	{

		ValueProvider<ConnectionInfoData, ConnectionInfoData> valueProviderIp = new ValueProvider<ConnectionInfoData, ConnectionInfoData>()
		{
			@Override
			public String getPath()
			{
				return "";
			}

			@Override
			public ConnectionInfoData getValue(ConnectionInfoData taskDTO)
			{
				return taskDTO;
			}

			@Override
			public void setValue(ConnectionInfoData object, ConnectionInfoData value)
			{
			}
		};

		ValueProvider<ConnectionInfoData, String> valueProviderCritical = new ValueProvider<ConnectionInfoData, String>()
		{
			@Override
			public String getPath()
			{
				return "";
			}

			@Override
			public String getValue(ConnectionInfoData taskDTO)
			{

				switch(taskDTO.getCriticalState()) {
					case 0:
						return messages.id_critical0();
					case 1:
						return messages.id_critical1();
					case 2:
						return messages.id_critical2();
					case 3:
						return messages.id_critical3();
				}
				return "";
			}

			@Override
			public void setValue(ConnectionInfoData object, String value)
			{
			}
		};

		ColumnConfig<ConnectionInfoData, ConnectionInfoData> ipAddressCol = new ColumnConfig<ConnectionInfoData, ConnectionInfoData>(valueProviderIp, 150, "IP");
		ColumnConfig<ConnectionInfoData, String> nameCol = new ColumnConfig<ConnectionInfoData, String>(props.deviceName(), 150, "Name");
		ColumnConfig<ConnectionInfoData, String> vendorCol = new ColumnConfig<ConnectionInfoData, String>(props.vendor(), 300, "Vendor");
		ColumnConfig<ConnectionInfoData, String> networkCol = new ColumnConfig<ConnectionInfoData, String>(props.network(), 150, "Network");
		ColumnConfig<ConnectionInfoData, Date> joinTimeCol = new ColumnConfig<ConnectionInfoData, Date>(props.joinTime(), 150, "Join Time");
		ColumnConfig<ConnectionInfoData, String> joinDaysCol = new ColumnConfig<ConnectionInfoData, String>(props.joinDays(), 70, "Days");
		ColumnConfig<ConnectionInfoData, Date> lastTimeCol = new ColumnConfig<ConnectionInfoData, Date>(props.lastTime(), 150, "Last Time");
		ColumnConfig<ConnectionInfoData, String> lastDaysCol = new ColumnConfig<ConnectionInfoData, String>(props.lastDays(), 70, "Days");
		ColumnConfig<ConnectionInfoData, Boolean> isConnectedCol = new ColumnConfig<ConnectionInfoData, Boolean>(props.isConnected(), 70, "UP");
		ColumnConfig<ConnectionInfoData, String> criticalStateCol = new ColumnConfig<ConnectionInfoData, String>(valueProviderCritical, 130, "Critical");

		List<ColumnConfig<ConnectionInfoData, ?>> columns = new ArrayList<ColumnConfig<ConnectionInfoData, ?>>();

		columns.add(ipAddressCol);
		columns.add(nameCol);
		columns.add(vendorCol);
		columns.add(networkCol);
		columns.add(joinTimeCol);
		columns.add(joinDaysCol);
		columns.add(lastTimeCol);
		columns.add(lastDaysCol);
		columns.add(isConnectedCol);
		columns.add(criticalStateCol);

		Cell cLastTime = new DateCell( DateTimeFormat.getFormat("MM/dd/yy hh:mm"));
		Cell cJoinTime = new DateCell( DateTimeFormat.getFormat("MM/dd/yy hh:mm"));
		lastTimeCol.setCell( cLastTime );
		joinTimeCol.setCell( cJoinTime );

		ipAddressCol.setComparator(new IpComparator() );
		ipAddressCol.setCell( CustomCell.getButtonCellIP() );

		lastDaysCol.setComparator( new DoubleComparator() );
		joinDaysCol.setComparator( new DoubleComparator() );

		ColumnModel<ConnectionInfoData> cm = new ColumnModel<ConnectionInfoData>(columns);

		store = new ListStore<ConnectionInfoData>(props.key());

		 grid = new Grid<ConnectionInfoData>(store, cm);

		DashboardUtils.setDefaultGridStyle( grid );


		//FILTERS
		StringFilter<ConnectionInfoData> deviceNameFilter = new StringFilter<ConnectionInfoData>(props.deviceName());
		StringFilter<ConnectionInfoData> ipFilter = new StringFilter<ConnectionInfoData>(props.ipaddress());
		StringFilter<ConnectionInfoData> criticalStateFilter = new StringFilter<ConnectionInfoData>(valueProviderCritical);
		StringFilter<ConnectionInfoData> lastDaysFilter = new StringFilter<ConnectionInfoData>(props.lastDays());
		BooleanFilter<ConnectionInfoData> isConnectedFilter = new BooleanFilter<ConnectionInfoData>(props.isConnected());
		StringFilter<ConnectionInfoData> joinDaysFilter = new StringFilter<ConnectionInfoData>(props.joinDays());
		StringFilter<ConnectionInfoData> networkFilter = new StringFilter<ConnectionInfoData>(props.network());
		StringFilter<ConnectionInfoData> vendorFilter = new StringFilter<ConnectionInfoData>(props.vendor());
		DateFilter<ConnectionInfoData> joinTimeFilter = new DateFilter<ConnectionInfoData>(props.joinTime());
		DateFilter<ConnectionInfoData> lastTimeFilter = new DateFilter<ConnectionInfoData>(props.lastTime());

		GridFilters<ConnectionInfoData> filters = new GridFilters<ConnectionInfoData>();
		filters.initPlugin(grid);
		filters.setLocal(true);
		filters.addFilter(deviceNameFilter);
		filters.addFilter(criticalStateFilter);
		filters.addFilter(lastDaysFilter);
		filters.addFilter(isConnectedFilter);
		filters.addFilter(vendorFilter);
		filters.addFilter(joinDaysFilter);
		filters.addFilter(networkFilter);
		filters.addFilter(joinTimeFilter);
		filters.addFilter(lastTimeFilter);
		filters.addFilter(ipFilter);
		// Stage manager, load the previous state
		GridFilterStateHandler<ConnectionInfoData> handler = new GridFilterStateHandler<ConnectionInfoData>(grid, filters);
		handler.loadState();

		return grid;

	}

	protected void populate( ConnectionInfoInfo connectionInfoList )
	{
		try
		{
			// GWT.log( "POPULATING Connection Report" );
			List<ConnectionInfoData> connectionInfoDataList = new ArrayList<ConnectionInfoData>();
			for ( Map.Entry<String, ConnectionInfoData> entry : connectionInfoList.getConnTable().entrySet()) {
				connectionInfoDataList.add( entry.getValue() );

			}
			store.addAll( connectionInfoDataList );
			list = (List<T>) connectionInfoDataList;
			exportWidget.export(null, connectionInfoList);

		}catch(Exception ex)
		{
			new WarningMessageBox(Constants.CONNECTION_REPORT, ex.getMessage() );
		}

	}

	private void removeDescFromIpAndExport(ConnectionInfoInfo connectionInfoList) {
		ConnectionInfoInfo connectionInfoListRemovedId = new ConnectionInfoInfo();

		HashMap<String, ConnectionInfoData> connTable = new HashMap<String, ConnectionInfoData>();
		connectionInfoListRemovedId.setErrorInfo(connectionInfoList.getErrorInfo());

		for ( Map.Entry<String, ConnectionInfoData> entry : connectionInfoList.getConnTable().entrySet()) {
			connTable.put(entry.getKey(), entry.getValue());
		}

		for ( Map.Entry<String, ConnectionInfoData> entry : connTable.entrySet()) {

			if(entry.getValue() == null || entry.getValue().getIpaddress() == null || entry.getValue().getIpaddress().isEmpty())
				continue;
			int decIdIndex = entry.getValue().getIpaddress().indexOf( " " );
			String ipAddress = entry.getValue().getIpaddress().substring( 0, decIdIndex );
			entry.getValue().setIpaddress(ipAddress);
		}
		connectionInfoListRemovedId.setConnTable(connTable);
		exportWidget.export(null, connectionInfoListRemovedId);

	}

	public class IpComparator implements Comparator
	{
		@Override
		public int compare(Object obj1, Object obj2) {
			try
			{
				ConnectionInfoData ob1 = (ConnectionInfoData) obj1;
				ConnectionInfoData ob2 = (ConnectionInfoData) obj2;
				String adr1 = ob1.getIpaddress();
				String adr2 = ob2.getIpaddress();

				if(adr1 == null || adr1.toString().isEmpty()) return -1;
				if(adr2 == null || adr2.toString().isEmpty()) return 1;

				String ad2 = adr2.toString();
				String[] ba1 = adr1.toString().split( "\\." );
				String[] ba2 = ad2.split( "\\." );

				for ( int i = 0; i < ba1.length; i++ )
				{
					int b1 = Integer.parseInt( ba1[ i ] );
					int b2 = Integer.parseInt( ba2[ i ] );

					if (b1 == b2)
						continue;
					if (b1 < b2)
						return -1;
					else
						return 1;
				}
				return 0;
			}
			catch ( Exception ex )
			{
				return 0;
			}

		}


	}

	///////////FILTER
	public void doFilter()
	{
		setColumnsToFilter("IP", "Name", "Vendor", "Network", "Join Time", "Days","LastDays", "Last Time", "UP", "Critical");
		filterWidget.colSearch.setVisible(true);
		filterWidget.columnToFilter.setVisible(true);


		filterWidget.buttonSearch.addSelectHandler(Event -> search());

		filterWidget.searchField.addKeyUpHandler(new KeyUpHandler()
		{

			@Override
			public void onKeyUp(KeyUpEvent event)
			{
				if(filterWidget.searchField.getText().isEmpty()  || (event.getNativeKeyCode() == 13 && !filterWidget.searchField.getText().isEmpty()))
					search();
			}
		});

		filterWidget.clearField.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				filterWidget.searchField.setText("");
				search();
			}
		});

	}

	private void search() {
		activeFilterHeading();
		DashboardUtils.logInfo("Searching Grid for:" + filterWidget.searchField.getText());

		filter();
	}

	private void activeFilterHeading()
	{
		if(!filterWidget.searchField.getText().isEmpty()) filterWidget.filteredGridHeading.setVisible(true);
		else filterWidget.filteredGridHeading.setVisible(false);
	}

	private boolean filterTextSatisfies(String filterTextValue, ArrayList<String> params)
	{
		boolean satisfies = false;
		if (filterTextValue.contains(" "))
		{
			String[] parts = filterTextValue.split(" ");
			for (int i = 0; i < parts.length; i++)
			{

				satisfies = filterText(parts[i], params);
				if (!satisfies) break;
			}

			return satisfies;

		}
		else
		{
			return filterText(filterTextValue, params);
		}

	}

	private boolean filterText(String filterTextValue, ArrayList<String> params)
	{
		for(String param : params) {
			String p = param.replaceAll("\\s+", "");
			filterTextValue = filterTextValue.replaceAll("\\s+", "");

			if (GxtStringUtils.containsIgnoreCase(p, filterTextValue))
				return true;
		}
		return false;
	}



	public void filter(List<FilterConfig> filters, String filterTextValue)
	{
		if(tempList.isEmpty()) {
			for (T arp : list) {
				tempList.add(arp);
			}
		}
		list.clear();

		for (int j = 0; j < tempList.size(); j++)
		{
			boolean valid = true;

			if (filters != null)
			{
				for (int i = 0; i < filters.size(); i++)
				{
					valid = valid && filterTextSatisfies(filterTextValue, getParams(tempList.get(j), filterWidget.columnToFilter.getText()));

				}
			}
			if (valid) list.add(tempList.get(j));

		}

	}

	public void filter( String filterTextValue)
	{
		if(tempList.isEmpty()) {
			for (T arp : list) {
				tempList.add(arp);
			}
		}
		list.clear();
		store.clear();
		for (int j = 0; j < tempList.size(); j++)
		{
			boolean valid = true;

			valid = valid && filterTextSatisfies(filterTextValue, getParams(tempList.get(j),filterWidget.columnToFilter.getText()));

			if (valid) list.add(tempList.get(j));

		}
		store.addAll(list);

	}

	protected  void setColumnsToFilter(String... columns)
	{
		filterWidget.columnToFilter.clear();
		for(String column : columns)
			filterWidget.columnToFilter.add(column);
	}

	protected void filter() {
		filter(filterWidget.searchField.getText());

	}

	protected ArrayList<String> getParams(T connection, String column) {
		T param = connection;
		ArrayList<String> params = new ArrayList<String>();
		switch (column) {
			case "IP":
				params.add(param.getIpaddress());
				break;
			case "Vendor":
				params.add(param.getVendor());
				break;
			case "Network":
				params.add(param.getNetwork());
				break;
			case "Name":
				params.add(param.getDeviceName());
				break;
			case "Join Time":
				params.add(param.getJoinTime() + "");
				break;
			case "Days":
				params.add(param.getJoinDays() + "");
				break;
			case "Last Time":
				params.add(param.getLastTime() + "");
				break;
			case "LastDays":
				params.add(param.getLastDays() + "");
				break;
			case "UP":
				params.add(param.isConnected() + "");
				break;
			case "Critical":
				params.add(param.getCriticalState() + "");
				break;
			case "":
			case "All":
				params.add(param.getDeviceName());
				params.add(param.getVendor());
				params.add(param.getNetwork());
				params.add(param.getJoinTime() + "");
				params.add(param.getJoinDays());
				params.add(param.getLastDays());
				params.add(param.getLastTime() + "");
				params.add(param.isConnected() + "");
				params.add(param.getCriticalState() + "");
				params.add(param.getIpaddress());
				break;
		}
		return params;
	}


}

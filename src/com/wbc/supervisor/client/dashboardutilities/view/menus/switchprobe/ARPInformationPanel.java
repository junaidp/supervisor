package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.GxtStringUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.IpComparator;
import com.wbc.supervisor.client.dashboardutilities.utils.LiveGridViewExt;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.ArpDataExtended;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.MacInfo;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.*;
import com.sencha.gxt.data.shared.loader.*;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.filters.BooleanFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.*;

public class ARPInformationPanel extends SwitchProbePanel
{
	private LiveGridViewExt<ArpDataExtended> gridView;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>> pagingLoader;

	public ARPInformationPanel()
	{
		add(filterWidget);
		add(grid());
		init();
		initGlobalSearch();
	}

	private void initGlobalSearch() {
		setColumnsToFilter("ip", "mac", "ifNum", "ifDescription", "name", "vendor");
		filterWidget.colSearch.setVisible(true);
		filterWidget.columnToFilter.setVisible(true);
		//filterWidget.labelSearch.getElement().getStyle().setPaddingLeft(50, Style.Unit.PX);
	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String columnToFilter) {
		ArpDataExtended arp = (ArpDataExtended) baseGrid;
		ArrayList<String> params = new ArrayList<String>();

		switch (columnToFilter) {
			case "mac":
				params.add(arp.getMac());
				break;
			case "ifNum":
				params.add(arp.getIfNum() + "");
				break;
			case "ifDescription":
				params.add(arp.getIfDescription());
				break;
			case "name":
				params.add(arp.getName() + "");
				break;
			case "vendor":
				params.add(arp.getVendor());
				break;
			case "ip":
				params.add(arp.getIp());
				break;
			case "All":
			case "":
				params.add(arp.getIp());
				params.add(arp.getMac());
				params.add(arp.getName());
				params.add(arp.getIfNum() + "");
				params.add(arp.getIfDescription() + "");
				params.add(arp.getVendor());
				break;
		}

		return params;
	}

	@Override
	protected void filter() {
		filter(filters, filterWidget.searchField.getText());
		restartFilterText();
	}

	private static final ARPProperties props = GWT.create( ARPProperties.class);
	private ListStore<ArpDataExtended> store =null;

	public interface ARPProperties extends PropertyAccess<SwitchErrorData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<ArpDataExtended> key();

		ValueProvider<ArpDataExtended, String> mac();
		ValueProvider<ArpDataExtended, String> ifDescription();
		ValueProvider<ArpDataExtended, Integer> ifNum();
		ValueProvider<ArpDataExtended, Integer> descid();
		ValueProvider<ArpDataExtended, String> ip();
		ValueProvider<ArpDataExtended, String> name();
		ValueProvider<ArpDataExtended, String> vendor();
	}

	public VerticalLayoutContainer grid()
	{

		ColumnConfig<ArpDataExtended, String> macCol = new ColumnConfig<ArpDataExtended, String>(props.mac(), 150, "Mac");
		ColumnConfig<ArpDataExtended, Integer> ifNumCol = new ColumnConfig<ArpDataExtended, Integer>(props.ifNum(), 150, "IfNum");
		ColumnConfig<ArpDataExtended, String> ifDescCol = new ColumnConfig<ArpDataExtended, String>(props.ifDescription(), 150, "ifDesc");
		ColumnConfig<ArpDataExtended, String> ipCol = new ColumnConfig<ArpDataExtended, String>(props.ip(), 150, "IP");
		ColumnConfig<ArpDataExtended, String> nameCol = new ColumnConfig<ArpDataExtended, String>(props.name(), 150, "Name");
		ColumnConfig<ArpDataExtended, String> ivendorCol = new ColumnConfig<ArpDataExtended, String>(props.vendor(), 150, "Vendor");

		ipCol.setComparator(new IpComparator() );
		ipCol.setCell( getButtonCellIP());

		List<ColumnConfig<ArpDataExtended, ?>> columns = new ArrayList<ColumnConfig<ArpDataExtended, ?>>();

		columns.add(ipCol);
		columns.add(macCol);
		columns.add(ifNumCol);
		columns.add(ifDescCol);
		columns.add(nameCol);
		columns.add(ivendorCol);

		ColumnModel<ArpDataExtended> cm = new ColumnModel<ArpDataExtended>(columns);

		store = new ListStore<ArpDataExtended>(props.key());
		pagingLoader = getLoader(store);
		gridView = initialiseLiveGrid();


		grid = new Grid<ArpDataExtended>(store, cm) {
			@Override
			protected void onAfterFirstAttach() {
				super.onAfterFirstAttach();
				Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
					@Override
					public void execute() {
						pagingLoader.load(0, gridView.getCacheSize());
					}
				});
			}
		};

		DashboardUtils.setDefaultGridStyle( grid );
		grid.setLoader(pagingLoader);
		grid.setLoadMask(true);
		grid.setView(gridView);

		ToolBar toolBar = new ToolBar();
		toolBar.setBorders(false);
		toolBar.add(new LiveToolItem(grid));
		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, 25));
		verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 500));

		return verticalLayoutContainer;


	}

	protected void populate( ArrayList<ArpDataExtended> list )
	{
		try
		{
			// DashboardUtils.logInfo( "POPULATING ARP INFO with size: " + list.size()  );
			this.list = list;

			UtilGrid utilGrid = new UtilGrid();
			utilGrid.setListArp(list);
			filterWidget.exportWidget.export(null, utilGrid);
		}catch(Exception ex)
		{
			new WarningMessageBox( "ARP INFORMATION PANEL", ex.getMessage() );
		}

	}

	private ArrayList<ArpDataExtended> removeDescFromIp(ArrayList<ArpDataExtended> data) {
		for (ArpDataExtended arpDataExtended : data ) {
			if(arpDataExtended.getIp() == null || arpDataExtended.getIp() == null || arpDataExtended.getIp().isEmpty())
				continue;
			int decIdIndex = arpDataExtended.getIp().indexOf( " " );
			String ipAddress = arpDataExtended.getIp().substring( 0, decIdIndex );
			arpDataExtended.setIp(ipAddress);
		}
		return data;
	}

	public  Cell<String> getButtonCellIP()
	{
		ButtonCell<String> symbolCell = new ButtonCell<String>()
		{
			@Override
			public void render(Context context, String ips, SafeHtmlBuilder sb)
			{

				String key = context.getKey().toString();
				ArpDataExtended ip = (ArpDataExtended) grid.getStore().findModelWithKey(key);
				if(ip!= null)
				{
					if(ip.getDescid()!=0)
						sb.appendHtmlConstant( "<span style='background-color:white; color:blue; cursor: pointer;  text-decoration: underline;'>" + ip.getIp() + "</span>" );
					else
						sb.appendHtmlConstant( "<span style='background-color:white;'>" + ip.getIp() + "</span>" );

				}
				else
				{
					sb.appendHtmlConstant("<span> &nbsp; </span>");
				}
			}

			@Override
			public void onBrowserEvent(Context context, Element parent, String ips, NativeEvent event,
									   ValueUpdater<String> valueUpdater)
			{
				super.onBrowserEvent( context, parent, ips, event, valueUpdater );

				String eventType = event.getType();
				String key = context.getKey().toString();
				ArpDataExtended ip = (ArpDataExtended) grid.getStore().findModelWithKey(key);
				if(ip!= null && ip.getDescid() != 0)
				{
					if ("click".equals( eventType ))
					{
						DashboardUtils.logInfo("Opening Ip :"+ ip.getIp() +" with DescId: "+ ip.getDescid());
						DashboardUtils.onIpSelection( ip.getIp(),  ip.getDescid());
					}
				}

			}
		};
		return symbolCell;
	}


	protected PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>> getLoader(ListStore<ArpDataExtended> store)
	{
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>>()
		{
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<ArpDataExtended>> callback)
			{

				getDatas(loadConfig, callback);
			}
		};

		PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>> loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>>(proxy);

		loader.useLoadConfig(new FilterPagingLoadConfigBean());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, ArpDataExtended, PagingLoadResult<ArpDataExtended>>(store));
		loader.setRemoteSort(true);

		gridFilters = new GridFilters<ArpDataExtended>(loader);
		return loader;
	}

	private void getDatas(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<ArpDataExtended>> callback)
	{
		try
		{
			if (list == null)
			{
				callback.onFailure(new Exception("empty Arp"));
				return;
			}

			final int offset = loadConfig.getOffset();
			int limit = loadConfig.getLimit();
			final List<ArpDataExtended> datas = new ArrayList<ArpDataExtended>();
			List<? extends SortInfo> sortInfo = loadConfig.getSortInfo();
			filters = loadConfig.getFilters();
			filter(filters, filterWidget.searchField.getText());
			sortFilteredData(sortInfo, grid);
			int end = offset + limit;
			if(end > list.size()) end = list.size();
			datas.clear();

			for (int i = offset; i < end; i++)
			{
				datas.add((ArpDataExtended) list.get( i ));
			}


			PagingLoadResult<ArpDataExtended> result = new PagingLoadResult<ArpDataExtended>()
			{

				private static final long serialVersionUID = 1L;

				@Override
				public List<ArpDataExtended> getData()
				{

					return datas;
				}

				@Override
				public void setTotalLength(int totalLength)
				{

				}

				@Override
				public void setOffset(int offset)
				{
				}

				@Override
				public int getTotalLength()
				{
					return list.size();
				}

				@Override
				public int getOffset()
				{
					return offset;
				}
			};
			callback.onSuccess(result);
		}
		catch (

				Exception ex)
		{
			System.out.println("err");
		}
	}

	public void sortFilteredData(List<? extends SortInfo> sortInfo, Grid<ArpDataExtended> grid)
	{
		if (!sortInfo.isEmpty())
		{
			SortDir sortDir = sortInfo.get(0).getSortDir();
			String s = sortInfo.get(0).getSortField();
			int index = getColumnIndex(s);
			DashboardUtils.logInfo("Sorting Aprp, Column: " + s + "Order: "+ sortDir.toString());
			ValueProvider<ArpDataExtended, Comparable> vp = (ValueProvider) grid.getColumnModel().getColumns().get(index).getValueProvider();
			Comparator comparator = getComparator(vp, sortDir);

			Collections.sort(list, comparator);

		}
		else
		{
			//ValueProvider<MacInfo, Comparable> vp = (ValueProvider) grid.getColumnModel().getColumns().get(0).getValueProvider();
			//Comparator comparator = getComparator(vp, SortDir.DESC);
			//Collections.sort(list, comparator);
		}

	}

	private int getColumnIndex(String s) {

		switch (s){
			case "mac":
				return 1;
			case "ifNum":
				return 2;
			case "ifDescription":
				return 3;
			case "name":
				return 4;
			case "vendor":
				return 5;
			default:
				return 0;
		}
	}

	public <V extends Comparable<V>> Comparator<ArpDataExtended> getComparator(final ValueProvider<? super ArpDataExtended, V> property, SortDir direction)
	{

		Comparator<ArpDataExtended> comp = new Comparator<ArpDataExtended>()
		{
			@Override
			public int compare(ArpDataExtended o1, ArpDataExtended o2)
			{
				V v1 = property.getValue(o1);
				V v2 = property.getValue(o2);
				if ((v1 == null & v2 != null) || (v1 != null && v2 == null)) { return v1 == null ? -1 : 1; }
				if (v1 == null & v2 == null) { return 0; }
				if (direction == SortDir.ASC) return v1.compareTo(v2);
				else
					return v2.compareTo(v1);
			}
		};
		return comp;
	}

	private LiveGridViewExt<ArpDataExtended> initialiseLiveGrid()
	{
		return new LiveGridViewExt<ArpDataExtended>()
		{
			@Override
			protected void initHeader()
			{
				super.initHeader();

				// remove tooltip
				ColumnHeader<ArpDataExtended> header = getHeader();
				header.removeToolTip();
				header.disableEvents(); // Disable default tooltip on header
			}

			private native QuickTip getQuickTip(ColumnHeader<ArpDataExtended> header)
				/*-{
					return header.@com.sencha.gxt.widget.core.client.grid.ColumnHeader::quickTip;
				}-*/;
		};
	}


}